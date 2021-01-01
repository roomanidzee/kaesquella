package com.romanidze.kaesquella.core.models

import org.scalatest.Assertion
import tethys._
import tethys.jackson._
import org.scalatest.matchers.should.Matchers
import tethys.JsonWriter
import tethys.readers.ReaderError

import scala.io.{BufferedSource, Source}

object ValidationUtils extends Matchers {

  def getJSONData(filePath: String): String = {

    val fileData: BufferedSource = Source.fromResource(filePath)
    val fileString: String = fileData.mkString
    fileData.close()

    fileString

  }

  def validateEncode[A: JsonWriter](obj: A, jsonString: String): Assertion = {

    val resultString: String = obj.asJson

    resultString shouldBe jsonString

  }

  def validateDecode[A: JsonReader](expectedObj: A, filePath: String): Assertion = {

    val fileString: String = getJSONData(filePath)

    val fileObj: Either[ReaderError, A] = fileString.jsonAs[A]

    fileObj should be(Symbol("right"))

    fileObj.toOption.get shouldBe expectedObj

  }

}
