package com.romanidze.kaesquella.core.models.query

import com.romanidze.kaesquella.core.models.ValidationUtils
import tethys._
import tethys.jackson._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.io.Source
import scala.io.BufferedSource
import tethys.readers.ReaderError
import org.scalatest.EitherValues

class RequestTest extends AnyWordSpec with Matchers with EitherValues {

  "KSQL Request" should {

    "encode to json" in {

      val testObj: Request = Request("test", Map("test" -> "test"))

      val json = """{"ksql":"test","streamsProperties":{"test":"test"}}"""

      ValidationUtils.validateEncode[Request](testObj, json)

    }

    "decode from json" in {

      val testObj =
        Request("SELECT * FROM pageviews", Map("ksql.streams.auto.offset.reset" -> "earliest"))

      ValidationUtils.validateDecode[Request](testObj, "query/request.json")

    }

  }

}
