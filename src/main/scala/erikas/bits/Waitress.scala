package erikas.bits

case class TimeoutException(message: String) extends Exception(message)

class Waitress(session: Session) {

  def waitFor[T](searcher: T, condition: Condition, timeout: Int) = {
    searcher match {
      case By(_,_) => waitForResult(searcher.asInstanceOf[By], count = 0, condition, timeout)
      case _ => waitForElementResult(searcher.asInstanceOf[WebElement], count = 0, condition, timeout)
    }
  }

  private def waitForElementResult(element: WebElement, count: Int, condition: Condition, timeout: Int): Unit = {
   var counter = count
    if(counter >= timeout){
     throw TimeoutException(s"Timed out while waiting for condition: $condition for: $element")
   } else {
     Thread.sleep(100)
     if (!condition.isSatisfied(List(element))){
       counter = counter + 100
       waitForElementResult(element, counter, condition, timeout)
     }
   }
  }

  private def waitForResult(by: By, count: Int, condition: Condition, timeout: Int): Unit = {
    var counter = count
    if(counter >= timeout){
      throw TimeoutException(s"Timed out while waiting for condition: $condition for: $by")
    } else {
      Thread.sleep(100)
      val elements = session.findElements(by)
      if(!(elements.nonEmpty && condition.isSatisfied(elements))){
        counter = counter + 100
        waitForResult(by, counter, condition, timeout)
      }
    }
  }

}

object Waitress{
  def apply(session: Session) = new Waitress(session)
}