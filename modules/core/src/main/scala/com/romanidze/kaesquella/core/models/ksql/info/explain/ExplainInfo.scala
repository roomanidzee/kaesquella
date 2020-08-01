package com.romanidze.kaesquella.core.models.ksql.info.explain

import com.romanidze.kaesquella.core.models.ksql.KSQLResponse

case class ExplainObject(
  statement: String,
  description: QueryDescription,
  overridenProperties: Map[String, String]
) extends KSQLResponse
