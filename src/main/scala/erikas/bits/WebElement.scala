package erikas.bits

import argonaut.Argonaut._
import erikas.bits.Driver.handleRequest
import erikas.bits.ResponseUtils._

trait Element {
  def getAttribute(attribute: String): Option[String]
}

class WebElement(elementId :String, sessionId: String, sessionUrl: String, driver: PhantomDriver, session: Session) extends Searcher with Element {

  val elementSessionUrl = s"$sessionUrl/element/$elementId"

  def getText: Option[String] = handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/text")).decode[StringResponse].value

  def getAttribute(attribute: String): Option[String] = {
    handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/attribute/$attribute")).decode[StringResponse].value
  }

  def click(): Unit = handleRequest(elementSessionUrl, driver.doPost(s"$elementSessionUrl/click", ElementClickRequest(elementId).asJson))

  def clear(): Unit = handleRequest(elementSessionUrl, driver.doPost(s"$elementSessionUrl/clear", ElementClearRequest(elementId, sessionId).asJson))

  def isEnabled: Boolean = handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/enabled")).decode[BooleanResponse].value

}

class StubWebElement() extends Element {

  var attr: Option[String] = None

   def withAttribute(attribute: String) = {
     attr = Some(attribute)
     this
   }

   def getAttribute(attribute: String) = attr
}

object StubWebElement {
  def apply() = new StubWebElement()
}
