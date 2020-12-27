package com.romanidze.kaesquella.core.models.terminate

import com.romanidze.kaesquella.core.models.ValidationUtils
import org.scalatest.EitherValues
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TopicsForTerminateTest extends AnyWordSpec with Matchers with EitherValues {

  "TopicsForTerminate" should {

    "encode to json" in {

      val testObj = TopicsForTerminate(List("FOO", "bar.*"))
      val jsonString = """{"deleteTopicList":["FOO","bar.*"]}""".stripMargin

      ValidationUtils.validateEncode[TopicsForTerminate](testObj, jsonString)

    }

  }

}
