package net.kenro.ji.jin

import io.shaka.http.Response
import io.shaka.http.Status.OK

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

  def tryHttp(runnable: () => Response): Response = {
    if (count >= millis) {
      throw TimeoutException(s"Timed out after $millis millis - waiting for request to return with HTTP 200, got exception: \n $ex")
    }
    val response = runnable()
    response match {
      case r@Response(OK, _, _) => r
      case _ => tryHttp(runnable)
    }
  }

}

object Eventually {
  def apply(millis: Int = 5000) = new Eventually(millis)
}
