package erikas.bits

import argonaut.Argonaut._
import argonaut.Json
import erikas.bits.Driver.handleFailedRequest
import io.shaka.http.Response

class Session(driver: PhantomDriver, desiredCapabilities: Capabilities = Capabilities(),
              requiredCapabilities: Capabilities = Capabilities()) {

  var sessionId = ""
  var sessionUrl = ""

  def create(): Unit = {
    val response = driver.doPost("/session", RequestSession(desiredCapabilities, requiredCapabilities).asJson)
    handleFailedRequest("/session", response)

    sessionId = response.entityAsString.decodeOption[SessionResponse].get.sessionId
    sessionUrl = s"/session/$sessionId"

    println(s"[INFO] creating new session with id: $sessionId")

  }

  def visitUrl(url: String) = {
    handleFailedRequest(sessionUrl, driver.doPost(s"$sessionUrl/url", RequestUrl(url).asJson))
    this
  }

  def findElement(by: By): WebElement = {
    val response = driver.doPost(s"$sessionUrl/element", RequestFindElement(by.locatorStrategy, by.value).asJson)
    handleFailedRequest(sessionUrl, response)
    println(response.entityAsString)
    val elementId = response.entityAsString.decodeOption[ElementResponse].get.value.get("ELEMENT").get
    new WebElement(elementId, sessionId, sessionUrl, driver, this)
  }


}


object Session extends App {

  val session = new Session(Driver("127.0.0.1", 7878))
  session.create()
  session.visitUrl("http://www.southwark.gov.uk/doitonline")
//  session.findElement(By.id("SearchSite"))

}
