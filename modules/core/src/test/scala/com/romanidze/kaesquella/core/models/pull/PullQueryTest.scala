package com.romanidze.kaesquella.core.models.pull

import com.romanidze.kaesquella.core.models.{ClientError, ValidationUtils}
import org.json4s.DefaultFormats
import org.json4s.JsonAST.JArray
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PullQueryTest extends AnyWordSpec with Matchers {

  "PullQuery model" should {

    "decode request from json" in {

      val expectedObj = PullRequest("select * from foo", Map("prop1" -> "val1", "prop2" -> "val2"))

      ValidationUtils.validateDecode[PullRequest](expectedObj, "pull/request.json")

    }

    "decode schema from json" in {

      val expectedObj = ResponseSchema(
        Some("xyz123"),
        List("col", "col2", "col3"),
        List("BIGINT", "STRING", "BOOLEAN")
      )

      ValidationUtils.validateDecode[ResponseSchema](expectedObj, "pull/schema-response.json")

    }

    "decode data from json" in {

      val jsonString: String = ValidationUtils.getJSONData("pull/data-response.json")

      val decodeResult: Either[ClientError, PullResponse] = convert(jsonString)

      assert(decodeResult.isRight)

      val pullResponse: PullResponse = decodeResult.toOption.get

      pullResponse.isSchema shouldBe false
      pullResponse.data.isEmpty shouldBe false

      val dataArray: JArray = pullResponse.data.get

      implicit val format: DefaultFormats = DefaultFormats

      dataArray.arr(0).extract[Int] shouldBe 765
      dataArray.arr(1).extract[String] shouldBe "whatever"
      dataArray.arr(2).extract[Boolean] shouldBe false

    }

  }

}
