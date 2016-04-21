package erikas.bits

import java.io.{File, FileOutputStream}

import argonaut.Argonaut._
import argonaut.Json
import erikas.bits.Driver.handleRequest
import erikas.bits.ResponseUtils._
import sun.misc.BASE64Decoder

import scala.sys.process.Process

class Session(driver: PhantomDriver, desiredCapabilities: Capabilities = Capabilities(),
              requiredCapabilities: Capabilities = Capabilities()) {

  var sessionId = ""
  var sessionUrl = ""
  var globalTimeout = 5000

  def create(): Unit = {
    sessionId = handleRequest("/session", driver.doPost("/session", SessionRequest(desiredCapabilities, requiredCapabilities).asJson))
      .response.decode[CreateSessionResponse].sessionId

    sessionUrl = s"/session/$sessionId"
    println(s"[INFO] creating new session with id: $sessionId")
  }

  def setGlobalTimeout(timeout: Int) = globalTimeout = timeout

  def getGlobalTimeout = globalTimeout

  def visitUrl(url: String) = {
    handleRequest(sessionUrl, driver.doPost(s"$sessionUrl/url", UrlRequest(url).asJson))
    this
  }

  def findElement(by: By): WebElement = {
    val elementId = handleRequest(sessionUrl, driver.doPost(s"$sessionUrl/element", FindElementRequest(by.locatorStrategy, by.value).asJson))
      .response.decode[ElementResponse].value.get("ELEMENT") match {
      case None => throw APIResponseError("oops")
      case Some(ele) => ele
    }

    new WebElement(elementId, sessionId, sessionUrl, driver, this)
  }

  def findElements(by: By): List[WebElement] = {
   val elementIds = handleRequest(sessionUrl, driver.doPost(s"$sessionUrl/elements", FindElementRequest(by.locatorStrategy, by.value).asJson))
      .response.decode[ElementResponses].value.map(er => er.get("ELEMENT"))

    for {
      maybeElementId <- elementIds
      elementId      <- maybeElementId

    } yield new WebElement(elementId,sessionId, sessionUrl, driver, this)
  }

  def elementExists(by: By): Boolean = findElements(by).nonEmpty

  // why does this return the text "active" intead of an elementId?
  def getActiveElement: Option[String] = {
   handleRequest(sessionUrl, driver.doGet(s"$sessionUrl/element/active")).decode[ElementResponse].value.get("ELEMENT")
  }

  def getSessions: List[Sessions] = {
    val url = "/sessions"
    handleRequest(url, driver.doGet(url)).response.decode[SessionResponse].value
  }

  def getStatus: ServerStatus = {
    val url = "/status"
    handleRequest(url, driver.doGet(url)).decode[ServerStatusResponse].value
  }

  def getCapabilities: Capabilities = {
    handleRequest(sessionUrl, driver.doGet(sessionUrl)).decode[CapabilityResponse].value
  }

  def dispose() = handleRequest(sessionUrl, driver.doDelete(sessionUrl))

  def getWindowHandles: List[WindowHandle] = {
    handleRequest(sessionUrl, driver.doGet(s"$sessionUrl/window_handles")).decode[WindowHandlesResponse].value.map(h => WindowHandle(h))
  }

  def getWindowHandle: WindowHandle = {
    WindowHandle(handleRequest(sessionUrl, driver.doGet(s"$sessionUrl/window_handle")).decode[WindowHandleResponse].value)
  }

  def getUrl: Option[String] = handleRequest(sessionUrl, driver.doGet(s"$sessionUrl/url")).decode[StringResponse].value

  def goForward = handleRequest(sessionUrl, driver.doPost(s"$sessionUrl/forward", Json()))

  def goBack = handleRequest(sessionUrl, driver.doPost(s"$sessionUrl/back", Json()))

  def refresh = handleRequest(sessionUrl, driver.doPost(s"$sessionUrl/refresh", Json()))

  def getSource: Option[String] = handleRequest(sessionUrl, driver.doGet(s"$sessionUrl/source")).decode[StringResponse].value

  def getTitle: Option[String] = handleRequest(sessionUrl, driver.doGet(s"$sessionUrl/title")).decode[StringResponse].value

  def executeScript(script: String, args: List[String] = Nil) = {
    handleRequest(sessionUrl, driver.doPost(s"$sessionUrl/execute", ExecuteScriptRequest(script, args).asJson))
  }

  def executeAsyncScript(script: String, args: List[String] = Nil) = {
    handleRequest(sessionUrl, driver.doPost(s"$sessionUrl/execute_async", ExecuteScriptRequest(script, args).asJson))
  }

  def takeScreenshot(outputFile: String = "screenshot.png") = {
    handleRequest(sessionUrl, driver.doGet(s"$sessionUrl/screenshot")).decode[StringResponse].value.foreach { s =>
      val decoded = new BASE64Decoder().decodeBuffer(s)
      val decodedStream = new FileOutputStream(new File(outputFile))
      decodedStream.write(decoded)
      decodedStream.close()
    }
  }

  def setTimeout(timeoutType: TimeoutType.Value, milliseconds: Int) = {
    handleRequest(sessionUrl, driver.doPost(s"$sessionUrl/timeouts", TimeoutRequest(timeoutType, milliseconds).toJson()))
  }

  def setAsyncScriptTimeout(milliseconds: Int) = {
    handleRequest(sessionUrl, driver.doPost(s"$sessionUrl/timeouts/async_script", TimeoutValueRequest(milliseconds).asJson))
  }

  def setImplicitWaitTimeout(milliseconds: Int) = {
    handleRequest(sessionUrl, driver.doPost(s"$sessionUrl/timeouts/implicit_wait", TimeoutValueRequest(milliseconds).asJson))
  }

  def waitFor[T <: Searcher](element: T, condition: Condition, timeout: Int = getGlobalTimeout) = {
    Waitress(this).waitFor(element, condition, timeout)
  }

  def findFirst[T <: Searcher](element: T, condition: Condition, timeout: Int = getGlobalTimeout): Option[WebElement] = {
     Waitress(this).waitAndFindFirst(element, condition, timeout)
  }

  def findAll(by: By, condition: Condition, timeout: Int = getGlobalTimeout): List[WebElement] = {
      Waitress(this).waitAndFindAll(by, condition, timeout)
  }

  def waitForFunction(runnable: () => Result, timeout: Int = getGlobalTimeout){
    Waitress(this).waitFor(runnable, timeout)
  }

  def waitForUrl(expectedUrl: String, timeout: Int = getGlobalTimeout) = {
    val waitForUrlFunction: () => Result = () => {
      val actualUrl = this.getUrl.getOrElse("")
      val outcome: Boolean = expectedUrl == actualUrl
      if(outcome) Result(outcome, actualUrl) else Result(outcome, s"Error: expected url: $expectedUrl but got url: $actualUrl")
    }
    Waitress(this).waitFor(waitForUrlFunction, timeout)
  }

}

