import net.kenro.ji.jin.docs._

object Docs extends App {

  val cards = List(

    // Session
    Card("setGlobalTimeout", "Sets the timeout used in all the functions that perform an internal timeout while waiting for something", "Session",
      List(Param("Int","timeout","(in millis, defaults to 5000)",ParamType.INPUT)),
      List(Example("session.setGlobalTimeout(8000)")),
      List(Link("getGlobalTimeout","#getGlobalTimeout")),
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("getGlobalTimeout", "Gets the current value in millis of the global timeout", "Session",
      List(Param("Int","","",ParamType.OUTPUT)),
      List(Example("val timeout: Int = session.getGlobalTimeout")),
      List(Link("setGlobalTimeout", "#setGlobalTimeout")),
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("setAllTimeouts", "Sets all the timeouts to the supplied value in millis", "Session",
      List(Param("Int","timeout","(in millis)",ParamType.INPUT)),
      List(Example("session.setAllTimeouts(8000)")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("visitUrl", "Navigate to the supplied url", "Session",
      List(
        Param("String", "url", "", ParamType.INPUT),
        Param("Session", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.visitUrl(\"www.google.com\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("findElement", "Find an element using a By locator - throws an exception if not found within implicit timeout", "Session",
      List(
        Param("By", "by", "", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.findElement(By.className(\"some-class\"))")),
      List(Link("By","#By"), Link("WebElement", "#WebElement")),
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("findElements", "Find all elements using a By locator - does not throw an exception if nothing found just returns an empty list", "Session",
      List(
        Param("By", "by", "", ParamType.INPUT),
        Param("List[WebElement]", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.findElements(By.className(\"some-class\"))")),
      List(Link("By","#By"), Link("WebElement", "#WebElement")),
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("elementExists", "Uses findElements to ascertain if an element exists based on By locator", "Session",
      List(
        Param("By", "by", "", ParamType.INPUT),
        Param("Boolean", "", "", ParamType.OUTPUT)
      ),
      List(Example("val result: Boolean = session.elementExists(By.className(\"some-class\"))")),
      List(Link("By","#By")),
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("getSessions", "Shows a list of existing sessions started by whichever driver is used: e.g. phantom, chrome", "Session",
      List(
        Param("List[Sessions]", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.getSessions()")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("getStatus", "Describes the general status of the server", "Session",
      List(
        Param("ServerStatus", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.getStatus()")),
      List(Link("ServerStatus", "#ServerStatus")),
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("getCapabilities", "Return the current capability information", "Session",
      List(
        Param("Capabilities", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.getCapabilities()")),
      List(Link("Capabilities", "#Capabilities")),
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("dispose()", "Disposes/terminates the current session", "Session",
      List(
        Param("Unit", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.dispose()")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("getUrl", "Gets the current url of the page", "Session",
      List(
        Param("Option[String]", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.getUrl")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("getWindowHandles", "Gets a list of open windows", "Session",
      List(
        Param("List[WindowHandle]", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.getWindowHandles")),
      List(Link("WindowHandle", "#WindowHandle")),
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("getWindowHandle", "Gets the current window handle", "Session",
      List(
        Param("WindowHandle", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.getWindowHandle")),
      List(Link("WindowHandle", "#WindowHandle")),
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("goForward", "Operate the browsers go forward", "Session",
      List(
        Param("Unit", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.goForward")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("goBack", "Operate the browsers go back", "Session",
      List(
        Param("Unit", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.goBack")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("refresh", "Operate the browsers refresh", "Session",
      List(
        Param("Unit", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.refresh")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("getSource", "Get the source of the current page", "Session",
      List(
        Param("Option[String]", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.getSource")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("getTitle", "Get the title of the current page", "Session",
      List(
        Param("Option[String]", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.getTitle")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("executeScript", "Execute some javascript synchronously", "Session",
      List(
        Param("ExecuteScriptResponse", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.executeScript(\"return $('.name');\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("executeAsyncScript", "Execute some javascript asynchronously", "Session",
      List(
        Param("ExecuteScriptResponse", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.executeAsyncScript(\"alert('woop');\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("takeScreenshot", "Take a screenshot", "Session",
      List(
        Param("String", "outputFile", "(defaults to screenshot.png)", ParamType.INPUT),
        Param("Unit", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.takeScreenshot(\"some-file.png\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("setTimeout", "Set a timeout for a specific TimeoutType", "Session",
      List(
        Param("TimeoutType", "timeoutType", "", ParamType.INPUT),
        Param("Int", "milliseconds", "", ParamType.INPUT),
        Param("Unit", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.setTimeout(TimeoutType.IMPLICIT, 5000)")),
      List(Link("TimeoutType", "#TimeoutType")),
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("setAsyncScriptTimeout", "Set a timeout for async script execution", "Session",
      List(
        Param("Int", "milliseconds", "", ParamType.INPUT),
        Param("Unit", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.setAsyncScriptTimeout(5000)")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("setImplicitWaitTimeout", "Set a timeout for implicit wait", "Session",
      List(
        Param("Int", "milliseconds", "", ParamType.INPUT),
        Param("Unit", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.setImplicitWaitTimeout(5000)")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("waitFor", "Wait for condition to be satisfied", "Session",
      List(
        Param("T <: Searcher", "element", "(takes an element or a By locator)", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(
        Example(
          """
            | // using By locator with default Condition.isVisible
            | val ele1: WebElement = session.waitFor(By.id("username"))
            |
            | // using By locator with a Condition
            | val ele2: WebElement = session.waitFor(By.id("username"), Condition.isEnabled)
            |
            | // using an element
            | val ele3: WebElement = session.waitFor(ele1)
            |
            | // using an element with a Condition
            | val ele4: WebElement = session.waitFor(ele1, Condition.textContains("hello"))
            |
          """.stripMargin)
      ),
      List(Link("Condition","#Condition")),
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("findFirst", "Finds an option of the first element that matches the locator criteria\nThe finder is more robust in that it peforms the search until it times out. Since it returns an option it's useful for cases when you want to check something but not throw an exception automatically.", "Session",
      List(
        Param("T <: Searcher", "element", "(takes an element or a By locator)", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("Option[WebElement]", "", "", ParamType.OUTPUT)
      ),
      List(
        Example("session.findFirst(By.id(\"some-id\"))")
      ),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("findAll", "Finds all elements that matches the locator criteria\nThe finder is more robust in that it peforms the search until it times out. It is more robust than findElements because it performs a new search each time until timeout expires.", "Session",
      List(
        Param("T <: Searcher", "element", "(takes an element or a By locator)", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("List[WebElement]", "", "", ParamType.OUTPUT)
      ),
      List(
        Example("session.findAll(By.id(\"some-id\"))")
      ),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("waitForFunction", "Peforms the supplied function until the timeout occurs or the function is satisfied. This is very useful for writing custom waitFor code where Erika doesn't supply a suitable method", "Session",
      List(
        Param("() => Result", "runnable", "(supply the function here - see example)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("Unit", "", "", ParamType.OUTPUT)
      ),
      List(
        Example(
          """
            | // This shows how the waitForUrl is implemented - you must return a Result from the function
            | val waitForUrlFunction: () => Result = () => {
            |      val actualUrl = this.getUrl.getOrElse("")
            |      val outcome: Boolean = expectedUrl == actualUrl
            |      if(outcome) Result(outcome, actualUrl) else Result(outcome, s"Error: expected url: $expectedUrl but got url: $actualUrl")
            |    }
            | session.waitForFunction(waitForUrlFunction)
          """.stripMargin)
      ),
      List(Link("Result", "#Result")),
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("waitForUrl", "wait for the page to have the supplied url", "Session",
      List(Param("String", "url", "(url to wait for)", ParamType.INPUT)),
      List(Example("session.waitForUrl(\"http://www.some-url.com\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("waitForClass", "A shortcut to waitFor using a classname", "Session",
      List(
        Param("String", "className", "", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.waitForClass(\"some-classname\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("waitForId", "A shortcut to waitFor using an Id", "Session",
      List(
        Param("String", "id", "", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.waitForId(\"some-id\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("waitForCss", "A shortcut to waitFor using css selector", "Session",
      List(
        Param("String", "cssSelector", "", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.waitForCss(\"some-css-selector\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("waitForXpath", "A shortcut to waitFor using an xpath", "Session",
      List(
        Param("String", "xpath", "", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.waitForXpath(\"//some/xpath\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("waitForName", "A shortcut to waitFor using name", "Session",
      List(
        Param("String", "name", "", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.waitForName(\"some-name\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("waitForLink", "A shortcut to waitFor using link text", "Session",
      List(
        Param("String", "linkText", "", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.waitForLink(\"some link text\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("waitForLinkP", "A shortcut to waitFor using partial link text", "Session",
      List(
        Param("String", "partialLinkText", "", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.waitForLinkP(\"some partial link text\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("waitForTag", "A shortcut to waitFor using a tag name", "Session",
      List(
        Param("String", "tagName", "", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.waitForTag(\"input\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    // WebElement
    Card("isTextInput", "checks if a WebElement is a text input", "WebElement",
      List(Param("Boolean", "", "", ParamType.OUTPUT)),
      List(Example("element.isTextInput")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("isButton", "checks if a WebElement is a button", "WebElement",
      List(Param("Boolean", "", "", ParamType.OUTPUT)),
      List(Example("element.isButton")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("isTextArea", "checks if a WebElement is a text area", "WebElement",
      List(Param("Boolean", "", "", ParamType.OUTPUT)),
      List(Example("element.isTextArea")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("isLink", "checks if a WebElement is a link", "WebElement",
      List(Param("Boolean", "", "", ParamType.OUTPUT)),
      List(Example("element.isLink")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("isRadio", "checks if a WebElement is a radio", "WebElement",
      List(Param("Boolean", "", "", ParamType.OUTPUT)),
      List(Example("element.isRadio")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("toTextInput", "converts a WebElement to a TextInput", "WebElement",
      List(Param("TextInput", "", "(throws exception if cannot convert)", ParamType.OUTPUT)),
      List(Example("element.toTextInput")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("toButton", "converts a WebElement to a Button", "WebElement",
      List(Param("Button", "", "(throws exception if cannot convert)", ParamType.OUTPUT)),
      List(Example("element.toButton")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("toRadio", "converts a WebElement to a Radio", "WebElement",
      List(Param("Radio", "", "(throws exception if cannot convert)", ParamType.OUTPUT)),
      List(Example("element.toRadio")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("toTextArea", "converts a WebElement to a TextArea", "WebElement",
      List(Param("TextArea", "", "(throws exception if cannot convert)", ParamType.OUTPUT)),
      List(Example("element.toTextArea")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("toLink", "converts a WebElement to a Link", "WebElement",
      List(Param("Link", "", "(throws exception if cannot convert)", ParamType.OUTPUT)),
      List(Example("element.toLink")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("getTextOption", "gets text of element as an option", "WebElement",
      List(Param("Option[String]", "", "", ParamType.OUTPUT)),
      List(Example("element.getTextOption")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("getText", "gets text of element as a string", "WebElement",
      List(Param("String", "", "", ParamType.OUTPUT)),
      List(Example("element.getText")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("getAttributeOption", "gets an attribute of the element as an option", "WebElement",
      List(
        Param("String", "attribute", "(name of attribute)", ParamType.INPUT),
        Param("Option[String]", "", "", ParamType.OUTPUT)
      ),
      List(Example("element.getAttributeOption(\"class\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("getAttributeOption", "gets an attribute of the element as a string", "WebElement",
      List(
        Param("String", "attribute", "(name of attribute)", ParamType.INPUT),
        Param("String", "", "", ParamType.OUTPUT)
      ),
      List(Example("element.getAttribute(\"class\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("getNameOption", "gets the name of the element as an option", "WebElement",
      List(Param("Option[String]", "", "", ParamType.OUTPUT)),
      List(Example("element.getNameOption")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("getName", "gets the name of the element as a string", "WebElement",
      List(Param("String", "", "", ParamType.OUTPUT)),
      List(Example("element.getName")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("click()", "clicks the element", "WebElement",
      List(Param("WebElement", "", "", ParamType.OUTPUT)),
      List(Example("element.click()")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("clear()", "clears the elements value", "WebElement",
      List(Param("WebElement", "", "", ParamType.OUTPUT)),
      List(Example("element.clear()")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("isEnabled", "checks if a WebElement is enabled", "WebElement",
      List(Param("Boolean", "", "", ParamType.OUTPUT)),
      List(Example("element.isEnabled")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("isDisplayed", "checks if a WebElement is displayed/visible", "WebElement",
      List(Param("Boolean", "", "", ParamType.OUTPUT)),
      List(Example("element.isDisplayed")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("isSelected", "checks if a WebElement is selected", "WebElement",
      List(Param("Boolean", "", "", ParamType.OUTPUT)),
      List(Example("element.isSelected")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("isPresent", "checks if a WebElement is enabled and displayed", "WebElement",
      List(Param("Boolean", "", "", ParamType.OUTPUT)),
      List(Example("element.isPresent")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("sendKeys", "sends keystrokes to the WebElement using a string", "WebElement",
      List(
        Param("String", "text", "(the string to be typed into the element)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("element.sendKeys(\"This is cool\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("sendKeys", "sends keystrokes to the WebElement using Keys", "WebElement",
      List(
        Param("Keys.Value*", "key", "(the keys to be typed into the element)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("element.sendKeys(Keys.TAB, Keys.ENTER)")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("waitFor", "Wait for condition to be satisfied", "WebElement",
      List(
        Param("T <: Searcher", "element", "(takes an element or a By locator)", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(
        Example(
          """
            | // using By locator with default Condition.isVisible
            | val ele1 = element.waitFor(By.id("username"))
            |
            | // using By locator with a Condition
            | element.waitFor(By.id("username"), Condition.isEnabled)
            |
            | // using an element
            | element.waitFor(ele1)
            |
            | // using an element with a Condition
            | element.waitFor(ele1, Condition.textContains("hello"))
            |
          """.stripMargin)
      ),
      List(Link("Condition", "#Condition")),
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("findFirst", "Finds an option of the first element that matches the locator criteria\nThe finder is more robust in that it peforms the search until it times out. Since it returns an option it's useful for cases when you want to check something but not throw an exception automatically.", "WebElement",
      List(
        Param("T <: Searcher", "element", "(takes an element or a By locator)", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("Option[WebElement]", "", "", ParamType.OUTPUT)
      ),
      List(
        Example("element.findFirst(By.id(\"some-id\"))")
      ),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("findAll", "Finds all elements that matches the locator criteria\nThe finder is more robust in that it peforms the search until it times out. It is more robust than findElements because it performs a new search each time until timeout expires.", "WebElement",
      List(
        Param("T <: Searcher", "element", "(takes an element or a By locator)", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("List[WebElement]", "", "", ParamType.OUTPUT)
      ),
      List(
        Example("element.findAll(By.id(\"some-id\"))")
      ),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("waitForFunction", "Peforms the supplied function until the timeout occurs or the function is satisfied. This is very useful for writing custom waitFor code where Erika doesn't supply a suitable method", "WebElement",
      List(
        Param("() => Result", "runnable", "(supply the function here - see example)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("Unit", "", "", ParamType.OUTPUT)
      ),
      List(
        Example(
          """
            | // This shows how the waitForUrl is implemented - you must return a Result from the function
            | val waitForUrlFunction: () => Result = () => {
            |      val actualUrl = this.getUrl.getOrElse("")
            |      val outcome: Boolean = expectedUrl == actualUrl
            |      if(outcome) Result(outcome, actualUrl) else Result(outcome, s"Error: expected url: $expectedUrl but got url: $actualUrl")
            |    }
            | element.waitForFunction(waitForUrlFunction)
          """.stripMargin)
      ),
      List(Link("Result", "#Result")),
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("waitForClass", "A shortcut to waitFor using a classname", "WebElement",
      List(
        Param("String", "className", "", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("element.waitForClass(\"some-classname\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("waitForId", "A shortcut to waitFor using an Id", "WebElement",
      List(
        Param("String", "id", "", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("element.waitForId(\"some-id\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("waitForCss", "A shortcut to waitFor using css selector", "WebElement",
      List(
        Param("String", "cssSelector", "", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("element.waitForCss(\"some-css-selector\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("waitForXpath", "A shortcut to waitFor using an xpath", "WebElement",
      List(
        Param("String", "xpath", "", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("element.waitForXpath(\"//some/xpath\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("waitForName", "A shortcut to waitFor using name", "WebElement",
      List(
        Param("String", "name", "", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("element.waitForName(\"some-name\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("waitForLink", "A shortcut to waitFor using link text", "WebElement",
      List(
        Param("String", "linkText", "", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("element.waitForLink(\"some link text\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("waitForLinkP", "A shortcut to waitFor using partial link text", "WebElement",
      List(
        Param("String", "partialLinkText", "", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("element.waitForLinkP(\"some partial link text\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("waitForTag", "A shortcut to waitFor using a tag name", "WebElement",
      List(
        Param("String", "tagName", "", ParamType.INPUT),
        Param("Condition", "condition", "(defaults to isVisible)", ParamType.INPUT),
        Param("Int", "timeout", "(defaults to globalTimeout)", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("element.waitForTag(\"input\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("getValue", "gets value from TextInput", "TextInput",
      List(Param("String", "", "", ParamType.OUTPUT)),
      List(Example("textInput.getValue")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("setValue", "sets the value of a TextInput", "TextInput",
      List(
        Param("String", "value", "", ParamType.INPUT),
        Param("WebElement", "", "", ParamType.OUTPUT)
      ),
      List(Example("textInput.setValue(\"some value\")")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    ),

    Card("clearValue", "clears the value of a TextInput", "TextInput",
      List(Param("WebElement", "", "", ParamType.OUTPUT)),
      List(Example("textInput.clearValue")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/WebElement.scala"
    )
  )

  val links = List(
    Link("Home", "home.html"),
    Link("Documentation", "#", "active"),
    Link("Github", "https://github.com/kingsleyh/erika")
  )

  val pages = List(
    Page("home.html", "Home", List(
      Link("Home", "home.html", "active"),
      Link("Documentation", "documentation.html"),
      Link("Github", "https://github.com/kingsleyh/erika")
    ), "README.md")
  )

  new DocGen("Erika", "v56", "https://github.com/kingsleyh/erika/blob/master")
    .withLinks(links)
    .withCards(cards)
    .withPages(pages)
    .generate()

}
