package fpuzzlers

import fpuzzlers.Folds.{Document, Page}
import org.scalatest.{MustMatchers, FunSuite}

class FoldsTest extends FunSuite with MustMatchers {
  val documents: List[Document] = List(
    Document(List(Page("Page1"), Page("Page2"))),
    Document(List(Page("Page3"), Page("Page4"))),
    Document(List(Page("Page5"), Page("Page6")))
  )

  val expected = new Document(List(Page("Page1"), Page("Page2"), Page("Page3"), Page("Page4"), Page("Page5"), Page("Page6")))

  test("imperative merge should yield single document") {
    val result = Folds.combineAllImperative(documents)
    result must be(expected)
  }

  test("functional merge should yield single document") {
    val result = Folds.combineAllFunctional(documents)
    result must be(expected)
  }
}
