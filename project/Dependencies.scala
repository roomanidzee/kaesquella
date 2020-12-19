import sbt._

object Dependencies {

  private val scalaTest: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % Versions.scalaTest,
    "org.scalactic" %% "scalactic" % Versions.scalaTest
  ).map(_ % Test)

  private val flexmark: Seq[ModuleID] =
    Seq("com.vladsch.flexmark" % "flexmark-all" % Versions.flexMark).map(_ % Test)

  private val wiremock: Seq[ModuleID] =
    Seq("com.github.tomakehurst" % "wiremock" % Versions.wiremock).map(_ % Test)

  private val tethys: Seq[ModuleID] = Seq(
    "com.tethys-json" %% "tethys-core" % Versions.tethys,
    "com.tethys-json" %% "tethys-jackson" % Versions.tethys,
    "com.tethys-json" %% "tethys-derivation" % Versions.tethys,
    "com.tethys-json" %% "tethys-json4s" % Versions.tethys
  )

  private val sttp: Seq[ModuleID] = Seq(
    "com.softwaremill.sttp.client" %% "core" % Versions.sttp
  )

  private val monix: Seq[ModuleID] = Seq(
    "com.softwaremill.sttp.client" %% "async-http-client-backend-monix" % Versions.sttp
  ).union(sttp)

  private val logging: Seq[ModuleID] = Seq(
    "org.apache.logging.log4j" % "log4j-api" % Versions.log4j2,
    "org.apache.logging.log4j" % "log4j-core" % Versions.log4j2,
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % Versions.log4j2,
    "org.apache.logging.log4j" % "log4j-1.2-api" % Versions.log4j2,
    "com.typesafe.scala-logging" %% "scala-logging" % Versions.scalaLogging
  )

  val coreDeps : Seq[ModuleID] = tethys.union(sttp)

  val testDeps: Seq[ModuleID] = scalaTest.union(flexmark).union(wiremock)

  val monixDeps: Seq[ModuleID] = tethys.union(monix).union(logging)

}
