package com.romanidze.kaesquella.core.models

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ExecutionErrorTest extends AnyWordSpec with Matchers {

  "ExecutionError" should {

    "encode to json" in {

      ValidationUtils.validateEncode[ExecutionError](
        ExecutionError("1000", "test"),
        """{"error_code":"1000","message":"test"}"""
      )

    }

    "decode from json" in {

      ValidationUtils.validateDecode[ExecutionError](
        ExecutionError("4000", "some_message"),
        "error_response.json"
      )

    }

  }

}
