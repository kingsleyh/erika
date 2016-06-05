import scala.util.Try
import scala.Some
import bintray.Keys._

name := "erika"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.8")

organization := "net.kenro-ji-jin"

version := Try(sys.env("LIB_VERSION")).getOrElse("1")

resolvers += "Tim Tennant's repo" at "http://dl.bintray.com/timt/repo/"

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

libraryDependencies += "io.argonaut" % "argonaut_2.11" % "6.1"

libraryDependencies += "io.shaka" %% "naive-http" % "85"

libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"

libraryDependencies += "net.kenro-ji-jin" % "docs4s_2.11" % "12"

pgpPassphrase := Some(Try(sys.env("SECRET")).getOrElse("goaway").toCharArray)

pgpSecretRing := file("./publish/sonatype.asc")

bintrayPublishSettings

repository in bintray := "repo"

bintrayOrganization in bintray := None

publishMavenStyle := true

publishArtifact in Test := false

homepage := Some(url("https://github.com/kingsleyh/erika"))

licenses +=("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))

pomExtra :=
  <scm>
    <url>git@github.com:kingsleyh/erika.git</url>
    <connection>scm:git:git@github.com:kingsleyh/erika.git</connection>
  </scm>
    <developers>
      <developer>
        <id>kingsleyh</id>
      </developer>
    </developers>
