resolvers ++= Seq( "bintray-sbt-plugin-releases" at "http://dl.bintray.com/content/sbt/sbt-plugin-releases" )

addSbtPlugin("me.lessis" % "bintray-sbt" % "0.2.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8.3")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")
