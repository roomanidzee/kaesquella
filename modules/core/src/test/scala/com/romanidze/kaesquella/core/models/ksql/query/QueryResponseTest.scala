package com.romanidze.kaesquella.core.models.ksql.query

import com.romanidze.kaesquella.core.models.ValidationUtils
import tethys._
import tethys.jackson._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.io.Source
import scala.io.BufferedSource
import tethys.readers.ReaderError
import org.scalatest.EitherValues

class QueryResponseTest extends AnyWordSpec with Matchers with EitherValues {

  "QueryResponse" should {

    "encode to json" in {

      val testObj: QueryResponse = QueryResponse("test", Seq(QueryInfo("test", "test", "test")))

      val json =
        """{"statementText":"test","queries":[{"queryString":"test","sinks":"test","id":"test"}]}"""

      ValidationUtils.validateEncode[QueryResponse](testObj, json)

    }

    "decode from json" in {

      val testObj: QueryResponse =
        QueryResponse("LIST QUERIES", Seq(QueryInfo("LIST QUERIES", "test, test", "test_id")))

      ValidationUtils.validateDecode[QueryResponse](testObj, "ksql/responses/query.json")

    }

  }

}
