package net.kenro.ji.jin

import argonaut.Argonaut._
import argonaut.Json
import Driver.handleRequest
import ResponseUtils._
import io.shaka.http.Response

trait Element {
  def getAttributeOption(attribute: String): Option[String]
  def getAttribute(attribute: String): String
  def getTextOption: Option[String]
  def getText: String
  def getNameOption: Option[String]
  def getName: String
  def click(): WebElement
  def clear(): WebElement
  def isEnabled: Boolean
  def isDisplayed: Boolean
  def isPresent: Boolean
  def sendKeys(text: String): WebElement
}

case class IncorrectElementException(message: String) extends Exception(message)

case class TextInput(elementId :String, sessionId: String, sessionUrl: String, driver: BaseDriver, session: Session)
  extends WebElement(elementId, sessionId, sessionUrl, driver, session) {
  def getValue = waitFor(this).getAttribute("value")
  def setValue(value: String) = waitFor(this).clear().sendKeys(value)
  def clearValue = waitFor(this).clear()
}

class Button(elementId :String, sessionId: String, sessionUrl: String, driver: BaseDriver, session: Session)
  extends WebElement(elementId, sessionId, sessionUrl, driver, session) {
  def getValue = waitFor(this).getAttribute("value")
  override def click(): Button = {
    waitFor(this)
    handleRequest(elementSessionUrl, driver.doPost(s"$elementSessionUrl/click", ElementClickRequest(elementId).asJson))
    this
  }
}

case class TextArea(elementId :String, sessionId: String, sessionUrl: String, driver: BaseDriver, session: Session)
  extends WebElement(elementId, sessionId, sessionUrl, driver, session) {
  def getValue = waitFor(this).getAttribute("value")
  def setValue(value: String) = waitFor(this).clear().sendKeys(value)
  def clearValue = waitFor(this).clear()
  }

case class Link(elementId :String, sessionId: String, sessionUrl: String, driver: BaseDriver, session: Session)
  extends WebElement(elementId, sessionId, sessionUrl, driver, session) {
  override def getText = waitFor(this).getAttribute("text")
  override def click() = {
    waitFor(this)
    handleRequest(elementSessionUrl, driver.doPost(s"$elementSessionUrl/click", ElementClickRequest(elementId).asJson))
    this
  }
}

class WebElement(elementId :String, sessionId: String, sessionUrl: String, driver: BaseDriver, session: Session) extends Searcher with Element {

  val elementSessionUrl = s"$sessionUrl/element/$elementId"

  def isTextInput = getName == "input" && List("text","password","email").contains(getAttribute("type"))

  def isButton = getName == "button" || (getName == "input" && getAttribute("type") == "submit")

  def isTextArea = getName == "textarea"

  def isLink = getName == "a"

  def toTextInput = {
    if(!isTextInput) throw IncorrectElementException(s"WebElement was not a <text input>. It is a: $getName")
    TextInput(elementId, sessionId, sessionUrl, driver, session)
  }

  def toButton = {
    if(!isButton) throw IncorrectElementException(s"WebElement was not a <text input>. It is a: $getName")
    new Button(elementId, sessionId, sessionUrl, driver, session)
  }

  def toTextArea = {
    if(!isTextArea) throw IncorrectElementException(s"WebElement was not a <textarea>. It is a: $getName")
    new TextArea(elementId, sessionId, sessionUrl, driver, session)
  }

  def toLink = {
    if(!isLink) throw IncorrectElementException(s"WebElement was not an <a>. It is a: $getName")
    new Link(elementId, sessionId, sessionUrl, driver, session)
  }

  def getTextOption: Option[String] = handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/text")).decode[StringResponse].value

  def getText: String = getTextOption.getOrElse("")

  def getAttributeOption(attribute: String): Option[String] = {
    handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/attribute/$attribute")).decode[StringResponse].value
  }

  def getAttribute(attribute: String): String = getAttributeOption(attribute).getOrElse("")

  def getNameOption: Option[String] = handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/name")).decode[StringResponse].value

  def getName = getNameOption.getOrElse("")

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

  def sendKeys(key: Keys.Value*): WebElement = {
    val values = key.flatMap(_.toString.toCharArray).toList
    handleRequest(elementSessionUrl, driver.doPost(s"$elementSessionUrl/value", SendKeysRequest(values).asJson))
    this
  }

  def findElement(by: By): WebElement = {
    val elementId = handleRequest(sessionUrl, driver.doPost(elementSessionUrl, FindElementRequest(by.locatorStrategy, by.value).asJson))
      .response.decode[ElementResponse].value.get("ELEMENT") match {
      case None => throw APIResponseError("oops")
      case Some(ele) => ele
    }

    new WebElement(elementId, sessionId, sessionUrl, driver, session)
  }

  def findElements(by: By): List[WebElement] = {
    val elementIds = handleRequest(sessionUrl, driver.doPost(elementSessionUrl, FindElementRequest(by.locatorStrategy, by.value).asJson))
      .response.decode[ElementResponses].value.map(er => er.get("ELEMENT"))

    for {
      maybeElementId <- elementIds
      elementId      <- maybeElementId

    } yield new WebElement(elementId,sessionId, sessionUrl, driver, session)
  }

  def waitFor[T <: Searcher](element: T, condition: Condition = Condition.isClickable, timeout: Int = session.getGlobalTimeout): WebElement = {
    Waitress(session).waitFor(element, condition, timeout)
  }

}

class StubWebElement() extends Element {

  var attr: Option[String] = None

   def withAttribute(attribute: String) = {
     attr = Some(attribute)
     this
   }

  override def getAttributeOption(attribute: String) = attr

  override def getAttribute(attribute: String) = attr.getOrElse("")

  override def getTextOption: Option[String] = ???

  override def getText: String = ???

  override def getNameOption: Option[String] = ???

  override def getName: String = ???

  override def sendKeys(text: String): WebElement = ???

  override def isPresent: Boolean = ???

  override def isEnabled: Boolean = ???

  override def clear(): WebElement = ???

  override def click(): WebElement = ???

//  override def click2(): Unit = ???

  override def isDisplayed: Boolean = ???
}

object StubWebElement {
  def apply() = new StubWebElement()
}
