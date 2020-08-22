package com.romanidze.kaesquella.monix.utils

import java.net.URL
import java.nio.file.{Path, Paths}

import monix.eval.Task
import monix.nio.text.UTF8Codec._
import monix.nio.file
import monix.execution.Scheduler.Implicits.global
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class FramingTest extends AnyWordSpec with Matchers {

  "Framing" should {

    "be processed for input with multiple lines" in {

      val resourceURL: URL = getClass.getClassLoader.getResource("test.txt")
      val testFilePath: Path = Paths.get(resourceURL.toURI)

      val lineTerm = Array('\n'.toByte)

      val expectedList: List[String] = List("Test", "Lines", "For", "Framing", "Execution")

      val lines: Task[List[String]] = file
        .readAsync(testFilePath, 1000)
        .pipeThrough(Framing(lineTerm, 1000))
        .pipeThrough(utf8Decode)
        .toListL

      lines.runSyncUnsafe() should contain atLeastOneElementOf expectedList

    }

  }

}
