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
    "com.tethys-json" %% "tethys-derivation" % Versions.tethys
  )

  val coreDeps : Seq[ModuleID] = tethys

  val testDeps: Seq[ModuleID] = scalaTest.union(flexmark)

}
