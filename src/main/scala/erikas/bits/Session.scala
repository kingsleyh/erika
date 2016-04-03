package erikas.bits

import argonaut.Argonaut._
import erikas.bits.Driver.handleFailedRequest

class Session(driver: PhantomDriver, desiredCapabilities: Capabilities = Capabilities(),
              requiredCapabilities: Capabilities = Capabilities()) {

  var sessionId = ""
  var sessionUrl = ""

  def create(): Unit = {
    val response = driver.doPost("/session", RequestSession(desiredCapabilities, requiredCapabilities).asJson)
    handleFailedRequest("/session", response)

    sessionId = response.entityAsString.decodeEither[SessionResponse] match {
      case Left(message) => throw APIResponseError(message)
      case Right(sessionResponse) => sessionResponse.sessionId
    }

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

    val element: Option[String] = response.entityAsString.decodeEither[ElementResponse] match {
      case Left(message) => throw APIResponseError(message)
      case Right(elementResponse) => elementResponse.value.get("ELEMENT")
    }
    new WebElement(element.get, sessionId, sessionUrl, driver, this)
  }


}


object Session extends App {

  val session = new Session(Driver("127.0.0.1", 7878))
  session.create()
  session.visitUrl("http://www.southwark.gov.uk/doitonline")
//  session.findElement(By.id("SearchSite"))

}
