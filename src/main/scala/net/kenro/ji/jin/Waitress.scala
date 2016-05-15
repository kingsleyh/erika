package net.kenro.ji.jin

case class TimeoutException(message: String) extends Exception(message)

class Waitress(session: Session) {

  def waitFor[T](searcher: T, condition: Condition, timeout: Int): WebElement = {
    searcher match {
      case By(_,_) => waitForResult(searcher.asInstanceOf[By], count = 0, condition, timeout)
      case _ => waitForElementResult(searcher.asInstanceOf[WebElement], count = 0, condition, timeout)
    }
  }

  def waitFor(runnable: () => Result, timeout: Int) = {
   waitForFunction(runnable, timeout, count = 0)
  }

  def waitAndFindFirst[T](searcher: T, condition: Condition, timeout: Int): Option[WebElement] = {
      searcher match {
        case By(_,_) => waitForFirst(searcher.asInstanceOf[By], count = 0, condition, timeout)
        case _ => waitForFirstElement(searcher.asInstanceOf[WebElement], count = 0, condition, timeout)
      }
    }

  def waitAndFindAll(by: By, condition: Condition, timeout: Int): List[WebElement] = {
    waitForAll(by, count = 0, condition, timeout)
  }

  private def waitForFunction(runnable: () => Result, timeout: Int, count: Int): Unit = {
    var counter = count
    val result = runnable()
    if(counter >= timeout){
      throw TimeoutException(s"Timed out while waiting for function to evaluate: \n ${result.message}")
    } else {
       if(!result.outcome){
         Thread.sleep(100)
         counter = counter + 100
         waitForFunction(runnable, timeout, counter)
       }
    }
  }

  private def waitForElementResult(element: WebElement, count: Int, condition: Condition, timeout: Int): WebElement = {
   var counter = count
    if(counter >= timeout){
     throw TimeoutException(s"Timed out while waiting for condition: $condition for: $element")
   } else {
     if (!condition.isSatisfied(List(element))){
       Thread.sleep(100)
       counter = counter + 100
       waitForElementResult(element, counter, condition, timeout)
     } else {
       element
     }
   }
  }

  private def waitForFirstElement(element: WebElement, count: Int, condition: Condition, timeout: Int): Option[WebElement] = {
    var counter = count
     if(counter >= timeout){
      None
    } else {
      if (!condition.isSatisfied(List(element))){
        Thread.sleep(100)
        counter = counter + 100
        waitForFirstElement(element, counter, condition, timeout)
      } else {
        Some(element)
      }
    }
   }

  private def waitForResult(by: By, count: Int, condition: Condition, timeout: Int): WebElement = {
    var counter = count
    if(counter >= timeout){
      throw TimeoutException(s"Timed out while waiting for condition: $condition for: $by")
    } else {
      val elements = session.findElements(by)
      if(!(elements.nonEmpty && condition.isSatisfied(elements))){
        Thread.sleep(100)
        counter = counter + 100
        waitForResult(by, counter, condition, timeout)
      } else {
        elements.filter(e => condition.isSatisfied(List(e))).head
      }
    }
  }

  private def waitForFirst(by: By, count: Int, condition: Condition, timeout: Int): Option[WebElement] = {
   waitForAll(by, count, condition, timeout).headOption
  }

  private def waitForAll(by: By, count: Int, condition: Condition, timeout: Int): List[WebElement] = {
      var counter = count
      if(counter >= timeout){
        Nil
      } else {
        val elements = session.findElements(by)
        if(!(elements.nonEmpty && condition.isSatisfied(elements))){
          Thread.sleep(100)
          counter = counter + 100
          waitForAll(by, counter, condition, timeout)
        } else {
          elements
        }
      }
    }

}

object Waitress{
  def apply(session: Session) = new Waitress(session)
}

case class Result(outcome: Boolean, message: String)