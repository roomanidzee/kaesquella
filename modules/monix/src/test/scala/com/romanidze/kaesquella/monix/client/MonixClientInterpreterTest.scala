package com.romanidze.kaesquella.monix.client

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.romanidze.kaesquella.core.models.ksql.ddl.DDLInfo
import com.romanidze.kaesquella.core.client.Output
import com.romanidze.kaesquella.core.models.{ClientError, KSQLVersionResponse, StatusInfo}
import com.romanidze.kaesquella.core.models.ksql.{Request => KSQLInfoRequest}
import com.romanidze.kaesquella.core.models.pull.{PullRequest, PullResponse}
import com.romanidze.kaesquella.core.models.query.row.RowInfo
import com.romanidze.kaesquella.core.models.query.{Request => KSQLQueryRequest}
import com.romanidze.kaesquella.core.models.terminate.TopicsForTerminate
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import monix.reactive.Observable
import org.json4s.JsonAST.JArray
import org.json4s.{DefaultFormats, JsonAST}
import org.scalatest.{BeforeAndAfterAll, EitherValues}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class MonixClientInterpreterTest
    extends AnyWordSpec
    with Matchers
    with BeforeAndAfterAll
    with EitherValues {

  private val wireMockServer = new WireMockServer(
    wireMockConfig()
      .port(9001)
      .usingFilesUnderClasspath("wiremock")
  )

  override def beforeAll(): Unit = wireMockServer.start()
  override def afterAll(): Unit = wireMockServer.stop()

  "Monix Client Interpreter" should {

    val client = new MonixClientInterpreter("http://localhost:9001")

    "retrieve query status" in {

      val responseTask: Task[Output[StatusInfo]] =
        client.getQueryStatus("stream/PAGEVIEWS/create")

      val responseEither: Output[StatusInfo] = responseTask.runSyncUnsafe()

      responseEither should be(Symbol("right"))

      responseEither.toOption.get shouldBe StatusInfo("SUCCESS", "Stream created and running")

    }

    "retrieve server version" in {

      val responseTask: Task[Output[KSQLVersionResponse]] = client.getServerVersion

      val responseEither: Output[KSQLVersionResponse] = responseTask.runSyncUnsafe()

      responseEither should be(Symbol("right"))

      val serverVersion: KSQLVersionResponse = responseEither.toOption.get

      serverVersion.info.version shouldBe "5.1.2"
      serverVersion.info.clusterID shouldBe "j3tOi6E_RtO_TMH3gBmK7A"
      serverVersion.info.serviceID shouldBe "default_"

    }

    "run ddl request" in {

      val request = KSQLInfoRequest(
        "CREATE STREAM pageviews_home AS SELECT * FROM pageviews_original WHERE pageid='home';",
        Map.empty[String, String]
      )

      val responseTask: Task[Output[DDLInfo]] = client.runDDLRequest(request)

      val responseEither: Output[DDLInfo] = responseTask.runSyncUnsafe()

      responseEither should be(Symbol("right"))

      val ddlInfo: DDLInfo = responseEither.toOption.get

      ddlInfo.status.status shouldBe "SUCCESS"
      ddlInfo.commandSequenceNumber shouldBe 1
    }

    "run query request" in {

      val request = KSQLQueryRequest("SELECT * FROM pageviews", Map.empty[String, String])

      val responseTask: RowInfoResponse = client.runQueryRequest(request)

      val responseEither: Output[Observable[Output[RowInfo]]] =
        responseTask.runSyncUnsafe()

      responseEither should be(Symbol("right"))

      val rowList: Task[List[Output[RowInfo]]] = responseEither.toOption.get.toListL

      val resultList: List[Output[RowInfo]] = rowList.runSyncUnsafe()

      implicit val format: DefaultFormats = DefaultFormats

      resultList.foreach { elem =>
        elem should be(Symbol("right"))
        val values: JsonAST.JArray = elem.toOption.get.row.columns

        values.arr(0).extract[Long] shouldBe 1524760769983L

      }

    }

    "terminate cluster" in {

      val request: Option[TopicsForTerminate] = Some(TopicsForTerminate(List("FOO", "bar.*")))

      val responseTask: Task[Output[StatusInfo]] = client.terminateCluster(request)
      val responseEither: Output[StatusInfo] = responseTask.runSyncUnsafe()

      responseEither should be(Symbol("right"))

      val terminateResult: StatusInfo = responseEither.toOption.get

      terminateResult.status shouldBe "200"

    }

    "run pull query" in {

      val request = PullRequest("select * from foo", Map("prop1" -> "val1", "prop2" -> "val2"))

      val responseTask: Task[Output[Observable[Output[PullResponse]]]] =
        client.runPullRequest(request)
      val responseEither: Output[Observable[Output[PullResponse]]] = responseTask.runSyncUnsafe()

      responseEither should be(Symbol("right"))

      val dataList: Task[List[Output[PullResponse]]] = responseEither.toOption.get.toListL
      val resultList: List[Output[PullResponse]] = dataList.runSyncUnsafe()

      resultList.foreach(elem => elem.isRight)

      val finalList: List[PullResponse] = resultList.map(elem => elem.toOption.get)

      finalList.size shouldBe 4

      assert(finalList(0).isSchema)

      val checkElem: PullResponse = finalList(1)

      checkElem.isSchema shouldBe false
      checkElem.data.isEmpty shouldBe false

      val dataArray: JArray = checkElem.data.get

      implicit val format: DefaultFormats = DefaultFormats

      dataArray.arr(0).extract[Int] shouldBe 123
      dataArray.arr(1).extract[String] shouldBe "blah"
      dataArray.arr(2).extract[Boolean] shouldBe true

    }

  }

}
