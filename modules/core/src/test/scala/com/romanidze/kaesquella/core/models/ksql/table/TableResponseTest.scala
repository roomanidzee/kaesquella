package com.romanidze.kaesquella.core.models.ksql.table

import tethys._
import tethys.jackson._

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.io.Source
import scala.io.BufferedSource
import tethys.readers.ReaderError
import org.scalatest.EitherValues

class TableResponseTest extends AnyWordSpec with Matchers with EitherValues {

  "TableResponse" should {

    "encode to json" in {

      val testObj: TableResponse =
        TableResponse("test", Seq(TableInfo("test", "test", "test", "TABLE", true)))

      val json =
        """{"statementText":"test","tables":[{"name":"test","topic":"test","format":"test","type":"TABLE","isWindowed":true}]}"""

      val resultString: String = testObj.asJson

      resultString shouldBe json

    }

    "decode from json" in {

      val fileData: BufferedSource = Source.fromResource("ksql/responses/table.json")
      val fileString: String = fileData.mkString
      fileData.close()

      val fileObj: Either[ReaderError, TableResponse] = fileString.jsonAs[TableResponse]
      fileObj should be(Symbol("right"))

      val resultObj: TableResponse = fileObj.right.get

      resultObj.tables.length shouldBe 1
      resultObj.statement shouldBe "LIST TABLES"

    }

  }

}
