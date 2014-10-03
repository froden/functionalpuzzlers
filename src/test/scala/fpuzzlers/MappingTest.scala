package fpuzzlers

import fpuzzlers.Mapping.{Receipt, Message}
import org.scalatest.{MustMatchers, FunSuite}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import language.postfixOps

import scala.concurrent.{Await, Future}
import scala.util.Random

class MappingTest extends FunSuite with MustMatchers {

  val ids = (1 to 10000).map(_ => Random.nextInt().abs.toString).toList
  val messages = Random.shuffle(ids.map(new Message(_)))
  val receipts = Random.shuffle(ids.map(new Receipt(_)))

  test("imperative mapping") {
    val f = Future(Mapping.imperativeMapReceiptsToMessages(messages, receipts))
    val res = Await.result(f, 100 millis)
    res.map(_._1) must be(messages)
    res.map(_._2).flatten.forall(r => receipts.contains(r)) must be(true)
  }

  test("functional mapping") {
    val f = Future(Mapping.functionalMapReceiptsToMessages(messages, receipts))
    val res = Await.result(f, 100 millis)
    res.map(_._1) must be(messages)
    res.map(_._2).flatten.forall(r => receipts.contains(r)) must be(true)
  }
}
