package com.romanidze.kaesquella.core.models

import tethys._
import tethys.jackson._

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.io.Source
import scala.io.BufferedSource
import tethys.readers.ReaderError
import org.scalatest.EitherValues

class VersionInfoTest extends AnyWordSpec with Matchers with EitherValues {

  "VersionInfo" should {

    "encode to json" in {

      val childObj: VersionInfo = VersionInfo("0.0.1", "test", "test")
      val testObj: KSQLVersionResponse = KSQLVersionResponse(childObj)

      val json =
        """{"KsqlServerInfo":{"version":"0.0.1","kafkaClusterId":"test","ksqlServiceId":"test"}}"""

      val resultString: String = testObj.asJson

      resultString shouldBe json

    }

    "decode to json" in {

      val fileData: BufferedSource = Source.fromResource("version_info.json")
      val fileString: String = fileData.mkString
      fileData.close()

      val fileObj: Either[ReaderError, KSQLVersionResponse] = fileString.jsonAs[KSQLVersionResponse]

      fileObj should be('right)

      val versionInfo: VersionInfo = fileObj.right.get.info

      versionInfo.version shouldBe "5.1.2"
      versionInfo.clusterID shouldBe "j3tOi6E_RtO_TMH3gBmK7A"
      versionInfo.serviceID shouldBe "default_"

    }

  }

}
