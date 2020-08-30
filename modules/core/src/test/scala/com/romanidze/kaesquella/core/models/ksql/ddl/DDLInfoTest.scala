package com.romanidze.kaesquella.core.models.ksql.ddl

import com.romanidze.kaesquella.core.models.ValidationUtils
import tethys._
import tethys.jackson._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.io.Source
import scala.io.BufferedSource
import tethys.readers.ReaderError
import org.scalatest.EitherValues

class DDLInfoTest extends AnyWordSpec with Matchers with EitherValues {

  "DDLInfo" should {

    "encode to json" in {

      val testObj: DDLInfo = DDLInfo("test", "test", CommandStatus("test", "test"), 1)
      val json =
        """{"statementText":"test","commandId":"test","commandStatus":{"status":"test","message":"test"},"commandSequenceNumber":1}"""

      ValidationUtils.validateEncode[DDLInfo](testObj, json)

    }

    "decode from json" in {

      val testObjects = Seq(
        DDLInfo(
          "CREATE STREAM pageviews_home AS SELECT * FROM pageviews_original WHERE pageid='home';",
          "stream/PAGEVIEWS_HOME/create",
          CommandStatus("SUCCESS", "Stream created and running"),
          1
        ),
        DDLInfo(
          "CREATE STREAM pageviews_alice AS SELECT * FROM pageviews_original WHERE userid='alice';",
          "stream/PAGEVIEWS_ALICE/create",
          CommandStatus("SUCCESS", "Stream created and running"),
          2
        )
      )

      ValidationUtils.validateDecode[Seq[DDLInfo]](testObjects, "ksql/responses/ddl.json")

    }

  }

}
