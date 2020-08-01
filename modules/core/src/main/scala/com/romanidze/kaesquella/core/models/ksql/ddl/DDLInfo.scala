package com.romanidze.kaesquella.core.models.ksql.ddl

import tethys._
import tethys.jackson._

import com.romanidze.kaesquella.core.models.ksql.KSQLResponse

case class DDLInfo(statement: String, commandID: String, status: CommandStatus) extends KSQLResponse

object DDLInfo {

  implicit val reader: JsonReader[DDLInfo] = JsonReader.builder
    .addField[String]("statementText")
    .addField[String]("commandId")
    .addField[CommandStatus]("commandStatus")
    .buildReader(DDLInfo.apply)

  implicit val writer: JsonWriter[DDLInfo] = JsonWriter
    .obj[DDLInfo]
    .addField("statementText")(_.statement)
    .addField("commandId")(_.commandID)
    .addField("commandStatus")(_.status)

}
