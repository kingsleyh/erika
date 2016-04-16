package erikas.bits

import org.scalatest.{FreeSpec, Matchers}

class SessionSpec extends FreeSpec with Matchers {

  "Session" - {

    "create should start a new phantomjs session" in {
      val (session, testDriver) = SessionHelper()

      testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
                .getPostRequest should be(PhantomRequests.create)

      testDriver.getRequestUrl should be("/session")

      session.sessionId should be("test-session-id")
      session.sessionUrl should be("/session/test-session-id")
    }

    "visitUrl should visit the supplied url" in {
      val (session, testDriver) = SessionHelper()

      testDriver.withPostResponse(PhantomResponses.sessionResponse,  () => session.create())
                .withPostResponse(PhantomResponses.visitUrlResponse, () => session.visitUrl("http://some-url"))
                .getPostRequest should be(PhantomRequests.visitUrl)

      testDriver.getRequestUrl should be("/session/test-session-id/url")

    }

    "getSessions should find a list of sessions" in {
      val (session, testDriver) = SessionHelper()

      val expectedResponse = List(Sessions("82bed430-fdcf-11e5-ab4d-4bc17e26c21d",Capabilities("phantomjs","mac-unknown-32bit","1.9.2",true,true,false,false,false,false,false,true,false,false,false,true)))

      testDriver.withPostResponse(PhantomResponses.sessionResponse,          () => session.create())
                .withGetResponseAction(PhantomResponses.getSessionsResponse, () => session.getSessions) should be(expectedResponse)

      testDriver.getRequestUrl should be("/sessions")

    }

    "getStatus should find the status of the server" in {
      val (session, testDriver) = SessionHelper()

      val expectedResponse = ServerStatus(Map("version" -> "1.0.4"),Map("name" -> "mac", "version" -> "unknown", "arch" -> "32bit"))

      testDriver.withPostResponse(PhantomResponses.sessionResponse,        () => session.create())
                .withGetResponseAction(PhantomResponses.getStatusResponse, () => session.getStatus) should be(expectedResponse)

      testDriver.getRequestUrl should be("/status")

    }

    "getCapabilities should find the capabilities of the server" in {
      val (session, testDriver) = SessionHelper()

      val expectedResponse = Capabilities("phantomjs","mac-unknown-32bit","1.9.2",true,true,false,false,false,false,false,true,false,false,false,true,Proxy())

      testDriver.withPostResponse(PhantomResponses.sessionResponse,      () => session.create())
        .withGetResponseAction(PhantomResponses.getCapabilitiesResponse, () => session.getCapabilities) should be(expectedResponse)

      testDriver.getRequestUrl should be("/session/test-session-id")
    }

    "getWindowHandles should find a list of handles" in {
      val (session, testDriver) = SessionHelper()

      val expectedResponse = List(WindowHandle("2c6d57d0-fe8f-11e5-ab4d-4bc17e26c21d"))

      testDriver.withPostResponse(PhantomResponses.sessionResponse,       () => session.create())
        .withGetResponseAction(PhantomResponses.getWindowHandlesResponse, () => session.getWindowHandles) should be(expectedResponse)

      testDriver.getRequestUrl should be("/session/test-session-id/window_handles")
    }

    "getWindowHandle should find the current window handle" in {
      val (session, testDriver) = SessionHelper()

      val expectedResponse = WindowHandle("2c6d57d0-fe8f-11e5-ab4d-4bc17e26c21d")

      testDriver.withPostResponse(PhantomResponses.sessionResponse,       () => session.create())
        .withGetResponseAction(PhantomResponses.getWindowHandleResponse,  () => session.getWindowHandle) should be(expectedResponse)

      testDriver.getRequestUrl should be("/session/test-session-id/window_handle")
    }

    "getUrl should find the url of the current page" in {
      val (session, testDriver) = SessionHelper()

      testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
        .withGetResponseAction(PhantomResponses.getUrlResponse,     () => session.getUrl) should be(Some("http://someurl.com/"))

      testDriver.getRequestUrl should be("/session/test-session-id/url")
    }

    "getSource should find the source of the current page" in {
      val (session, testDriver) = SessionHelper()

      testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
        .withGetResponseAction(PhantomResponses.getSourceResponse,     () => session.getSource) should be(Some("source"))

      testDriver.getRequestUrl should be("/session/test-session-id/source")
    }

    "getTitle should find the title of the current page" in {
      val (session, testDriver) = SessionHelper()

      testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
        .withGetResponseAction(PhantomResponses.getTitleResponse,   () => session.getTitle) should be(Some("title"))

      testDriver.getRequestUrl should be("/session/test-session-id/title")
    }

    "executeScript should instruct the server to perform a script execution" in {
      val (session, testDriver) = SessionHelper()
      val script = "function(){ return 1+1;}"
      testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
        .withPostResponse(PhantomResponses.anyResponse,             () => session.executeScript(script))
        .getPostRequest should be(PhantomRequests.executeScript)
      testDriver.getRequestUrl should be("/session/test-session-id/execute")
    }

    "executeAsyncScript should instruct the server to perform an async script execution" in {
      val (session, testDriver) = SessionHelper()
      val script = "function(){ return 1+1;}"
      testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
        .withPostResponse(PhantomResponses.anyResponse,             () => session.executeAsyncScript(script))
        .getPostRequest should be(PhantomRequests.executeScript)
      testDriver.getRequestUrl should be("/session/test-session-id/execute_async")
    }

    "With nothing to assert" - {

      "dispose should instruct the server to delete the current session" in {
        val (session, testDriver) = SessionHelper()

        testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
          .withDeleteResponse(PhantomResponses.anyResponse,           () => session.dispose)
          .getRequestUrl should be("/session/test-session-id")
      }

      "goForward should instruct the server to perform a browser forward" in {
        val (session, testDriver) = SessionHelper()

        testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
          .withPostResponse(PhantomResponses.anyResponse,             () => session.goForward)
          .getRequestUrl should be("/session/test-session-id/forward")
      }

      "goBack should instruct the server to perform a browser back" in {
        val (session, testDriver) = SessionHelper()

        testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
          .withPostResponse(PhantomResponses.anyResponse,             () => session.goBack)
          .getRequestUrl should be("/session/test-session-id/back")
      }

      "refresh should instruct the server to perform a browser refresh" in {
        val (session, testDriver) = SessionHelper()

        testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
          .withPostResponse(PhantomResponses.anyResponse,             () => session.refresh)
          .getRequestUrl should be("/session/test-session-id/refresh")
      }

      "getActiveElement seems to be broken in phantomjs" in {
        val (session, testDriver) = SessionHelper()

        testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
          .withGetResponse(PhantomResponses.anyResponse, () => session.getActiveElement)
          .getRequestUrl should be("/session/test-session-id/element/active")
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
        testDriver.getRequestUrl should be("/session/test-session-id/elements")
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

      "setTimeout should instruct the server to set a timeout" in {
        val (session, testDriver) = SessionHelper()

        testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
          .withPostResponse(PhantomResponses.anyResponse,             () => session.setTimeout(TimeoutType.PAGE_LOAD, 1000))
          .getPostRequest should be(PhantomRequests.setTimeout)
        testDriver.getRequestUrl should be("/session/test-session-id/timeouts")
      }

      "setAsyncScriptTimeout should instruct the server to set the async timeout" in {
        val (session, testDriver) = SessionHelper()

        testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
          .withPostResponse(PhantomResponses.anyResponse,             () => session.setAsyncScriptTimeout(1000))
          .getPostRequest should be(PhantomRequests.setTimeoutValue)
        testDriver.getRequestUrl should be("/session/test-session-id/timeouts/async_script")
      }

      "setImplicitWaitTimeout should instruct the server to set the implicit timeout" in {
        val (session, testDriver) = SessionHelper()

        testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
          .withPostResponse(PhantomResponses.anyResponse,             () => session.setImplicitWaitTimeout(1000))
          .getPostRequest should be(PhantomRequests.setTimeoutValue)
        testDriver.getRequestUrl should be("/session/test-session-id/timeouts/implicit_wait")
      }

    }

    "Wait For" - {

      "should wait for a condition on an element" in {
        val (session, testDriver) = SessionHelper()

        val element = testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
          .withPostResponseAction(PhantomResponses.findElementResponse, () => session.findElement(By.className("some-classname")))

        val notFoundResponses = makeResponses(10, """{"sessionId":"test-session-id","status":0,"value":"Kingsleyx"}""")
        val foundResponses = makeResponses(2, """{"sessionId":"test-session-id","status":0,"value":"Kingsley"}""")

        testDriver.withGetResponses(notFoundResponses ++ foundResponses, () => session.waitFor(element, Condition.attributeContains("name", "Kingsley")))
      }

      "should wait for a condition with a By" in {
        val (session, testDriver) = SessionHelper()

        val notFoundResponses = makeResponses(4, """{"sessionId":"test-session-id","status":0,"value":"Kingsleyx"}""")
        val foundResponses = makeResponses(2, """{"sessionId":"test-session-id","status":0,"value":"Kingsley"}""")

        testDriver
            .withPostResponses(makeResponses(6, PhantomResponses.findElementsResponse))
          .withGetResponses(notFoundResponses ++ foundResponses, () => session.waitFor(By.className("some-classname"), Condition.attributeContains("name", "Kingsley")))
      }


      "should wait for a function to return a Result" in {
        val (session, testDriver) = SessionHelper()

        val notFoundResponses = makeResponses(4, """{"sessionId":"test-session-id","status":0,"value":"Kingsleyx"}""")
        val foundResponses = makeResponses(1, """{"sessionId":"test-session-id","status":0,"value":"Kingsley"}""")

        val func = () => {
          val attr: Option[String] = session.findElement(By.className("some-classname")).getAttribute("name")
          Result(attr.contains("Kingsley"), "Error could not find attribute: name")
        }

        testDriver
          .withPostResponses(makeResponses(6, PhantomResponses.findElementResponse))
          .withGetResponses(notFoundResponses ++ foundResponses, () => session.waitForFunction(func))
      }

      "should wait for url" in {
        val (session, testDriver) = SessionHelper()

        val notFoundResponses = makeResponses(4, """{"sessionId":"b3fb3a40-fe90-11e5-ab4d-4bc17e26c21d","status":0,"value":"http://not-yet.com/"}""")
        val foundResponses = makeResponses(1, PhantomResponses.getUrlResponse)

        testDriver
          .withGetResponses(notFoundResponses ++ foundResponses, () => session.waitForUrl("http://someurl.com/"))
      }

    }

  }

  def AssertFindElementBy(expectedRequest: String, locator: By) = {
    val (session, testDriver) = SessionHelper()

    val element = testDriver.withPostResponse(PhantomResponses.sessionResponse, () => session.create())
      .withPostResponseAction(PhantomResponses.findElementResponse, () => session.findElement(locator))

    testDriver.getPostRequest should be(expectedRequest)
    element.elementSessionUrl should be("/session/test-session-id/element/:wdc:1460015822532")
    testDriver.getRequestUrl should be("/session/test-session-id/element")
  }

  private def makeResponses(number: Int, response: String) = 1.to(number).map(n => response).toList

}




