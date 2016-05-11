package net.kenro.ji.jin

import scala.util.control.NonFatal

class Eventually(val millis: Int = 5000) {

  var count: Int = 0
  var ex: Throwable = null

  def tryExecute(runnable: () => Unit) {
    if (count >= millis) {
      throw TimeoutException(s"Timed out after $millis millis - waiting for process to complete without error, got exception: \n $ex")
    }
    try {
      runnable()
    } catch {
      case NonFatal(throwable) => {
        ex = throwable
        Thread.sleep(100)
        count = count + 100
        tryExecute(runnable)
      }
    }
  }

}

object Eventually {
  def apply(millis: Int = 5000) = new Eventually(millis)
}
