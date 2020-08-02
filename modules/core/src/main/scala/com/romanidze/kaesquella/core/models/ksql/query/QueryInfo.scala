package com.romanidze.kaesquella.core.models.ksql.query

import tethys._
import tethys.jackson._

/**
 * Class for information about retrieved queries
 *
 * @param query statement, which started the query
 * @param sinks streams and tables for query results
 * @param id query ID
 *
 * @author Andrey Romanov
 * @since 0.0.1
 */
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
