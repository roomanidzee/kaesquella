package com.romanidze.kaesquella.core.models.pull

import org.json4s.JsonAST.JArray

/**
 * Class for representing a pull query response
 * @param isSchema flag which control, if response contains schema or not
 * @param schema schema of response (None in case of data response)
 * @param data data from the response (None in case of schema response)
 *
 * @author Andrey Romanov
 * @since 0.0.1
 */
case class PullResponse(isSchema: Boolean, schema: Option[ResponseSchema], data: Option[JArray])
