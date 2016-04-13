package erikas.bits

trait Condition {
  def isSatisfied(webElements: List[WebElement]): Boolean
  def toString: String
}

object Condition {
  def titleIs(expectedTitle: String): TitleIsCondition = {
    new TitleIsCondition(expectedTitle)
  }
}

class TitleIsCondition(expectedTitle: String) extends Condition {
  override def isSatisfied(webElements: List[WebElement]) = {
    if(webElements.nonEmpty) webElements.head.getAttribute("text").contains(expectedTitle)
    else {
      false
    }
  }

  override def toString: String = s"TitleIsCondition, expectedTitle: $expectedTitle"
}