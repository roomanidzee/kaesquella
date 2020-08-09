import sbt._

object Dependencies {

  private val scalaTest: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % Versions.scalaTest,
    "org.scalactic" %% "scalactic" % Versions.scalaTest
  ).map(_ % Test)

  private val flexmark: Seq[ModuleID] =
    Seq("com.vladsch.flexmark" % "flexmark-all" % Versions.flexMark).map(_ % Test)

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
    "com.softwaremill.sttp.client" %% "async-http-client-backend-monix" % Versions.sttp,
  ).union(sttp)

  val coreDeps : Seq[ModuleID] = tethys

  val testDeps: Seq[ModuleID] = scalaTest.union(flexmark)

  val monixDeps: Seq[ModuleID] = coreDeps.union(monix)

}
