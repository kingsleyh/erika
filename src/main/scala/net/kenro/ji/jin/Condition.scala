package net.kenro.ji.jin

trait Searcher

trait Condition {
  def isSatisfied(webElements: List[Element]): Boolean

  def toString: String
}

object Condition {

  def titleIs(expectedTitle: String): TitleIsCondition = new TitleIsCondition(expectedTitle)

  def attributeContains(attribute: String, expectedValue: String) = new AttributeContainsCondition(attribute, expectedValue)

  def isClickable = new IsClickableCondition()

  def isPresent = new IsClickableCondition()

  def textContains(expectedValue: String) = new TextContainsCondition(expectedValue)

  // TODO - valueContains, isVisible, hasAttribute

}

class TitleIsCondition(expectedTitle: String) extends Condition {
  override def isSatisfied(webElements: List[Element]) = {
    if (webElements.isEmpty) false else webElements.flatMap(e => e.getAttributeOption("text")).contains(expectedTitle)
  }

  override def toString: String = s"TitleIsCondition, expectedTitle: $expectedTitle"
}

class AttributeContainsCondition(attributeName: String, expectedValue: String) extends Condition {
  override def isSatisfied(webElements: List[Element]): Boolean = {
    if(webElements.isEmpty) false else {
      webElements.flatMap(e => e.getAttributeOption(attributeName)).contains(expectedValue)
    }
  }

  override def toString: String = s"AttributeContainsCondition, attributeName: $attributeName expectedValue: $expectedValue"

}

class TextContainsCondition(expectedValue: String) extends Condition {
  override def isSatisfied(webElements: List[Element]): Boolean = {
    if(webElements.isEmpty) false else {
      webElements.flatMap(e => e.getTextOption).contains(expectedValue)
    }
  }

  override def toString: String = s"TextContainsCondition expectedValue: $expectedValue"
}

class IsClickableCondition extends Condition {
  override def isSatisfied(webElements: List[Element]): Boolean = {
    if(webElements.isEmpty) false else {
      webElements.exists(e => e.isPresent)
    }
  }

  override def toString: String = "Is clickable condition"
}