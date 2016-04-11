package erikas.bits

import argonaut.Argonaut._
import argonaut.Json
import erikas.bits.Driver.handleRequest
import erikas.bits.ResponseUtils._
import java.io.{File, FileOutputStream}
import sun.misc.BASE64Decoder

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

  def dispose = handleRequest(sessionUrl, driver.doDelete(sessionUrl))

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
    handleRequest(sessionUrl, driver.doPost(s"$sessionUrl/timeouts", TimeoutRequest(timeoutType, milliseconds).asJson))
  }

  def setAsyncScriptTimeout(timeoutType: TimeoutType.type, milliseconds: Int) = {
    handleRequest(sessionUrl, driver.doPost(s"$sessionUrl/timeouts/async_script", TimeoutValueRequest(milliseconds).asJson))
  }

  def setImplicitWaitTimeout(timeoutType: TimeoutType.type, milliseconds: Int) = {
    handleRequest(sessionUrl, driver.doPost(s"$sessionUrl/timeouts/implicit_wait", TimeoutValueRequest(milliseconds).asJson))
  }

}

object Session extends App {

  val session = new Session(Driver("127.0.0.1", 7878))
  session.create()
  session.visitUrl("http://jamesclear.com/")
  Thread.sleep(1000)
  println(session.findElements(By.className("entry-title")).nonEmpty)
//  println(session.getSource)
//  session.takeScreenshot()

  //  val element = session.findElement(By.className("entry-title"))
//  Thread.sleep(1000)
//  println(element.getAttribute("class"))
//  element.isEnabled
}
