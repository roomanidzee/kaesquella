import Dependencies._

val projectVersion = "0.0.1"
val scalaProjectVersion = "2.13.2"
val coverageMinimumVal = 90

lazy val commonSettings = Seq(
  version := projectVersion,
  scalaVersion := scalaProjectVersion,
  resolvers ++= Seq(
      Resolver.mavenCentral,
      Resolver.mavenLocal,
      Resolver.bintrayRepo("sbt-native-packager", "maven"),
      Resolver.bintrayRepo("sbt-assembly", "maven")
  ),
  scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8"),
  testOptions in Test ++= Seq(
      Tests.Argument(TestFrameworks.ScalaTest, "-oT"),
      Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-reports")
  ),
  scalafmtOnCompile := true,
  sonarProperties := Sonar.properties,
  coverageMinimum := coverageMinimumVal,
  coverageFailOnMinimum := true
)

lazy val kaesquella = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := "kaesquella",
    assemblyJarName in assembly := s"kaesquella-${version.value}.jar",
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
