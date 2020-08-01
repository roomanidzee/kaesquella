package com.romanidze.kaesquella.core.models.ksql.ddl

import com.romanidze.kaesquella.core.models.ksql.KSQLResponse

case class DDLInfo(statement: String, commandID: String, status: CommandStatus) extends KSQLResponse
