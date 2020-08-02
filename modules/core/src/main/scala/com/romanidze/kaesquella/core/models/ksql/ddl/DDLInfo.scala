package com.romanidze.kaesquella.core.models.ksql.ddl

import tethys._
import tethys.jackson._

import com.romanidze.kaesquella.core.models.ksql.KSQLResponse

/**
 * Class for representing execution result for such queries, like `CREATE`, `DROP`, `TERMINATE`
 *
 * @param statement ksql statement
 * @param commandID command, which was created by statement (ID for status retrieve)
 * @param status result of statement
 *
 * @author Andrey Romanov
 * @since 0.0.1
 */
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
