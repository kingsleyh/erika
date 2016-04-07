package erikas.bits

import argonaut.Argonaut._
import erikas.bits.Driver.handleRequest
import erikas.bits.ResponseUtils._

class Session(driver: PhantomDriver, desiredCapabilities: Capabilities = Capabilities(),
              requiredCapabilities: Capabilities = Capabilities()) {

  var sessionId = ""
  var sessionUrl = ""

  def create(): Unit = {
    sessionId = handleRequest("/session", driver.doPost("/session", RequestSession(desiredCapabilities, requiredCapabilities).asJson))
      .response.decode[SessionResponse].sessionId

    sessionUrl = s"/session/$sessionId"
    println(s"[INFO] creating new session with id: $sessionId")
  }

  def visitUrl(url: String) = {
    handleRequest(sessionUrl, driver.doPost(s"$sessionUrl/url", RequestUrl(url).asJson))
    this
  }

  def findElement(by: By): String = {
    val elementId = handleRequest(sessionUrl, driver.doPost(s"$sessionUrl/element", RequestFindElement(by.locatorStrategy, by.value).asJson))
      .response.decode[ElementResponse].value.get("ELEMENT") match {
      case None => throw APIResponseError("oops")
      case Some(ele) => ele
    }

    elementId
  }

}

object Session extends App {

  val session = new Session(Driver("127.0.0.1", 7878))
  session.create()
  session.visitUrl("http://www.southwark.gov.uk/doitonline")
  session.findElement(By.id("SearchSite"))

}
