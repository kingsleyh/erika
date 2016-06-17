package net.kenro.ji.jin

import argonaut.Argonaut._
import argonaut.{DecodeJson, Json}
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
  def getValue: String = waitFor(this).getAttribute("value")
  def setValue(value: String): WebElement = waitFor(this).clear().sendKeys(value)
  def clearValue: WebElement = waitFor(this).clear()
}

case class Button(elementId :String, sessionId: String, sessionUrl: String, driver: BaseDriver, session: Session)
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

case class Radio(elementId :String, sessionId: String, sessionUrl: String, driver: BaseDriver, session: Session)
  extends WebElement(elementId, sessionId, sessionUrl, driver, session) {
  def getValue = waitFor(this).getAttribute("value")
  override def isSelected = {
    waitFor(this)
    handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/selected")).decode[BooleanResponse].value
  }
  override def click(): Radio = {
    waitFor(this)
    handleRequest(elementSessionUrl, driver.doPost(s"$elementSessionUrl/click", ElementClickRequest(elementId).asJson))
    this
  }
}

case class RadioMulti(radios: List[WebElement]) {
  def getSelected: Option[Radio] = getRadios.find(r => r.isSelected)
  def getSelectedValue: String = getSelected.map(v => v.getValue).getOrElse("")
  def hasSelected: Boolean = getSelected.nonEmpty
  def getByValue(value: String) = getRadios.find(r => r.getAttribute("value") == value)
  def getRadios: List[Radio] = radios.map(e => e.toRadio)
  def selectByValue(value: String) = getByValue(value).map(e => e.click())
}

object ListOfWebElementUtils {
  implicit class ListDecorator(list: List[Element]) {
    def toRadioMulti = {

      val radios = list.filterNot(ele => ele.asInstanceOf[WebElement].isRadio)
      if(radios.nonEmpty) throw IncorrectElementException(s"All elements must be radios - at least one was not of type radio")
      if(list.isEmpty) throw IncorrectElementException(s"You have an empty list - at least one radio is expected")

      val name = list.head.getAttribute("name")

      val sameNames = list.filterNot(ele => ele.getAttribute("name") == name)
      if(sameNames.nonEmpty) throw IncorrectElementException(s"All radio elements should have the same name for a multi - at least one had a different name")

      RadioMulti(list.map(ele => ele.asInstanceOf[WebElement]))

    }
  }
}

class WebElement(elementId :String, sessionId: String, sessionUrl: String, driver: BaseDriver, session: Session) extends Searcher with Element {

  val elementSessionUrl = s"$sessionUrl/element/$elementId"

  def isTextInput = getName == "input" && List("text","password","email").contains(getAttribute("type"))

  def isButton = getName == "button" || (getName == "input" && getAttribute("type") == "submit")

  def isTextArea = getName == "textarea"

  def isLink = getName == "a"

  def isRadio = getName == "input" && getAttribute("type") == "radio"

  def toTextInput = {
    if(!isTextInput) throw IncorrectElementException(s"WebElement was not a <text input>. It is a: $getName")
    TextInput(elementId, sessionId, sessionUrl, driver, session)
  }

  def toButton = {
    if(!isButton) throw IncorrectElementException(s"WebElement was not a <text input>. It is a: $getName")
    Button(elementId, sessionId, sessionUrl, driver, session)
  }

  def toRadio = {
    if(!isRadio) throw IncorrectElementException(s"WebElement was not a <radio>. It is a: $getName")
    Radio(elementId, sessionId, sessionUrl, driver, session)
  }

   def toTextArea = {
    if(!isTextArea) throw IncorrectElementException(s"WebElement was not a <textarea>. It is a: $getName")
    TextArea(elementId, sessionId, sessionUrl, driver, session)
  }

  def toLink = {
    if(!isLink) throw IncorrectElementException(s"WebElement was not an <a>. It is a: $getName")
    Link(elementId, sessionId, sessionUrl, driver, session)
  }

  def getTextOption: Option[String] = {
    val response = handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/text"))
      handleStaleReference(response, () => response.decode[StringResponse].value)
  }

  def getText: String = getTextOption.getOrElse("")

  def getAttributeOption(attribute: String): Option[String] = {
    val response = handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/attribute/$attribute"))
    handleStaleReference(response, () => response.decode[StringResponse].value)
  }

  def getAttribute(attribute: String): String = getAttributeOption(attribute).getOrElse("")

  def getNameOption: Option[String] = {
    val response = handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/name"))
      handleStaleReference(response, () => response.decode[StringResponse].value)
  }

  def getName: String = getNameOption.getOrElse("")

  def click(): WebElement = {
    handleRequest(elementSessionUrl, driver.doPost(s"$elementSessionUrl/click", ElementClickRequest(elementId).asJson))
    this
  }

  def clear(): WebElement = {
    handleRequest(elementSessionUrl, driver.doPost(s"$elementSessionUrl/clear", ElementClearRequest(elementId, sessionId).asJson))
    this
  }

