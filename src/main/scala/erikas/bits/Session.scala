package erikas.bits

import argonaut._, Argonaut._
import erikas.bits.Driver.handleFailedRequest

class Session(host: String, port: Int, desiredCapabilities: Capabilities = Capabilities(),
              requiredCapabilities: Capabilities = Capabilities()) {

  val driver = new Driver(host, port)
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
    val apiElement = RequestFindElement(by.getStrategy(), by.getValue()).asJson
    val response = driver.doPost(s"$sessionUrl/element", apiElement)

  }


}


object Session extends App {

  val session = new Session("127.0.0.1", 7878)
  session.create()
  session.visitUrl("https://www.paypal.com/signin/?country.x=GB&locale.x=en_GB")
  session.findElement(By.id("email"))

}