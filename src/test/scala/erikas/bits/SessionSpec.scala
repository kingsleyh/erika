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

    "getSessions should find a list of sessions" in {
      val (session, testDriver) = SessionHelper()

      val expectedResponse = List(Sessions("82bed430-fdcf-11e5-ab4d-4bc17e26c21d",Capabilities("phantomjs","mac-unknown-32bit","1.9.2",true,true,false,false,false,false,false,true,false,false,false,true)))

      testDriver.withPostResponse(PhantomResponses.sessionResponse,  () => session.create())
                .withGetResponseAction(PhantomResponses.getSessionsResponse, () => session.getSessions) should be(expectedResponse)

    }

    "Finding Elements" - {

      "findElement should find an element by Id" in {
        AssertFindElementBy(PhantomRequests.findElementById, By.id("some-id"))
      }

      "findElement should find an element by className" in {
        AssertFindElementBy(PhantomRequests.findElementByClassName, By.className("some-classname"))
      }

      "findElement should find an element by cssSelector" in {
        AssertFindElementBy(PhantomRequests.findElementByCssSelector, By.cssSelector(".some-selector"))
      }

      "findElement should find an element by name" in {
        AssertFindElementBy(PhantomRequests.findElementByName, By.name("name"))
      }

      "findElement should find an element by linkText" in {
        AssertFindElementBy(PhantomRequests.findElementByLinkText, By.linkText("some link text"))
      }

      "findElement should find an element by partialLinkText" in {
        AssertFindElementBy(PhantomRequests.findElementByPartialLinkText, By.partialLinkText("some partial text"))
      }

      "findElement should find an element by tagName" in {
        AssertFindElementBy(PhantomRequests.findElementByTagName, By.tagName("tagname"))
      }

      "findElement should find an element by xpath" in {
        AssertFindElementBy(PhantomRequests.findElementByCssSelector, By.xpath("//*path"))
      }

    }

    def AssertFindElementBy(expectedRequest: String, locator: By) = {
      val (session, testDriver) = SessionHelper()

      val element = testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
        .withPostResponseAction(PhantomResponses.findElementResponse, () => session.findElement(locator))

      testDriver.getPostRequest should be(expectedRequest)
      element.elementSessionUrl should be("/session/test-session-id/element/:wdc:1460015822532")
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