  def isEnabled: Boolean = {
    val response = handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/enabled"))
    handleStaleReferenceBoolean(response, () => response.decode[BooleanResponse].value)
  }

  def isDisplayed: Boolean = {
    val response = handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/displayed"))
    handleStaleReferenceBoolean(response, () => response.decode[BooleanResponse].value)
  }

  def isSelected: Boolean = {
    val response = handleRequest(elementSessionUrl, driver.doGet(s"$elementSessionUrl/selected"))
    handleStaleReferenceBoolean(response, () => response.decode[BooleanResponse].value)
  }

  def isPresent: Boolean = isEnabled && isDisplayed

  def submit: WebElement = {
    handleRequest(elementSessionUrl, driver.doPost(s"$elementSessionUrl/submit", ElementRequest(elementId, sessionId).asJson))
    this
  }

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
      case None => throw APIResponseError("was not an element")
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

  def waitFor[T <: Searcher](element: T, condition: Condition = Condition.isVisible, timeout: Int = session.getGlobalTimeout): WebElement = {
    Waitress(session).waitFor(element, condition, timeout)
  }

  def waitForFunction(runnable: () => Result, timeout: Int = session.getGlobalTimeout) = {
    Waitress(session).waitFor(runnable, timeout)
  }

  def findFirst[T <: Searcher](element: T, condition: Condition = Condition.isVisible, timeout: Int = session.getGlobalTimeout): Option[WebElement] = {
    Waitress(session).waitAndFindFirst(element, condition, timeout)
  }

  def findAll(by: By, condition: Condition = Condition.isVisible, timeout: Int = session.getGlobalTimeout): List[WebElement] = {
    Waitress(session).waitAndFindAll(by, condition, timeout)
  }

  // short methods
  def waitForClass(className: String, condition: Condition = Condition.isVisible, timeout: Int = session.getGlobalTimeout): WebElement = waitFor(By.className(className),condition, timeout)

  def waitForId(id: String, condition: Condition = Condition.isVisible, timeout: Int = session.getGlobalTimeout): WebElement = waitFor(By.id(id),condition, timeout)

  def waitForCss(cssSelector: String, condition: Condition = Condition.isVisible, timeout: Int = session.getGlobalTimeout): WebElement = waitFor(By.cssSelector(cssSelector),condition, timeout)

  def waitForXpath(xpath: String, condition: Condition = Condition.isVisible, timeout: Int = session.getGlobalTimeout): WebElement = waitFor(By.xpath(xpath),condition, timeout)

  def waitForName(name: String, condition: Condition = Condition.isVisible, timeout: Int = session.getGlobalTimeout): WebElement = waitFor(By.name(name),condition, timeout)

  def waitForLink(linkText: String, condition: Condition = Condition.isVisible, timeout: Int = session.getGlobalTimeout): WebElement = waitFor(By.linkText(linkText),condition, timeout)

  def waitForLinkP(partialLinkText: String, condition: Condition = Condition.isVisible, timeout: Int = session.getGlobalTimeout): WebElement = waitFor(By.partialLinkText(partialLinkText),condition, timeout)

  def waitForTag(tagName: String, condition: Condition = Condition.isVisible, timeout: Int = session.getGlobalTimeout): WebElement = waitFor(By.tagName(tagName),condition, timeout)

  private def handleStaleReference(response: Response, runnable: () => Option[String]): Option[String] = {
    val result = response.entityAsString.decodeOption[FailureResponse]
    val stale: Boolean = result.exists(r => r.state == "stale element reference")
    if(stale) Some("stale element reference") else runnable()
  }

  private def handleStaleReferenceBoolean(response: Response, runnable: () => Boolean): Boolean = {
    val result = response.entityAsString.decodeOption[FailureResponse]
    val stale: Boolean = result.exists(r => r.state == "stale element reference")
    if(stale) false else runnable()
  }



}

class StubWebElement() extends Element {

  var attr: Option[String] = None
  var text: Option[String] = None
  var present: Boolean = false
  var enabled: Boolean = false
  var displayed: Boolean = false

   def withAttribute(attribute: String) = {
     attr = Some(attribute)
     this
   }

  def withText(value: String) = {
    text = Some(value)
    this
  }

  def withPresent(value: Boolean) = {
   present = value
   this
  }

  def withEnabled(value: Boolean) = {
    enabled = value
    this
  }

  def withDisplayed(value: Boolean) = {
    displayed = value
    this
  }

  override def getAttributeOption(attribute: String) = attr

  override def getAttribute(attribute: String) = attr.getOrElse("")

  override def getTextOption: Option[String] = text

  override def getText: String = text.getOrElse("")

  override def getNameOption: Option[String] = ???

  override def getName: String = ???

  override def sendKeys(text: String): WebElement = ???

  override def isPresent: Boolean = present

  override def isEnabled: Boolean = enabled

  override def clear(): WebElement = ???

  override def click(): WebElement = ???

  override def isDisplayed: Boolean = displayed
}

object StubWebElement {
  def apply() = new StubWebElement()
}
