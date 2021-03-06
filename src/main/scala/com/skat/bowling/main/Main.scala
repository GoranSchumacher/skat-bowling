package com.skat.bowling.main

import com.ning.http.client.AsyncHttpClientConfig.Builder
import com.skat.bowling.caseclasses.{Game, GetResponse, PostRequest}
import play.api.libs.ws.DefaultWSClientConfig
import play.api.libs.ws.ning.{NingAsyncHttpClientConfigBuilder, NingWSClient}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

object Main {

  import play.api.libs.json._

  implicit val residentFormat = Json.format[GetResponse]
  implicit val postRequestFormat = Json.format[PostRequest]

  //See: http://carminedimascio.com/2015/02/how-to-use-the-play-ws-library-in-a-standalone-scala-app/

  def main(args: Array[String]): Unit = {

    val url = "http://37.139.2.74/api/points"
    val result = scala.io.Source.fromURL(url).mkString
    println(result)
    val json = Json.parse(result)
    val getResponse: Try[GetResponse] = json.validate[GetResponse] match {
      case s: JsSuccess[GetResponse] => {
        Success(s.get)
      }
      case e: JsError => {
        Failure(new Exception(s"Parse Exception: $e"))
      }
    }

    println(s"GET: $getResponse")
    val config = new NingAsyncHttpClientConfigBuilder(DefaultWSClientConfig()).build
    val builder = new Builder(config)
    val client = new NingWSClient(builder.build)

    val game = Game.createFromFrameResult(getResponse)
    println(s"Result.total: ${game.gameResultTotal}")

    val innerJson: String = Json.toJson(PostRequest(game.gameResultTotal)).toString()
    println(s"TOKEN: ${getResponse.get.token}")
    println(s"innerJson: ${innerJson}")

    client.url(s"$url?token=${getResponse.get.token}").post(innerJson).onComplete {
      case Success(content) => {
        println(s"Successful response: Status: ${content.status} Body: ${content.body}")
      }
      case Failure(t) => {
        println(s"An error has occured: ${t.getMessage}")
      }
    }
    Thread.sleep(5000)
    client.close()
  }

}
