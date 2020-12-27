package com.romanidze.kaesquella.core.models

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import org.scalatest.EitherValues

class VersionInfoTest extends AnyWordSpec with Matchers with EitherValues {

  "VersionInfo" should {

    "encode to json" in {

      val childObj: VersionInfo = VersionInfo("0.0.1", "test", "test")
      val testObj: KSQLVersionResponse = KSQLVersionResponse(childObj)

      ValidationUtils.validateEncode[KSQLVersionResponse](
        testObj,
        """{"KsqlServerInfo":{"version":"0.0.1","kafkaClusterId":"test","ksqlServiceId":"test"}}"""
      )

    }

    "decode from json" in {

      val childObj: VersionInfo = VersionInfo("5.1.2", "j3tOi6E_RtO_TMH3gBmK7A", "default_")
      val testObj: KSQLVersionResponse = KSQLVersionResponse(childObj)

      ValidationUtils.validateDecode[KSQLVersionResponse](testObj, "version_info.json")

    }

  }

}
