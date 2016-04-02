name := "erika"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "Tim Tennant's repo" at "http://dl.bintray.com/timt/repo/"

libraryDependencies += "io.argonaut" % "argonaut_2.11" % "6.2-M1"
libraryDependencies += "io.shaka" %% "naive-http" % "85"