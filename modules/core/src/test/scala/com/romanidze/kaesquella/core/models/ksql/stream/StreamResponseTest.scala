package com.romanidze.kaesquella.core.models.ksql.stream

import tethys._
import tethys.jackson._

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.io.Source
import scala.io.BufferedSource
import tethys.readers.ReaderError
import org.scalatest.EitherValues

class StreamResponseTest extends AnyWordSpec with Matchers with EitherValues {

  "StreamResponse" should {

    "encode to json" in {

      val testObj: StreamResponse =
        StreamResponse("test", Seq(StreamInfo("test", "test", "test", "STREAM")))

      val json =
        """{"statementText":"test","streams":[{"name":"test","topic":"test","format":"test","type":"STREAM"}]}"""

      val resultString: String = testObj.asJson

      resultString shouldBe json

    }

    "decode from json" in {

      val fileData: BufferedSource = Source.fromResource("ksql/responses/stream.json")
      val fileString: String = fileData.mkString
      fileData.close()

      val fileObj: Either[ReaderError, StreamResponse] = fileString.jsonAs[StreamResponse]
      fileObj should be(Symbol("right"))

      val resultObj: StreamResponse = fileObj.toOption.get

      resultObj.streams.length shouldBe 1
      resultObj.statement shouldBe "LIST STREAMS"

    }

  }

}
