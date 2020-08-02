package com.romanidze.kaesquella.core.models.ksql

import tethys._
import tethys.jackson._

/**
 * Case class, which represents most of queries for '/ksql' endpoint
 *
 * @param input Input KSQL query (single or multiple)
 * @param properties Map of settings for input KSQL query
 *
 * @author Andrey Romanov
 * @since 0.0.1
 * @todo Add support for describe and explain queries
 */
case class Request(input: String, properties: Map[String, String])

object Request {

  implicit val reader: JsonReader[Request] = JsonReader.builder
    .addField[String]("ksql")
    .addField[Map[String, String]]("streamsProperties")
    .buildReader(Request.apply)

  implicit val writer: JsonWriter[Request] = JsonWriter
    .obj[Request]
    .addField("ksql")(_.input)
    .addField("streamsProperties")(_.properties)

}
