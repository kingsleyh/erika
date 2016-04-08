package erikas.bits

import erikas.bits.Driver.handleRequest
import erikas.bits.ResponseUtils._
import argonaut.Argonaut._

class WebElement(elementId :String, sessionId: String, sessionUrl: String, driver: PhantomDriver, session: Session) {

  val elementSessionUrl = s"$sessionUrl/element/$elementId"

  def getText: Option[String] = handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/text")).decode[StringResponse].value

  def getAttribute(attribute: String): Option[String] = {
    handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/attribute")).decode[StringResponse].value
  }

  def click(): Unit = handleRequest(elementSessionUrl, driver.doPost(s"$elementSessionUrl/click", ElementClickRequest(elementId).asJson))

  def clear(): Unit = handleRequest(elementSessionUrl, driver.doPost(s"$elementSessionUrl/clear", ElementClearRequest(elementId, sessionId).asJson))

  def isEnabled: Boolean = handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/enabled")).decode[BooleanResponse].value

}
