package com.romanidze.kaesquella.core.models

case class ExecutionError(errorCode: Int, message: String)

case class ClientError(message: String, description: String)
