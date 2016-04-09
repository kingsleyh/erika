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

      testDriver.withPostResponse(PhantomResponses.sessionResponse,          () => session.create())
                .withGetResponseAction(PhantomResponses.getSessionsResponse, () => session.getSessions) should be(expectedResponse)

    }

    "getStatus should find the status of the server" in {
      val (session, testDriver) = SessionHelper()

      val expectedResponse = ServerStatus(Map("version" -> "1.0.4"),Map("name" -> "mac", "version" -> "unknown", "arch" -> "32bit"))

      testDriver.withPostResponse(PhantomResponses.sessionResponse,        () => session.create())
                .withGetResponseAction(PhantomResponses.getStatusResponse, () => session.getStatus) should be(expectedResponse)

    }

    "getCapabilities should find the capabilities of the server" in {
      val (session, testDriver) = SessionHelper()

      val expectedResponse = Capabilities("phantomjs","mac-unknown-32bit","1.9.2",true,true,false,false,false,false,false,true,false,false,false,true,Proxy())

      testDriver.withPostResponse(PhantomResponses.sessionResponse,      () => session.create())
        .withGetResponseAction(PhantomResponses.getCapabilitiesResponse, () => session.getCapabilities) should be(expectedResponse)

    }

    "getWindowHandles should find a list of handles" in {
      val (session, testDriver) = SessionHelper()

      val expectedResponse = List(WindowHandle("2c6d57d0-fe8f-11e5-ab4d-4bc17e26c21d"))

      testDriver.withPostResponse(PhantomResponses.sessionResponse,       () => session.create())
        .withGetResponseAction(PhantomResponses.getWindowHandlesResponse, () => session.getWindowHandles) should be(expectedResponse)

    }

    "getWindowHandle should find the current window handle" in {
      val (session, testDriver) = SessionHelper()

      val expectedResponse = WindowHandle("2c6d57d0-fe8f-11e5-ab4d-4bc17e26c21d")

      testDriver.withPostResponse(PhantomResponses.sessionResponse,       () => session.create())
        .withGetResponseAction(PhantomResponses.getWindowHandleResponse,  () => session.getWindowHandle) should be(expectedResponse)

    }

    "getUrl should find the url of the current page" in {
      val (session, testDriver) = SessionHelper()

      testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
        .withGetResponseAction(PhantomResponses.getUrlResponse,     () => session.getUrl) should be(Some("http://someurl.com/"))

    }

    "getSource should find the source of the current page" in {
      val (session, testDriver) = SessionHelper()

      testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
        .withGetResponseAction(PhantomResponses.getSourceResponse,     () => session.getSource) should be(Some("source"))

    }

    "getTitle should find the title of the current page" in {
      val (session, testDriver) = SessionHelper()

      testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
        .withGetResponseAction(PhantomResponses.getTitleResponse,     () => session.getTitle) should be(Some("title"))

    }

    "With nothing to assert" - {

      "dispose should instruct the server to delete the current session" in {
        val (session, testDriver) = SessionHelper()

        testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
          .withDeleteResponse(PhantomResponses.anyResponse,           () => session.dispose)
      }

      "goForward should instruct the server to perform a browser forward" in {
        val (session, testDriver) = SessionHelper()

        testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
          .withPostResponse(PhantomResponses.anyResponse,             () => session.goForward)
      }

      "goBack should instruct the server to perform a browser back" in {
        val (session, testDriver) = SessionHelper()

        testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
          .withPostResponse(PhantomResponses.anyResponse,             () => session.goBack)
      }

      "Refresh should instruct the server to perform a browser refresh" in {
        val (session, testDriver) = SessionHelper()

        testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
          .withPostResponse(PhantomResponses.anyResponse,             () => session.refresh)
      }

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
        AssertFindElementBy(PhantomRequests.findElementByXpath, By.xpath("//*path"))
      }

      "findElements should find all elements" in {
        val (session, testDriver) = SessionHelper()

        val element = testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
          .withPostResponseAction(PhantomResponses.findElementsResponse, () => session.findElements(By.id("some-id")))

        testDriver.getPostRequest should be(PhantomRequests.findElementById)
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

  def AssertFindElementBy(expectedRequest: String, locator: By) = {
    val (session, testDriver) = SessionHelper()

    val element = testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
      .withPostResponseAction(PhantomResponses.findElementResponse, () => session.findElement(locator))

    testDriver.getPostRequest should be(expectedRequest)
    element.elementSessionUrl should be("/session/test-session-id/element/:wdc:1460015822532")
  }

}




