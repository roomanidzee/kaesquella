package com.romanidze.kaesquella.core.models.ksql.query

import tethys._
import tethys.jackson._

case class QueryInfo(query: String, sinks: String, id: String)

object QueryInfo {

  implicit val reader: JsonReader[QueryInfo] = JsonReader.builder
    .addField[String]("queryString")
    .addField[String]("sinks")
    .addField[String]("id")
    .buildReader(QueryInfo.apply)

  implicit val writer: JsonWriter[QueryInfo] = JsonWriter
    .obj[QueryInfo]
    .addField("queryString")(_.query)
    .addField("sinks")(_.sinks)
    .addField("id")(_.id)

}
