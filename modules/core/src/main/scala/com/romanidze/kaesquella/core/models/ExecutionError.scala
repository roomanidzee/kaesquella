package com.romanidze.kaesquella.core.models

import tethys._
import tethys.jackson._
import tethys.derivation.semiauto._

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

case class ClientError(message: String, description: String)

object ClientError {

  implicit val reader: JsonReader[ClientError] = jsonReader[ClientError]
  implicit val writer: JsonObjectWriter[ClientError] = jsonWriter[ClientError]

}
