package erikas.bits

import argonaut.Argonaut._
import erikas.bits.Driver.handleRequest
import erikas.bits.ResponseUtils._

class Session(driver: PhantomDriver, desiredCapabilities: Capabilities = Capabilities(),
              requiredCapabilities: Capabilities = Capabilities()) {

  var sessionId = ""
  var sessionUrl = ""
  var globalTimeout = 5000

  def create(): Unit = {
    sessionId = handleRequest("/session", driver.doPost("/session", RequestSession(desiredCapabilities, requiredCapabilities).asJson))
      .response.decode[SessionResponse].sessionId

    sessionUrl = s"/session/$sessionId"
    println(s"[INFO] creating new session with id: $sessionId")
  }

  def setGlobalTimeout(timeout: Int) = globalTimeout = timeout

  def getGlobalTimeout = globalTimeout

  def visitUrl(url: String) = {
    handleRequest(sessionUrl, driver.doPost(s"$sessionUrl/url", RequestUrl(url).asJson))
    this
  }

  def findElement(by: By): WebElement = {
    val elementId = handleRequest(sessionUrl, driver.doPost(s"$sessionUrl/element", RequestFindElement(by.locatorStrategy, by.value).asJson))
      .response.decode[ElementResponse].value.get("ELEMENT") match {
      case None => throw APIResponseError("oops")
      case Some(ele) => ele
    }

    new WebElement(elementId, sessionId, sessionUrl, driver, this)
  }

}

object Session extends App {

  val session = new Session(Driver("127.0.0.1", 7878))
  session.create()
  session.visitUrl("http://jamesclear.com/")
  Thread.sleep(2000)
  val element = session.findElement(By.className("entry-title"))
  println(element.getText)
}
