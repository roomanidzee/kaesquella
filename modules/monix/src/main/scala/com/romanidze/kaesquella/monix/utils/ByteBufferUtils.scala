package com.romanidze.kaesquella.monix.utils

import java.nio.ByteBuffer

import com.typesafe.scalalogging.LazyLogging

import scala.util.control.Breaks

/**
 * Utils for working with Byte Buffer
 *
 * @author Andrey Romanov
 * @since 0.0.1
 * @version 0.0.1
 */
object ByteBufferUtils extends LazyLogging {

  /**
   * Method for searching a symbol in buffer
   * @param search symbol for search in byte data
   * @param data byte data
   * @return info, if it exists, or not
   * @author Andrey Romanov and @quelgar. Original source: https://gist.github.com/quelgar/3e8aa15d3211f4c791acceeeef6beeef
   * @since 0.0.1
   * @version 0.0.1
   */
  def findInBuffer(search: ByteBuffer, data: ByteBuffer): Boolean = {
    val initDataPos: Int = data.position
    logger.debug("init data position: {}", initDataPos)
    while (data.hasRemaining) {
      if (data.remaining < search.remaining) {
        return false
      }
      val dataMatch = data.asReadOnlyBuffer
      var matched = true
      val myBreaks = new Breaks
      myBreaks.breakable {
        while (search.hasRemaining && dataMatch.hasRemaining) {
          if (search.get() != dataMatch.get()) {
            matched = false
            myBreaks.break()
          }
        }
      }
      search.position(0)
      if (matched) {
        return true
      }
      data.get()
    }
    data.position(initDataPos)
    false
  }

}
