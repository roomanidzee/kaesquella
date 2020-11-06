package com.romanidze.kaesquella.monix.utils

import java.nio.ByteBuffer

import com.typesafe.scalalogging.LazyLogging
import monix.execution.Ack

import monix.execution.Scheduler.Implicits.global
import monix.execution.atomic.AtomicBoolean

import scala.concurrent.Future

/**
 * Processing for data in byte buffer and make separate strings
 * @author Andrey Romanov and @quelgar. Original source: https://gist.github.com/quelgar/3e8aa15d3211f4c791acceeeef6beeef
 * @since 0.0.1
 * @version 0.0.1
 */
class ByteBufferProcessing(subscriber: AtomicSubscriber, stopOnNext: AtomicBoolean)
    extends LazyLogging {

  def onNextBuffer(buffer: ByteBuffer, elemBuf: ByteBuffer, delimiter: ByteBuffer): Future[Ack] = {

    if (stopOnNext.get() || subscriber.get().isEmpty) {
      Ack.Stop
    } else if (elemBuf.hasRemaining) {

      logger.debug("Element has {} remaining", elemBuf.remaining())

      if (elemBuf.remaining() > buffer.remaining()) {

        logger.debug(
          "Partial element copy: buffer remaining = {}, element position = {}",
          buffer.remaining(),
          elemBuf.position()
        )

        elemBuf.limit(elemBuf.position() + buffer.remaining())
        buffer.put(elemBuf)
        elemBuf.limit(elemBuf.capacity())

      } else {
        logger.debug("Full element copy")

        buffer.put(elemBuf)
      }

      logger.debug(
        "Before delimiter search: buffer position = {}, buffer limit = {}",
        buffer.position(),
        buffer.limit()
      )
      buffer.flip()
      searchDelimiter(elemBuf, buffer, delimiter)

    } else {
      logger.debug("Element processed")
      Ack.Continue
    }

  }

  private def searchDelimiter(
    elemBuf: ByteBuffer,
    buffer: ByteBuffer,
    delimiter: ByteBuffer
  ): Future[Ack] = {

    val initLimit: Int = buffer.limit()
    val initPosition: Int = buffer.position()

    logger.debug("search delimiter, buffer position = {}, limit = {}", buffer.position(), initLimit)

    if (ByteBufferUtils.findInBuffer(delimiter, buffer)) {
      logger.debug(
        "found delimiter, buffer position = {}, limit = {}",
        buffer.position(),
        buffer.limit()
      )

      buffer.limit(buffer.position())
      buffer.position(initPosition)

      val out = new Array[Byte](buffer.remaining())

      buffer.get(out)
      buffer.limit(initLimit)
      buffer.position(buffer.position() + delimiter.remaining())

      subscriber.get().get.onNext(out).flatMap {
        case Ack.Stop     => Ack.Stop
        case Ack.Continue => searchDelimiter(elemBuf, buffer, delimiter)
      }

    } else {

      logger.debug(
        "delimiter not found, buffer position = {}, limit = {}",
        buffer.position(),
        buffer.limit()
      )
      buffer.compact()

      onNextBuffer(buffer, elemBuf, delimiter)
    }

  }

}
