package com.romanidze.kaesquella.core.models.debug.shared

import com.romanidze.kaesquella.core.models.ValidationUtils
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class FieldInfoTest extends AnyWordSpec with Matchers {

  "FieldInfo" should {

    "decode from json" in {

      val testObject = FieldInfo("test", SchemaInfo("STRING", None, None))

      ValidationUtils.validateDecode[FieldInfo](testObject, "debug/shared/field_info.json")

    }

  }

}
