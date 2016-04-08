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

    "Finding Elements" - {

      "findElement should find an element by Id" in {
        val (session, testDriver) = SessionHelper()

        val element = testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
                      .withPostResponseAction(PhantomResponses.findElementResponse, () => session.findElement(By.id("some-id")))

        testDriver.getPostRequest should be(PhantomRequests.findElementById)
        element.elementSessionUrl should be("/session/test-session-id/element/:wdc:1460015822532")
      }

      "findElement should find an element by className" in {
        val (session, testDriver) = SessionHelper()

        val element = testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
                      .withPostResponseAction(PhantomResponses.findElementResponse, () => session.findElement(By.className("some-classname")))

        testDriver.getPostRequest should be(PhantomRequests.findElementByClassName)
        element.elementSessionUrl should be("/session/test-session-id/element/:wdc:1460015822532")
      }

    }

    "Timeouts" - {

      "getGlobalTimeout should return the global timeout in milliseconds" in {
        val (session, _) = SessionHelper()
        session.getGlobalTimeout should be(5000)
      }

      "setGlobalTimeout should set the global timeout" in {
        val (session, _) = SessionHelper()
        session.setGlobalTimeout(10000)
        session.getGlobalTimeout should be(10000)
      }

    }

  }

}




