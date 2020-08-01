package com.romanidze.kaesquella.core.models.ksql.info.describe

import com.romanidze.kaesquella.core.models.ksql.KSQLResponse

case class DescribeInfo(statementText: String, description: SourceDescription) extends KSQLResponse
