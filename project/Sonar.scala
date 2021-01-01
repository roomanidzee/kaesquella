
object Sonar {
  val properties = Map(
    "sonar.host.url" -> "https://sonarcloud.io",
    "sonar.organization" -> "romanidze-projects",
    "sonar.projectName" -> "kaesquella",
    "sonar.projectKey" -> "romanidze_kaesquella",
    "sonar.sourceEncoding" -> "UTF-8",
    "sonar.scala.version" -> "2.13.2",
    "sonar.sourceEncoding" -> "UTF-8",

    "sonar.projectBaseDir" -> "modules",

    "sonar.modules" -> "core,monix",

    "core.sonar.sources" -> "src/main/scala",
    "core.sonar.tests"-> "src/test/scala",
    "core.sonar.scala.scoverage.reportPath" -> "target/scala-2.13/scoverage-report/scoverage.xml",
    "core.sonar.scala.coverage.reportPaths" -> "target/scala-2.13/scoverage-report/scoverage.xml",

    "monix.sonar.sources" -> "src/main/scala",
    "monix.sonar.tests"-> "src/test/scala",
    "monix.sonar.scala.scoverage.reportPath" -> "target/scala-2.13/scoverage-report/scoverage.xml",
    "monix.sonar.scala.coverage.reportPaths" -> "target/scala-2.13/scoverage-report/scoverage.xml"
  )
}