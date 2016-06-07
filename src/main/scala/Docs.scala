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

    Card("visitUrl", "navigate to the supplied url", "Session",
      List(
        Param("String", "url", "", ParamType.INPUT),
        Param("Session", "", "", ParamType.OUTPUT)
      ),
      List(Example("session.visitUrl(\"www.google.com\")")),
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

  new DocGen("Erika", "v48", "https://github.com/kingsleyh/erika/blob/master")
    .withLinks(links)
    .withCards(cards)
    .withPages(pages)
    .generate()

}
