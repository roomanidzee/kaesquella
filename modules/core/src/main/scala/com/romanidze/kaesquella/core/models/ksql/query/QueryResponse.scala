package com.romanidze.kaesquella.core.models.ksql.query

import tethys._
import tethys.jackson._

/**
 * Class for representing `LIST QUERIES` and `SHOW QUERIES` results
 *
 * @param statement source statement
 * @param queries sequence of queries
 *
 * @author Andrey Romanov
 * @since 0.0.1
 */
case class QueryResponse(statement: String, queries: Seq[QueryInfo])

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
