package com.romanidze.kaesquella.core.models.ksql.table

import com.romanidze.kaesquella.core.models.ksql.KSQLResponse

case class TableResponse(statement: String, tables: Seq[TableInfo]) extends KSQLResponse
