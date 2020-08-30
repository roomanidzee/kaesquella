package com.romanidze.kaesquella.monix.client

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.romanidze.kaesquella.core.models.{ClientError, StatusInfo}
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

    "retrieve query status" in {

      val client = new MonixClientInterpreter("http://localhost:9001")

      val responseTask: Task[Either[ClientError, StatusInfo]] =
        client.getQueryStatus("stream/PAGEVIEWS/create")

      val responseEither: Either[ClientError, StatusInfo] = responseTask.runSyncUnsafe()

      responseEither should be(Symbol("right"))

      responseEither.toOption.get shouldBe StatusInfo("SUCCESS", "Stream created and running")

    }

  }

}
