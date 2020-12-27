package com.romanidze.kaesquella.core.models.ksql.table

import com.romanidze.kaesquella.core.models.ValidationUtils
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import org.scalatest.EitherValues

class TableResponseTest extends AnyWordSpec with Matchers with EitherValues {

  "TableResponse" should {

    "encode to json" in {

      val testObj: TableResponse =
        TableResponse("test", Seq(TableInfo("test", "test", "test", "TABLE", true)))

      val json =
        """{"statementText":"test","tables":[{"name":"test","topic":"test","format":"test","type":"TABLE","isWindowed":true}]}"""

      ValidationUtils.validateEncode[TableResponse](testObj, json)

    }

    "decode from json" in {

      val testObj: TableResponse =
        TableResponse(
          "LIST TABLES",
          Seq(TableInfo("test_table", "test_topic", "JSON", "TABLE", true))
        )

      ValidationUtils.validateDecode[TableResponse](testObj, "ksql/responses/table.json")

    }

  }

}
