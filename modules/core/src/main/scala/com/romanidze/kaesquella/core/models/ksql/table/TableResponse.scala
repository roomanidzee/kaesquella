package com.romanidze.kaesquella.core.models.ksql.table

import tethys._
import tethys.jackson._

import com.romanidze.kaesquella.core.models.ksql.KSQLResponse

/**
 * Class for representing `LIST TABLES` and `SHOW TABLES` results
 *
 * @param statement source statment
 * @param tables sequence of tables
 *
 * @author Andrey Romanov
 * @since 0.0.1
 */
case class TableResponse(statement: String, tables: Seq[TableInfo]) extends KSQLResponse

object TableResponse {

  implicit val reader: JsonReader[TableResponse] = JsonReader.builder
    .addField[String]("statementText")
    .addField[Seq[TableInfo]]("tables")
    .buildReader(TableResponse.apply)

  implicit val writer: JsonWriter[TableResponse] = JsonWriter
    .obj[TableResponse]
    .addField("statementText")(_.statement)
    .addField("tables")(_.tables)

}
