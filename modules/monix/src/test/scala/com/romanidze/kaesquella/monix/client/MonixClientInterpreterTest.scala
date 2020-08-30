package com.romanidze.kaesquella.monix.client

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.romanidze.kaesquella.core.models.{ClientError, KSQLVersionResponse, StatusInfo}
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
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

      val responseTask: Task[Either[ClientError, StatusInfo]] =
        client.getQueryStatus("stream/PAGEVIEWS/create")

      val responseEither: Either[ClientError, StatusInfo] = responseTask.runSyncUnsafe()

      responseEither should be(Symbol("right"))

      responseEither.toOption.get shouldBe StatusInfo("SUCCESS", "Stream created and running")

    }

    "retrieve server version" in {

      val responseTask: Task[Either[ClientError, KSQLVersionResponse]] = client.getServerVersion

      val responseEither = responseTask.runSyncUnsafe()

      responseEither should be(Symbol("right"))

      val serverVersion: KSQLVersionResponse = responseEither.toOption.get

      serverVersion.info.version shouldBe "5.1.2"
      serverVersion.info.clusterID shouldBe "j3tOi6E_RtO_TMH3gBmK7A"
      serverVersion.info.serviceID shouldBe "default_"

    }

  }

}
