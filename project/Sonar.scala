
object Sonar {
  val properties = Map(
    "sonar.host.url" -> "https://sonarcloud.io",
    "sonar.organization" -> "romanidze-projects",
    "sonar.projectName" -> "kaesquella",
    "sonar.projectKey" -> "romanidze_kaesquella",
    "sonar.sourceEncoding" -> "UTF-8",
    "sonar.scala.version" -> "2.13.2",

    "sonar.modules" -> "kaesquella-core",

    "kaesquella-core.sonar.projectName" -> "kaesquella-core",
    "kaesquella-core.sonar.sources" -> "modules/core/src/main/scala",
    "kaesquella-core.sonar.tests" -> "modules/core/src/test/scala",

    "sonar.sourceEncoding" -> "UTF-8",
    "sonar.scala.scoverage.reportPath" -> "target/scala-2.13/scoverage-report/scoverage.xml",
    "sonar.scala.coverage.reportPaths" -> "target/scala-2.13/scoverage-report/scoverage.xml",
  )
}