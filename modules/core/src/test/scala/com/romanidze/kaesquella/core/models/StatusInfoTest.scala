package com.romanidze.kaesquella.core.models

import tethys._
import tethys.jackson._

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.io.Source
import scala.io.BufferedSource
import tethys.readers.ReaderError
import org.scalatest.EitherValues

class StatusInfoTest extends AnyWordSpec with Matchers with EitherValues {

  "StatusInfo" should {

    "encode to json" in {

      ValidationUtils.validateEncode[StatusInfo](
        StatusInfo("SUCCESS", "test_message"),
        """{"status":"SUCCESS","message":"test_message"}"""
      )

    }

    "decode from json" in {

      ValidationUtils.validateDecode[StatusInfo](
        StatusInfo("SUCCESS", "Stream created and running"),
        "status.json"
      )

    }

  }

}
