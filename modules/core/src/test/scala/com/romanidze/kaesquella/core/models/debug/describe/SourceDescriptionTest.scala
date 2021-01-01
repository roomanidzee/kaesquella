package com.romanidze.kaesquella.core.models.debug.describe

import com.romanidze.kaesquella.core.models.ValidationUtils
import com.romanidze.kaesquella.core.models.debug.shared.{FieldInfo, SchemaInfo}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class SourceDescriptionTest extends AnyWordSpec with Matchers {

  "SourceDescription" should {

    "decode from json" in {

      val sourceDescription = SourceDescription(
        "test",
        List("test", "test"),
        List("test", "test"),
        List(FieldInfo("test", SchemaInfo("STRING", None, None))),
        "STREAM",
        "time",
        "time",
        "AVRO",
        "test",
        extended = false,
        None,
        None,
        None,
        None
      )

      val testObject = DescribeResult(sourceDescription)

      ValidationUtils
        .validateDecode[DescribeResult](testObject, "debug/describe/describe_result.json")

    }

  }

}
