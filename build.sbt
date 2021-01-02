val projectVersion = "0.0.1"
val scalaProjectVersion = "2.13.4"

lazy val commonSettings = Seq(
  version := projectVersion,
  scalaVersion := scalaProjectVersion,
  resolvers ++= Seq(
      Resolver.mavenCentral,
      Resolver.mavenLocal
  ),
  scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8"),
  scalafmtOnCompile := true,
  sonarProperties := Sonar.properties
)

lazy val kaesquella = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := "kaesquella",
  )
  .aggregate(core, monix)
  .dependsOn(core, monix)

lazy val core = (project in file("modules/core"))
  .settings(commonSettings)
  .settings(
    name := "kaesquella-core",
    libraryDependencies ++= Dependencies.coreDeps ++ Dependencies.testDeps
  )

lazy val monix = (project in file("modules/monix"))
  .settings(commonSettings)
  .settings(
    name := "kaesquella-monix",
    libraryDependencies ++= Dependencies.monixDeps ++ Dependencies.testDeps
  )
  .dependsOn(core)
