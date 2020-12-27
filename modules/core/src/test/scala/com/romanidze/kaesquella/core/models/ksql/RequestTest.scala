package com.romanidze.kaesquella.core.models.ksql

import com.romanidze.kaesquella.core.models.ValidationUtils
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import org.scalatest.EitherValues

class RequestTest extends AnyWordSpec with Matchers with EitherValues {

  "KSQL Request" should {

    "encode to json" in {

      ValidationUtils.validateEncode[Request](
        Request("test", Map("test" -> "test")),
        """{"ksql":"test","streamsProperties":{"test":"test"}}"""
      )

    }

    "decode from json" in {

      val expectedObj = Request(
        "CREATE STREAM pageviews_home AS SELECT * FROM pageviews_original WHERE pageid='home'; CREATE STREAM pageviews_alice AS SELECT * FROM pageviews_original WHERE userid='alice';",
        Map("ksql.streams.auto.offset.reset" -> "earliest")
      )

      ValidationUtils.validateDecode[Request](expectedObj, "ksql/request.json")

    }

  }

}
