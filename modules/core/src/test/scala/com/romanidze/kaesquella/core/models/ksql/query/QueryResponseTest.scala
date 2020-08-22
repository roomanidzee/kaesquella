package com.romanidze.kaesquella.core.models.ksql.query

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

      val resultString: String = testObj.asJson

      resultString shouldBe json

    }

    "decode from json" in {

      val fileData: BufferedSource = Source.fromResource("ksql/responses/query.json")
      val fileString: String = fileData.mkString
      fileData.close()

      val fileObj: Either[ReaderError, QueryResponse] = fileString.jsonAs[QueryResponse]
      fileObj should be(Symbol("right"))

      val resultObj: QueryResponse = fileObj.right.get

      resultObj.queries.length shouldBe 1

      resultObj.queries(0).id shouldBe "test_id"

    }

  }

}