object Session {

  def apply(desiredCapabilities: Capabilities = Capabilities(),
            requiredCapabilities: Capabilities = Capabilities(),
//            phantomJsOptions: PhantomJsOptions = PhantomJsOptions(),
            pathToPhantom: String = "/usr/local/bin/phantomjs",
            host: String = "127.0.0.1",
            port: Int = Session.freePort
           )(block: (Session) => Unit) = {
    val commands = s"$pathToPhantom --webdriver=$host:$port"

    println(commands)

    val process = Process(commands).run()

    val session = new Session(Driver(host,port),desiredCapabilities, requiredCapabilities)

    Eventually(10000).tryExecute(() => {
      session.create()
    })

    try {
      block(session)
    } finally {
      session.dispose()
      process.destroy()
    }

  }

  private def freePort = {
    val p = new java.net.ServerSocket(0)
    p.close()
    p.getLocalPort
  }

}

object Run extends App {

  Session()(session => {

    session.visitUrl("http://jamesclear.com/")

      val ele = session.findFirst(By.className("entry-title"), Condition.attributeContains("itemprop", "headline"))

    println(ele)
  })


//  val session = new Session(Driver("127.0.0.1", 7878))
//  session.create()
//  session.visitUrl("http://jamesclear.com/")
//  Thread.sleep(1000)
//  println(session.findElements(By.className("entry-title")).nonEmpty)
//  println(session.getSource)
//  session.takeScreenshot()
//
//    val element = session.findElement(By.className("entry-title"))
//  Thread.sleep(1000)
//  println(element.getAttribute("class"))
//    session.waitFor(element, Condition.attributeContains("itemprop","headline"))
//    session.waitFor(By.className("entry-title"), Condition.attributeContains("itemprop","headline"))


//  session.waitForFunction(() => {
//    val attr: Option[String] = session.findElement(By.className("entry-title")).getAttribute("itemprop")
//    Result(attr.contains("headline"), "Error could not find attribute: itemprop")
//  },10000)

//session.waitForUrl("http://jamesclear.com/")

//    session.waitFor(element, Condition.titleIs("wooop"))

//    session.waitFor(By.className("entry-title"), Condition.titleIs("wooop"))


//  Thread.sleep(1000)



//  println(element.getAttribute("class"))
//  element.isEnabled
}


