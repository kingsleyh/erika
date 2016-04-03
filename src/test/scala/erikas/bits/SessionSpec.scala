package erikas.bits

import argonaut.Json
import io.shaka.http._
import io.shaka.http.Status.OK
import org.scalatest.{FreeSpec, Matchers}

class SessionSpec extends FreeSpec with Matchers {

  "Session" - {

    "create should start a new phantomjs session" in {
      val testDriver = TestDriver("some-host",1234)
      testDriver.postResponse = Response(OK,entity = Some(Entity("""{"sessionId":"test-session-id"}""")))

      val session = new Session(testDriver)
      session.create()

      testDriver.getPostRequest.nospaces should be(PhantomRequests.create)

      session.sessionId should be("test-session-id")
      session.sessionUrl should be("/session/test-session-id")
    }

    "visitUrl should visit the supplied url" in {
      val testDriver = TestDriver("some-host",1234)
      val session = new Session(testDriver)

      testDriver.postResponse = Response(OK,entity = Some(Entity("""{"sessionId":"test-session-id"}""")))
      session.create()

      testDriver.postResponse = Response(OK,entity = Some(Entity("""{"sessionId":"test-session-id","status":0,"value":{}}""")))
      session.visitUrl("http://some-url")
      testDriver.getPostRequest.nospaces should be(PhantomRequests.visitUrl)
    }

//    "findElement should find an element" in {
//
//    }


  }


}




