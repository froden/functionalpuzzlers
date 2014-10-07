package fpuzzlers

import scala.annotation.tailrec

/*
 * In this exercise you will calculate the sum of a list of integers in different functional ways.
 * You are not allowed to use mutable state (e.g. var), or loops of any kind (e.g. while, for).
 */
object Recursion extends App {

  /*
   * Sum the list of integers using recursion.
   * You are not allowed to use mutable state (e.g. var), or loops of any kind (e.g. while, for).
   *
   * Same as foldRight
   * List(2,4,7,19,25).foldRight(0)(_+_)
   *
   *    f
   *   / \
   *  2   f
   *     / \
   *    4   f
   *       / \
   *      7   f
   *         / \
   *        19  f
   *           / \
   *          25  0
   */
  def recursiveSum(l: List[Int]): Int = {
    l match {
      case head :: tail => head + recursiveSum(tail)
      case Nil => 0
    }
  }

  /*
   * Sum the list of integers using tail call optimized recursion.
   * You are not allowed to use mutable state (e.g. var), or loops of any kind (e.g. while, for).
   *
   * Same as foldLeft
   * List(2,4,7,19,25).foldLeft(0)(_+_)
   *
   *             f
   *            / \
   *           f  25
   *          / \
   *         f  19
   *        / \
   *       f   7
   *      / \
   *     f   4
   *    / \
   *   0   2
   */
  def tailRecursiveSum(l: List[Int]): Int = {
    @tailrec
    def innerSum(l: List[Int], accumulator: Int): Int = {
      l match {
        case head :: tail => innerSum(tail, accumulator + head)
        case Nil => accumulator
      }
    }
    innerSum(l, 0)
  }

  /*
   * Sum the list of integers using fold
   */
  def foldSum(l: List[Int]): Int = l.foldLeft(0)(_ + _)

  /*
   * Sum the list of integers using reduce
   */
  def reduceSum(l: List[Int]): Int = l.reduceLeft(_ + _)

}