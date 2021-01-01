package com.romanidze.kaesquella.core.models.query

import tethys._
import tethys.jackson._

import org.json4s.JsonAST._
import org.json4s.DefaultFormats

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.io.Source
import scala.io.BufferedSource
import tethys.readers.ReaderError
import org.scalatest.EitherValues

import com.romanidze.kaesquella.core.models.query.row.Data
import com.romanidze.kaesquella.core.models.query.row.RowInfo

class RowInfoTest extends AnyWordSpec with Matchers with EitherValues {

  "Row Info" should {

    "encode to json" in {

      val testData: Data = Data(JArray(List(JString("test"), JInt(1), JBool(false))))

      val testObj: RowInfo = RowInfo(testData, Some("test"), Some("test"))

      val json =
        """{"row":{"columns":["test",1,false]},"errorMessage":"test","finalMessage":"test"}"""

      val resultString: String = testObj.asJson

      resultString shouldBe json

    }

    "decode from json" in {

      val fileData: BufferedSource = Source.fromResource("query/response.json")
      val fileString: String = fileData.mkString
      fileData.close()

      val fileObj: Either[ReaderError, RowInfo] = fileString.jsonAs[RowInfo]
      fileObj should be(Symbol("right"))

      val resultObj: RowInfo = fileObj.toOption.get

      resultObj.errorMessage shouldBe None
      resultObj.finalMessage shouldBe None

      val values: JArray = resultObj.row.columns

      implicit val format: DefaultFormats = DefaultFormats

      values.arr(0).extract[Long] shouldBe 1524760769983L
      values.arr(1).extract[String] shouldBe "1"
      values.arr(2).extract[Long] shouldBe 1524760769747L
      values.arr(3).extract[String] shouldBe "alice"
      values.arr(4).extract[String] shouldBe "home"

    }

  }

}
