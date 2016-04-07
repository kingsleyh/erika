package erikas.bits

import org.scalatest.{FreeSpec, Matchers}

class SessionSpec extends FreeSpec with Matchers {

  "Session" - {

    "create should start a new phantomjs session" in {
      val (session, testDriver) = SessionHelper()

      testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
                .getPostRequest should be(PhantomRequests.create)

      session.sessionId should be("test-session-id")
      session.sessionUrl should be("/session/test-session-id")
    }

    "visitUrl should visit the supplied url" in {
      val (session, testDriver) = SessionHelper()

      testDriver.withPostResponse(PhantomResponses.sessionResponse,  () => session.create())
                .withPostResponse(PhantomResponses.visitUrlResponse, () => session.visitUrl("http://some-url"))
                .getPostRequest should be(PhantomRequests.visitUrl)

    }

    "findElement should find an element" in {
      val (session, testDriver) = SessionHelper()

      testDriver.withPostResponse(PhantomResponses.sessionResponse,     () => session.create())
                .withPostResponse(PhantomResponses.findElementResponse, () => session.findElement(By.id("some-id")))
                .getPostRequest should be(PhantomRequests.findElement)

    }

  }

}




