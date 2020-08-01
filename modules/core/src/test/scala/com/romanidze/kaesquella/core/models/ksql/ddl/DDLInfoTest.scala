package com.romanidze.kaesquella.core.models.ksql.ddl

import tethys._
import tethys.jackson._

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.io.Source
import scala.io.BufferedSource
import tethys.readers.ReaderError
import org.scalatest.EitherValues

class DDLInfoTest extends AnyWordSpec with Matchers with EitherValues {

  "DDLInfo" should {

    "encode to json" in {

      val testObj: DDLInfo = DDLInfo("test", "test", CommandStatus("test", "test"))
      val json =
        """{"statementText":"test","commandId":"test","commandStatus":{"status":"test","message":"test"}}"""

      val resultString: String = testObj.asJson

      resultString shouldBe json

    }

    "decode from json" in {

      val fileData: BufferedSource = Source.fromResource("ksql/responses/ddl.json")
      val fileString: String = fileData.mkString
      fileData.close()

      val fileObj: Either[ReaderError, Seq[DDLInfo]] = fileString.jsonAs[Seq[DDLInfo]]
      fileObj should be('right)

      val resultObj: Seq[DDLInfo] = fileObj.right.get

      resultObj.length shouldBe 2

      resultObj(0).commandID shouldBe "stream/PAGEVIEWS_HOME/create"
      resultObj(1).commandID shouldBe "stream/PAGEVIEWS_ALICE/create"

    }

  }

}
