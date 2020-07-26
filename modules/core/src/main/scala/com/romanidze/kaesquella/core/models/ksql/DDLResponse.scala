package com.romanidze.kaesquella.core.models.ksql

trait KSQLResponse

object DDLResponse {

  case class CommandStatus(status: String, message: String)

  case class DDLInfo(statement: String, commandID: String, status: CommandStatus)
      extends KSQLResponse

  case class StreamInfo(name: String, topic: String, format: String)

  case class StreamResponse(statement: String, streams: Seq[StreamInfo]) extends KSQLResponse

  case class TableInfo(name: String, topic: String, format: String)

  case class TableResponse(statement: String, tables: Seq[TableInfo]) extends KSQLResponse

  case class QueryInfo(query: String, sinks: String, id: String)

  case class QueryResponse(statement: String, queries: Seq[QueryInfo]) extends KSQLResponse

}
