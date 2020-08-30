package com.romanidze.kaesquella.core.models.ksql

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

      val resultString: String = testObj.asJson

      resultString shouldBe json

    }

    "decode from json" in {

      val fileData: BufferedSource = Source.fromResource("ksql/request.json")
      val fileString: String = fileData.mkString
      fileData.close()

      val fileObj: Either[ReaderError, Request] = fileString.jsonAs[Request]
      fileObj should be(Symbol("right"))

      val resultObj: Request = fileObj.toOption.get

      resultObj.input.split(";").length shouldBe 2
      resultObj.properties should contain("ksql.streams.auto.offset.reset" -> "earliest")

    }

  }

}
