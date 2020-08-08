
object Sonar {
  val properties = Map(
    "sonar.host.url" -> "https://sonarcloud.io",
    "sonar.organization" -> "romanidze-projects",
    "sonar.projectName" -> "kaesquella",
    "sonar.projectKey" -> "romanidze_kaesquella",
    "sonar.sourceEncoding" -> "UTF-8",
    "sonar.scala.version" -> "2.13.2",

    "sonar.modules" -> "modules/core",

    "sonar.sourceEncoding" -> "UTF-8",
    "sonar.scala.scoverage.reportPath" -> "target/scala-2.13/scoverage-report/scoverage.xml",
    "sonar.scala.coverage.reportPaths" -> "target/scala-2.13/scoverage-report/scoverage.xml",
  )
}