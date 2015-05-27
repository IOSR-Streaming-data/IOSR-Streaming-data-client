package pl.edu.agh.iosr.iosr2015.data.streaming.twitter.client

import akka._
import akka.actor.ActorSystem
import _root_.spray.httpx.SprayJsonSupport
import _root_.spray.httpx.marshalling.ToResponseMarshallable
import _root_.spray.json.DefaultJsonProtocol
import _root_.spray.routing._
import us.bleibinha.spray.json.macros.lazyy.json

import scala.concurrent.Future
import scala.language.postfixOps
import scala.util.Random
import scala.concurrent.duration._

trait Backend {
  val system = ActorSystem("LOCAL")
}


@json case class TFIDF(doc: Long, term: String, tfidf: Double = Math.log(Random.nextInt(200)))

trait WebApi extends Directives with SprayJsonSupport with DefaultJsonProtocol {
  this: Backend =>

  import system.dispatcher

  import TFIDF._

  val cachePerWord = scala.collection.mutable.Map.empty[String, ToResponseMarshallable]
  val cacheTFIDF = scala.collection.mutable.Map.empty[(String, String), ToResponseMarshallable]

  def idfsPerWord(wordId: String): ToResponseMarshallable = {
    if (!cachePerWord.contains(wordId)) {
      cachePerWord += wordId -> queryDatabaseTFIFDsByWord(wordId)
    }
    cachePerWord(wordId)
  }

  def queryDatabaseTFIFDsByWord(wordId: String): ToResponseMarshallable = {
    val a = (1 to Random.nextInt(30) map {
      i => TFIDF(Random.nextInt(10), wordId, if (Math.abs(Random.nextGaussian()) > 0.20) Random.nextGaussian() + 1 else 0)
    }).groupBy(_.doc).mapValues(_.head).values.toList.take(Random.nextInt(5) + 3)
    cacheTFIDF ++= a.map(x => (x.doc.toString -> x.term) -> (x: ToResponseMarshallable)).toMap
    a
  }

  def queryDatabaseTFIDFByWordAndId(docId: String, termId: String): ToResponseMarshallable = {
    if (!cacheTFIDF.contains((docId, termId))) {
      cacheTFIDF += (docId, termId) -> (TFIDF(docId.toLong, termId, Random.nextDouble()): ToResponseMarshallable)
    }
    cacheTFIDF((docId, termId))
  }

  def executeQuery[T](value: T): Future[T] = pattern.after(
    (Random.nextInt(800) + 200) millis, using = system.scheduler
  )(Future.successful(value))

  val routes: Route =
    pathEndOrSingleSlash {
      get {
        complete("Hello from IOSR2015-twitter-streaming-data-client application")
      }
    } ~
    pathPrefix("tfidf") {
      pathEndOrSingleSlash(get(complete("returning all tfidfs"))) ~
      pathPrefix(Segment) { termId =>
        pathEndOrSingleSlash {
          get(complete(executeQuery(idfsPerWord(termId))))
        } ~
        pathPrefix(Segment) { docId =>
          complete(executeQuery(queryDatabaseTFIDFByWordAndId(docId, termId)))
        }
      }
    }
}


