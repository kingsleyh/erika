import net.kenro.ji.jin.docs._

object Docs extends App {

  val cards = List(
    Card("setGlobalTimeout", "Sets the timeout used in all the functions that perform an internal timeout while waiting for something", "Unit",
      List(Param("Int","timeout","(in millis)",ParamType.INPUT)),
      List(Example("")),
      List(Link("getGlobalTimeout","#getGlobalTimeout")),
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

  new DocGen("Erika", "v40", "https://github.com/kingsleyh/erika/blob/master")
    .withLinks(links)
    .withCards(cards)
    .withPages(pages)
    .generate()

}
