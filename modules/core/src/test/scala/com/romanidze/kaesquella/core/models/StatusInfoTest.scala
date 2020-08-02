package com.romanidze.kaesquella.core.models

import tethys._
import tethys.jackson._

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.io.Source
import scala.io.BufferedSource
import tethys.readers.ReaderError
import org.scalatest.EitherValues

class StatusInfoTest extends AnyWordSpec with Matchers with EitherValues {

  "StatusInfo" should {

    "encode to json" in {

      val testObj: StatusInfo = StatusInfo("SUCCESS", "test_message")

      val json = """{"status":"SUCCESS","message":"test_message"}"""

      val resultString: String = testObj.asJson

      resultString shouldBe json

    }

    "decode from json" in {

      val fileData: BufferedSource = Source.fromResource("status.json")
      val fileString: String = fileData.mkString
      fileData.close()

      val fileObj: Either[ReaderError, StatusInfo] = fileString.jsonAs[StatusInfo]

      fileObj should be('right)

      val expectedResult: StatusInfo = StatusInfo("SUCCESS", "Stream created and running")

      fileObj.right.get shouldBe expectedResult

    }

  }

}
