package com.romanidze.kaesquella.core.models.ksql.stream

import com.romanidze.kaesquella.core.models.ValidationUtils
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

      ValidationUtils.validateEncode[StreamResponse](testObj, json)

    }

    "decode from json" in {

      val testObj: StreamResponse =
        StreamResponse(
          "LIST STREAMS",
          Seq(StreamInfo("test_stream", "test_topic", "JSON", "STREAM"))
        )

      ValidationUtils.validateDecode[StreamResponse](testObj, "ksql/responses/stream.json")

    }

  }

}
