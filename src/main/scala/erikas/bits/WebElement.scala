package erikas.bits

import argonaut.Argonaut._
import erikas.bits.Driver.handleRequest
import erikas.bits.ResponseUtils._

trait Element {
  def getAttribute(attribute: String): Option[String]
  def getText: Option[String]
  def click(): WebElement
  def clear(): WebElement
  def isEnabled: Boolean
  def isDisplayed: Boolean
  def isPresent: Boolean
  def sendKeys(text: String): WebElement
}

class WebElement(elementId :String, sessionId: String, sessionUrl: String, driver: PhantomDriver, session: Session) extends Searcher with Element {

  val elementSessionUrl = s"$sessionUrl/element/$elementId"

  def getText: Option[String] = handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/text")).decode[StringResponse].value

  def getAttribute(attribute: String): Option[String] = {
    handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/attribute/$attribute")).decode[StringResponse].value
  }

  def click(): WebElement = {
    handleRequest(elementSessionUrl, driver.doPost(s"$elementSessionUrl/click", ElementClickRequest(elementId).asJson))
    this
  }

  def clear(): WebElement = {
    handleRequest(elementSessionUrl, driver.doPost(s"$elementSessionUrl/clear", ElementClearRequest(elementId, sessionId).asJson))
    this
  }

  def isEnabled: Boolean = handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/enabled")).decode[BooleanResponse].value

  def isDisplayed: Boolean = handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/displayed")).decode[BooleanResponse].value

  def isPresent: Boolean = isEnabled && isDisplayed

  def sendKeys(text: String): WebElement = {
    handleRequest(elementSessionUrl, driver.doPost(s"$elementSessionUrl/value", SendKeysRequest(text.toArray.toList).asJson))
    this
  }

}

class StubWebElement() extends Element {

  var attr: Option[String] = None

   def withAttribute(attribute: String) = {
     attr = Some(attribute)
     this
   }

  override def getAttribute(attribute: String) = attr

  override def getText: Option[String] = ???

  override def sendKeys(text: String): WebElement = ???

  override def isPresent: JsonBoolean = ???

  override def isEnabled: JsonBoolean = ???

  override def clear(): WebElement = ???

  override def click(): WebElement = ???

  override def isDisplayed: JsonBoolean = ???
}

object StubWebElement {
  def apply() = new StubWebElement()
}
