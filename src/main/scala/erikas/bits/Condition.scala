package erikas.bits

trait Searcher

trait Condition {
  def isSatisfied(webElements: List[WebElement]): Boolean

  def toString: String
}

object Condition {

  def titleIs(expectedTitle: String): TitleIsCondition = new TitleIsCondition(expectedTitle)

  def attributeContains(attribute: String, expectedValue: String) = new AttributeContainsCondition(attribute, expectedValue)

}

class TitleIsCondition(expectedTitle: String) extends Condition {
  override def isSatisfied(webElements: List[WebElement]) = {
    if (webElements.isEmpty) webElements.head.getAttribute("text").contains(expectedTitle)
    else {
      false
    }
  }

  override def toString: String = s"TitleIsCondition, expectedTitle: $expectedTitle"
}

class AttributeContainsCondition(attributeName: String, expectedValue: String) extends Condition {
  override def isSatisfied(webElements: List[WebElement]): Boolean = {
    if(webElements.isEmpty) false else {
      println("attr was: " + webElements.head.getAttribute("text"))
      webElements.head.getAttribute(attributeName).contains(expectedValue)
    }
  }

  override def toString: String = s"AttributeContainsCondition, attributeName: $attributeName expectedValue: $expectedValue"

}