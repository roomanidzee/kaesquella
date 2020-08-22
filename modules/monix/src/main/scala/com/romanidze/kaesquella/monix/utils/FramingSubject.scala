package com.romanidze.kaesquella.monix.utils

import java.nio.ByteBuffer

import com.typesafe.scalalogging.Logger
import monix.execution.atomic.{Atomic, AtomicBoolean}
import monix.execution.exceptions
import monix.execution.{Ack, Cancelable}
import monix.reactive.observers.Subscriber
import monix.reactive.subjects.Subject

import scala.concurrent.Future

/**
 * Main subject for process data
 * @param delimiter symbol to split
 * @param maximumLength max symbol length
 * @author Andrey Romanov and @quelgar. Original source: https://gist.github.com/quelgar/3e8aa15d3211f4c791acceeeef6beeef
 * @since 0.0.1
 * @version 0.0.1
 */
class FramingSubject(delimiter: ByteBuffer, maximumLength: Int)
    extends Subject[Array[Byte], Array[Byte]] {

  val logger: Logger = Logger(classOf[FramingSubject])

  private[this] val subscriber: AtomicSubscriber = Atomic(Option.empty[Subscriber[Array[Byte]]])
  private[this] val stopOnNext: AtomicBoolean = Atomic(false)

  private var buffer: ByteBuffer = _

  override def size: Int = if (this.subscriber.get().nonEmpty) 1 else 0

  override def onNext(elem: Array[Byte]): Future[Ack] = {

    logger.debug("onNext: element size = {}", elem.length)

    val bufferProcessing = new ByteBufferProcessing(subscriber, stopOnNext)

    bufferProcessing.onNextBuffer(buffer, ByteBuffer.wrap(elem), delimiter)
  }

  override def onError(ex: Throwable): Unit = subscriber.get.foreach(_.onError(ex))

  override def onComplete(): Unit = subscriber.get.foreach(_.onComplete())

  override def unsafeSubscribeFn(subscriberFn: Subscriber[Array[Byte]]): Cancelable = {

    if (!this.subscriber.compareAndSet(None, Some(subscriberFn))) {

      subscriberFn.onError(exceptions.APIContractViolationException(this.getClass.getName))
      Cancelable.empty

    } else {
      buffer = ByteBuffer.allocate(maximumLength)
      Cancelable(() => stopOnNext.set(true))
    }

  }
}
