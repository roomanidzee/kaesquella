package com.romanidze.kaesquella.core.models.ksql.stream

import com.romanidze.kaesquella.core.models.ksql.KSQLResponse

case class StreamResponse(statement: String, streams: Seq[StreamInfo]) extends KSQLResponse
