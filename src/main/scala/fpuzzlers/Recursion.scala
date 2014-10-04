package fpuzzlers

import scala.annotation.tailrec

/*
 * In this exercise ...
 */
object Recursion extends App {

  val numbers = List(2,4,7,19,25)

  // Fold right
  val recursiveResult = {
    def sum(l: List[Int]): Int = {
      l match {
        case head :: tail => head + sum(tail)
        case Nil => 0
      }
    }
    sum(numbers)
  }

  println(recursiveResult)

  // Fold left
  val tailRecRecursiveResult = {
    def sum(l: List[Int]): Int = {
      @tailrec
      def innerSum(l: List[Int], accumulator: Int): Int = {
        l match {
          case head :: tail => innerSum(tail, accumulator + head)
          case Nil => accumulator
        }
      }
      innerSum(l, 0)
    }
    sum(numbers)
  }


  val foldResult = numbers.foldLeft(0)(_ + _)
  val reduceResult = numbers.reduceLeft(_ + _)


  println(foldResult)
  println(reduceResult)

}