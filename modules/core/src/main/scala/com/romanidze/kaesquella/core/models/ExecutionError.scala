package com.romanidze.kaesquella.core.models

import tethys._
import tethys.jackson._
import tethys.derivation.semiauto._

/**
 * Class for representing an error instance from KSQL Server
 *
 * @param errorCode error code from KSQL server
 * @param message error message from KSQL server
 *
 * @author Andrey Romanov
 * @since 0.0.1
 */
case class ExecutionError(errorCode: String, message: String)

object ExecutionError {

  implicit val reader: JsonReader[ExecutionError] = JsonReader.builder
    .addField[String]("error_code")
    .addField[String]("message")
    .buildReader(ExecutionError.apply)

  implicit val writer: JsonWriter[ExecutionError] = JsonWriter
    .obj[ExecutionError]
    .addField("error_code")(_.errorCode)
    .addField("message")(_.message)

}

/**
 * Case class for client error (by many backends)
 *
 * @param message error message
 * @param description error description
 * @param traceback description in case of deserialization error
 * @author Andrey Romanov
 * @since 0.0.1
 */
case class ClientError(
  message: String,
  description: Option[ExecutionError],
  traceback: Option[String]
)
