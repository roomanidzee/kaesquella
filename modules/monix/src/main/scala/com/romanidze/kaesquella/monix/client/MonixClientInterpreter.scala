package com.romanidze.kaesquella.monix.client

import java.nio.ByteBuffer

import tethys._
import tethys.jackson._
import sttp.client._
import sttp.client.asynchttpclient.monix._
import com.romanidze.kaesquella.core.client.ClientInterpreter
import com.romanidze.kaesquella.core.models.ksql.{Request => KSQLInfoRequest}
import com.romanidze.kaesquella.core.models.ksql.ddl.DDLInfo
import com.romanidze.kaesquella.core.models.ksql.query.QueryResponse
import com.romanidze.kaesquella.core.models.ksql.stream.StreamResponse
import com.romanidze.kaesquella.core.models.{processBody, ClientError, KSQLVersionResponse, StatusInfo}
import com.romanidze.kaesquella.core.models.ksql.table.TableResponse
import com.romanidze.kaesquella.core.models.query.{Request => KSQLQueryRequest}
import com.romanidze.kaesquella.core.models.query.row.RowInfo
import com.romanidze.kaesquella.monix.utils.processRowInfo
import monix.eval.Task
import monix.reactive.Observable
import org.asynchttpclient.{AsyncHttpClient, DefaultAsyncHttpClient}

import scala.concurrent.duration.Duration

class MonixClientInterpreter(baseURL: String, httpClient: AsyncHttpClient)
    extends ClientInterpreter[Task, Observable] {

  def this(baseURL: String) = this(baseURL, new DefaultAsyncHttpClient())

  private implicit val backend: MonixBackend = AsyncHttpClientMonixBackend.usingClient(httpClient)

  /**
   * Method for retrieving information about query status
   *
   * @return query status
   */
  override def getQueryStatus(queryID: String): Task[Either[ClientError, StatusInfo]] = {

    val requestURL: String = s"${baseURL}/status/${queryID}"

    val request: ClientRequest = basicRequest
      .header("Accept", "application/vnd.ksql.v1+json")
      .header("Content-Type", "application/vnd.ksql.v1+json")
      .get(uri"${requestURL}")

    val response: ClientSimpleResponse = request.send()

    response.map(elem => processBody[StatusInfo](elem.body))

  }

  /**
   * Method for retrieving server information
   *
   * @return server information
   */
  override def getServerVersion: Task[Either[ClientError, KSQLVersionResponse]] = {

    val requestURL: String = s"${baseURL}/info"

    val request: ClientRequest = basicRequest
      .header("Accept", "application/json")
      .header("Content-Type", "application/json")
      .get(uri"${requestURL}")

    val response: ClientSimpleResponse = request.send()

    response.map(elem => processBody[KSQLVersionResponse](elem.body))

  }

  /**
   * Method for retrieving results of select request
   *
   * @param request request instance with query and properties
   * @return row information with data
   */
  override def runQueryRequest(inputRequest: KSQLQueryRequest): RowInfoResponse = {

    val requestURL: String = s"${baseURL}/query"

    val request: ClientStreamingRequest = basicRequest
      .header("Accept", "application/vnd.ksql.v1+json")
      .header("Content-Type", "application/vnd.ksql.v1+json")
      .post(uri"${requestURL}")
      .body(inputRequest.asJson)
      .response(asStream[Observable[ByteBuffer]])
      .readTimeout(Duration.Inf)

    val response: ClientStreamingResponse = request.send()

    response.map(elem => processRowInfo(elem.body))

  }

  /**
   * Execution for CREATE / DROP / TERMINATE commands
   *
   * @param request request instance with query (queries) and properties
   * @return information about execution result
   */
  override def runDDLRequest(request: KSQLInfoRequest): Task[Either[ClientError, DDLInfo]] = {

    val requestURL: String = s"${baseURL}/ksql"

    val clientRequest: ClientRequest = basicRequest
      .header("Accept", "application/vnd.ksql.v1+json")
      .header("Content-Type", "application/vnd.ksql.v1+json")
      .post(uri"${requestURL}")
      .body(request.asJson)

    val response: ClientSimpleResponse = clientRequest.send()

    response.map(elem => processBody[DDLInfo](elem.body))

  }

  /**
   * Execution for SHOW STREAMS command
   *
   * @return information about KSQL streams
   */
  override def getStreams: Task[Either[ClientError, StreamResponse]] = {

    val request: KSQLInfoRequest = KSQLInfoRequest("SHOW STREAMS", Map.empty[String, String])

    val requestURL: String = s"${baseURL}/ksql"

    val clientRequest: ClientRequest = basicRequest
      .header("Accept", "application/vnd.ksql.v1+json")
      .header("Content-Type", "application/vnd.ksql.v1+json")
      .post(uri"${requestURL}")
      .body(request.asJson)

    val response: ClientSimpleResponse = clientRequest.send()

    response.map(elem => processBody[StreamResponse](elem.body))

  }

  /**
   * Execution for SHOW TABLES command
   *
   * @return information about KSQL tables
   */
  override def getTables: Task[Either[ClientError, TableResponse]] = {

    val request: KSQLInfoRequest = KSQLInfoRequest("SHOW TABLES", Map.empty[String, String])

    val requestURL: String = s"${baseURL}/ksql"

    val clientRequest: ClientRequest = basicRequest
      .header("Accept", "application/vnd.ksql.v1+json")
      .header("Content-Type", "application/vnd.ksql.v1+json")
      .post(uri"${requestURL}")
      .body(request.asJson)

    val response: ClientSimpleResponse = clientRequest.send()

    response.map(elem => processBody[TableResponse](elem.body))

  }

  /**
   * Execution for SHOW QUERIES command
   *
   * @return information about KSQL queries
   */
  override def getQueries: Task[Either[ClientError, QueryResponse]] = {

    val request: KSQLInfoRequest = KSQLInfoRequest("SHOW QUERIES", Map.empty[String, String])

    val requestURL: String = s"${baseURL}/ksql"

    val clientRequest: ClientRequest = basicRequest
      .header("Accept", "application/vnd.ksql.v1+json")
      .header("Content-Type", "application/vnd.ksql.v1+json")
      .post(uri"${requestURL}")
      .body(request.asJson)

    val response: ClientSimpleResponse = clientRequest.send()

    response.map(elem => processBody[QueryResponse](elem.body))

  }
}
