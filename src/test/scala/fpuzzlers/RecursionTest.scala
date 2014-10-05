package fpuzzlers

import fpuzzlers.Recursion._
import org.scalatest.{FunSuite, MustMatchers}

import scala.util.Random

class RecursionTest extends FunSuite with MustMatchers {

  def numbers(n: Int) = List.fill(n)(Random.nextInt)

  test("recursiveSum must return the sum of the given list of integers") {
    val intList = numbers(1000)
    recursiveSum(intList) must be(intList.sum)
  }

  test("tailRecursiveSum must return the sum of the given list of integers") {
    val intList = numbers(1000000)
    tailRecursiveSum(intList) must be(intList.sum)
  }

  test("foldSum must return the sum of the given list of integers") {
    val intList = numbers(1000000)
    foldSum(intList) must be(intList.sum)
  }

  test("reduceSum must return the sum of the given list of integers") {
    val intList = numbers(1000000)
    reduceSum(intList) must be(intList.sum)
  }

}
