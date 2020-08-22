package com.romanidze.kaesquella.monix.utils

import java.nio.ByteBuffer

import monix.reactive.{Observable, Observer, Pipe}

final class Framing(delimiter: ByteBuffer, maximumLength: Int)
    extends Pipe[Array[Byte], Array[Byte]] {

  override def unicast: (Observer[Array[Byte]], Observable[Array[Byte]]) = {

    val framingSubject = new FramingSubject(delimiter, maximumLength)

    (framingSubject, framingSubject)

  }

}

object Framing {

  def apply(delimiter: Array[Byte], maximumLength: Int) =
    new Framing(ByteBuffer.wrap(delimiter), maximumLength)

}
