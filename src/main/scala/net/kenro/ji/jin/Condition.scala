package net.kenro.ji.jin

trait Searcher

trait Condition {
  def isSatisfied(webElements: List[Element]): Boolean

  def toString: String
}

object Condition {

  def titleIs(expectedValue: String) = new WithCondition(
    (webElements: List[Element]) => webElements.flatMap(e => e.getAttributeOption("text")).contains(expectedValue),
    s"TitleIsCondition, expectedTitle: $expectedValue")

  def attributeContains(attribute: String, expectedValue: String) = new WithCondition(
    (webElements: List[Element]) =>  webElements.flatMap(e => e.getAttributeOption(attribute)).contains(expectedValue),
    s"AttributeContainsCondition, attributeName: $attribute expectedValue: $expectedValue")

  def isClickable = new WithCondition(
    (webElements: List[Element]) => webElements.exists(e => e.isPresent && e.isEnabled),
    "Is clickable condition")

  def isPresent = new WithCondition(
    (webElements: List[Element]) => webElements.exists(e => e.isPresent),
    "Is Present condition")

  def isEnabled = new WithCondition(
    (webElements: List[Element]) => webElements.exists(e => e.isEnabled),
    "Is Enabled condition")

  def isVisible = new WithCondition(
    (webElements: List[Element]) => webElements.exists(e => e.isDisplayed),
    "Is Visible condition")

  def textContains(expectedValue: String) = new WithCondition(
    (webElements: List[Element]) => webElements.flatMap(e => e.getTextOption).contains(expectedValue),
    s"TextContains with expectedValue: $expectedValue")

  def textStartsWith(expectedValue: String) = new WithCondition(
    (webElements: List[Element]) => webElements.flatMap(e => e.getText).startsWith(expectedValue),
    s"TextStartsWith with expectedValue: $expectedValue")

  def textEndsWith(expectedValue: String) = new WithCondition(
    (webElements: List[Element]) => webElements.flatMap(e => e.getText).endsWith(expectedValue),
    s"TextEndssWith with expectedValue: $expectedValue")

  // TODO - valueContains, hasAttribute

}

class WithCondition(f: List[Element] => Boolean, string: String) extends Condition {
  override def isSatisfied(webElements: List[Element]): Boolean = {
    if(webElements.isEmpty) false else f(webElements)
  }

  override def toString: String = string
}
