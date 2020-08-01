package com.romanidze.kaesquella.core.models.ksql.table

import tethys._
import tethys.jackson._

import com.romanidze.kaesquella.core.models.ksql.KSQLResponse

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
