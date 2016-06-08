import net.kenro.ji.jin.docs._

object Docs extends App {

  val cards = List(

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

    Card("setTimeout", "set a timeout for a specific TimeoutType", "Session",
      List(
        Param("TimeoutType", "timeoutType", "", ParamType.INPUT),
        Param("Int", "milliseconds", "", ParamType.INPUT),
        Param("Unit", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.setTimeout(TimeoutType.IMPLICIT, 5000)")),
      List(Link("TimeoutType", "#TimeoutType")),
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("setAsyncScriptTimeout", "set a timeout for async script execution", "Session",
      List(
        Param("Int", "milliseconds", "", ParamType.INPUT),
        Param("Unit", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.setAsyncScriptTimeout(5000)")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("setImplicitWaitTimeout", "set a timeout for implicit wait", "Session",
      List(
        Param("Int", "milliseconds", "", ParamType.INPUT),
        Param("Unit", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.setImplicitWaitTimeout(5000)")),
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
    ),

    Card("waitFor", "wait for condition to be satisfied", "Session",
      List(
        Param("T <: Searcher", "element", "(takes an element or a By locator)", ParamType.INPUT),
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
      List.empty,
      "src/main/scala/net/kenro/ji/jin/Session.scala"
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

  new DocGen("Erika", "v49", "https://github.com/kingsleyh/erika/blob/master")
    .withLinks(links)
    .withCards(cards)
    .withPages(pages)
    .generate()

}
