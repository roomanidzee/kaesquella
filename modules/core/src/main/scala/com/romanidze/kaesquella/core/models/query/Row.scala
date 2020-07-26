package com.romanidze.kaesquella.core.models.query

object Row {

  case class Data(columns: Seq[Any])

  case class RowInfo(row: Data, errorMessage: String, finalMessage: String)

}
