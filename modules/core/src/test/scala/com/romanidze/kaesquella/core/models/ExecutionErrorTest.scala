package com.romanidze.kaesquella.core.models

import tethys._
import tethys.jackson._

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.io.Source
import scala.io.BufferedSource
import tethys.readers.ReaderError
import org.scalatest.EitherValues

class ExecutionErrorTest extends AnyWordSpec with Matchers with EitherValues {

  "ExecutionError" should {

    "encode to json" in {

      val testObj: ExecutionError = ExecutionError("1000", "test")

      val json = """{"error_code":"1000","message":"test"}"""

      val resultString: String = testObj.asJson

      resultString shouldBe json

    }

    "decode from json" in {

      val fileData: BufferedSource = Source.fromResource("error_response.json")
      val fileString: String = fileData.mkString
      fileData.close()

      val fileObj: Either[ReaderError, ExecutionError] = fileString.jsonAs[ExecutionError]

      fileObj should be(Symbol("right"))

      val expectedResult: ExecutionError = ExecutionError("4000", "some_message")

      fileObj.right.get shouldBe expectedResult

    }

  }

}
