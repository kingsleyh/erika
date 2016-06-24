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
      case NonFatal(throwable) =>
        ex = throwable
        Thread.sleep(100)
        count = count + 100
        tryExecute(runnable)
    }
  }

  // re-try http calls to see if a 200 response can be found
  // whatever the response after the retries return it anyway to be handled for stale references
  // on a case by case basis
  import util.control.Breaks._

  def reTryHttp(runnable: () => Response, maxLoop: Int = 3, restPeriod: Int = 500): Response = {
    var response: Response = null
    breakable {
      (0 to maxLoop).foreach(n => {

        try {
          response = runnable()
          if (response.status == OK) {
            break
          }
        } catch {
          case NonFatal(throwable) =>
            val exception = throwable
            println(s"[reTryHttp] Exception happened when making http call (continuing on anyway) - exception was: $exception")
        }

        println(s"[reTryHttp] waiting for $restPeriod millis before trying again")
        Thread.sleep(restPeriod)

      })
    }
    response
  }

  //  Example
  //  object Example extends App {
  //    val tryFunc: () => FunctionResult[String] = () => {
  //      val a = 1
  //      val outcome = a > 1
  //      if(outcome) FunctionResult[String](outcome, "cool") else FunctionResult[String](outcome, "yikes")
  //    }
  //    val r: FunctionResult[String] = Eventually().reTryFunction(tryFunc)
  //    println(r.payLoad)
  //  }
  def reTryFunction[T](runnable: () => FunctionResult[T], maxLoop: Int = 3, restPeriod: Int = 500): FunctionResult[T] = {
    var conclusion: FunctionResult[T] = null
    breakable {
      (1 to maxLoop).foreach(n => {
        try {
          conclusion = runnable()
          if (conclusion.wasSatisfied) {
            break
          }
        } catch {
          case NonFatal(throwable) =>
            val exception = throwable
            println(s"[reTryFunction] Exception happened when calling function (continuing on anyway) - exception was: $exception")
        }

        println(s"[reTryFunction] waiting for $restPeriod millis before trying again")
        Thread.sleep(restPeriod)
      })
    }
    conclusion
  }

}

case class FunctionResult[T](wasSatisfied: Boolean, payLoad: T)

object Eventually {
  def apply(millis: Int = 5000) = new Eventually(millis)
}


