package com.romanidze.kaesquella.core.models.ksql.query

import com.romanidze.kaesquella.core.models.ksql.KSQLResponse

case class QueryResponse(statement: String, queries: Seq[QueryInfo]) extends KSQLResponse
