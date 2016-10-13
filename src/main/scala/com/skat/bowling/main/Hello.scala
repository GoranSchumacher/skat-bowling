package com.skat.bowling.main

import com.ning.http.client.AsyncHttpClientConfig
import com.skat.bowling.com.skat.caseclasses.{Game, GetResponse, PostRequest, Tuple2Int}
import play.api.libs.json.{JsPath, Reads, Json, Writes}
import play.api.libs.ws.{WSResponse, DefaultWSClientConfig}
import play.api.libs.ws.ning.{NingAsyncHttpClientConfigBuilder, NingWSClient}
import play.api.libs.functional.syntax._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.reflect.macros.ParseException
import scala.util.{Try, Success, Failure}

object Hello {

//  implicit val getResponseWrites = new Writes[GetResponse] {
//    def writes(getResponse: GetResponse) = Json.obj(
//      "points" -> getResponse.points.toString(),
//      "token" -> getResponse.token
//    )
//  }
//
//  implicit val getResponseReads: Reads[GetResponse] = (
//    (JsPath \ "points").read[Set[Tuple2[Int, Int]]] and
//      (JsPath \ "token").read[String]
//    )(GetResponse.apply _)

  import play.api.libs.json._
//
//  implicit def tuple2Writes[A, B](implicit a: Writes[A], b: Writes[B]): Writes[Tuple2[A, B]] = new Writes[Tuple2[A, B]] {
//    def writes(tuple: Tuple2[A, B]) = JsArray(Seq(a.writes(tuple._1), b.writes(tuple._2)))
//  }

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
        Failure(new Exception("Parse Exception: " + e))
      }
    }

    println("GET: " + getResponse)
    val config = new NingAsyncHttpClientConfigBuilder(DefaultWSClientConfig()).build
    val builder = new AsyncHttpClientConfig.Builder(config)
    val client = new NingWSClient(builder.build)

    //val innerJson: String = Json.toJson(PostRequest(getResponse.get.token, Set())).toString()
    val innerJson2: String = Json.toJson(PostRequest(getResponse.get.token, Seq())).toString()
    println(s"TOKEN: "+ getResponse.get.token)
    println(s"innerJson2: "+ innerJson2)

    val game = new Game()

    game.frames(0).setThrows(Array(7,3))
    game.frames(1).setThrows(Array(3))
    game.frames(2).setThrows(Array(10))
    game.frames(3).setThrows(Array(7,3))
    game.frames(4).setThrows(Array(4,2))
    println(s"Result.total: ${game.gameResultTotal}")
    //println(s"Result.Upto(1): ${game.gameResultUptoFrame(1)}")
    //println(s"Result.Upto(2): ${game.gameResultUptoFrame(2)}")
    //println(s"Result.Upto(3): ${game.gameResultUptoFrame(3)}")
    //println(s"Result.Upto(4): ${game.gameResultUptoFrame(4)}")

    //client.url(url).post(Map("token" -> getResponse.get.token, "points" -> innerJson).toString()).onComplete {
    client.url(url).post(innerJson2).onComplete {
        case Success(content) => {
          println("Successful response: " + Json.toJson(content.body))
        }
        case Failure(t) => {
          println("An error has occured: " + t.getMessage)
        }
      }
    //}

  }
}
