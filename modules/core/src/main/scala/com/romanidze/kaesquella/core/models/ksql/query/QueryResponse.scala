package com.romanidze.kaesquella.core.models.ksql.query

import tethys._
import tethys.jackson._

import com.romanidze.kaesquella.core.models.ksql.KSQLResponse

case class QueryResponse(statement: String, queries: Seq[QueryInfo]) extends KSQLResponse

object QueryResponse {

  implicit val reader: JsonReader[QueryResponse] = JsonReader.builder
    .addField[String]("statementText")
    .addField[Seq[QueryInfo]]("queries")
    .buildReader(QueryResponse.apply)

  implicit val writer: JsonWriter[QueryResponse] = JsonWriter
    .obj[QueryResponse]
    .addField("statementText")(_.statement)
    .addField("queries")(_.queries)

}
