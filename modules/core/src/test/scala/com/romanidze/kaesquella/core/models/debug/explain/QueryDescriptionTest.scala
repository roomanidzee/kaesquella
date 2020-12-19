package com.romanidze.kaesquella.core.models.debug.explain

import com.romanidze.kaesquella.core.models.ValidationUtils
import com.romanidze.kaesquella.core.models.debug.shared.{FieldInfo, SchemaInfo}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class QueryDescriptionTest extends AnyWordSpec with Matchers {

  "Query description" should {

    "decode from json" in {

      val queryDescription = QueryDescription(
        "test",
        List(FieldInfo("test", SchemaInfo("STRING", None, None))),
        List("test", "test"),
        List("test", "test"),
        "test",
        "test"
      )

      val testObject = ExplainResult(queryDescription, Map("test" -> "test"))

      ValidationUtils.validateDecode[ExplainResult](testObject, "debug/explain/explain_result.json")

    }

  }

}
