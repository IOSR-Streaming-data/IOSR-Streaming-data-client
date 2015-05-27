package pl.edu.agh.iosr.iosr2015.data.streaming.twitter.client

import java.util.UUID
//
//import com.websudos.phantom.sample.ExampleModel
//import com.websudos.phantom.dsl._
//
//case class TDIDF(
//                          id: Int,
//                          name: String,
//                          props: Map[String, String],
//                          timestamp: Int,
//                          test: Option[Int]
//                          )
//
//sealed class ExampleRecord extends CassandraTable[ExampleRecord, ExampleModel] {
//
//  object id extends UUIDColumn(this) with PartitionKey[UUID]
//  object timestamp extends DateTimeColumn(this) with ClusteringOrder[DateTime] with Ascending
//  object name extends StringColumn(this)
//  object props extends MapColumn[ExampleRecord, ExampleModel, String, String](this)
//  object test extends OptionalIntColumn(this)
//
//  def fromRow(row: Row): ExampleModel = {
//    ExampleModel(id(row), name(row), props(row), timestamp(row), test(row));
//  }
//}