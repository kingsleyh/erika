package erikas.bits

import io.shaka.http.Status.OK
import io.shaka.http._
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

    "findElement should find an element" in {
      val testDriver = TestDriver("some-host",1234)
      val session = new Session(testDriver)

      testDriver.postResponse = Response(OK,entity = Some(Entity("""{"sessionId":"test-session-id"}""")))
      session.create()

      testDriver.postResponse = Response(OK,entity = Some(Entity("""{"sessionId":"test-session-id","status":0,"value":{"ELEMENT":":wdc:1460015822532"}}""")))
      val elementId = session.findElement(By.id("some-id"))
      testDriver.getPostRequest.nospaces should be(PhantomRequests.findElement)

      elementId should be(":wdc:1460015822532")

    }


  }


}




