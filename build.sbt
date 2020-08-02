import Dependencies._

val projectVersion = "0.0.1"
val scalaProjectVersion = "2.13.2"

lazy val kaesquella = (project in file("."))
  .settings(
    name := "kaesquella",
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
    assemblyJarName in assembly := s"kaesquella-${version.value}.jar",
    sonarProperties := Sonar.properties
  )
  .aggregate(core)

lazy val core = (project in file("modules/core"))
  .settings(
    name := "kaesquella-core",
    version := projectVersion,
    scalaVersion := scalaProjectVersion,
    testOptions in Test ++= Seq(
        Tests.Argument(TestFrameworks.ScalaTest, "-oT"),
        Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-reports")
      ),
    scalafmtOnCompile := true,
    libraryDependencies ++= Dependencies.coreDeps ++ Dependencies.testDeps,
    sonarProperties := Sonar.properties
  )
