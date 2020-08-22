package com.romanidze.kaesquella.monix

import monix.execution.atomic.AtomicAny
import monix.reactive.observers.Subscriber

/**
 * Utils package with some common things
 *
 * @author Andrey Romanov
 * @since 0.0.1
 * @version 0.0.1
 */
package object utils {
  type AtomicSubscriber = AtomicAny[Option[Subscriber[Array[Byte]]]]
}
