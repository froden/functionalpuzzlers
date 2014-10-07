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
   */
  def recursiveSum(l: List[Int]): Int = ??? //TODO: implement this

  /*
   * Sum the list of integers using tail call optimized recursion.
   * You are not allowed to use mutable state (e.g. var), or loops of any kind (e.g. while, for).
   */
  def tailRecursiveSum(l: List[Int]): Int = ??? //TODO: implement this

  /*
   * Sum the list of integers using fold
   */
  def foldSum(l: List[Int]): Int = ??? //TODO: implement this

  /*
   * Sum the list of integers using reduce
   */
  def reduceSum(l: List[Int]): Int = ??? //TODO: implement this

}