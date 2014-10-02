package fpuzzlers

import org.scalatest.{MustMatchers, FunSuite}
import DependencyInjection._

class DependencyInjectionTest extends FunSuite with MustMatchers {

  val bruker = new Bruker
  val brev = new Brev

  implicit object TestKontoDao extends KontoDao {
    override def hentBruker(i: Int) = bruker
  }

  implicit object TestBrevDao extends BrevDao {
    override def hentAlleBrev(bruker: Bruker) = List(brev)
  }

  test("oo dependency injection example") {
    val innboksService = new InnboksService(TestKontoDao, TestBrevDao)
    val res = innboksService.hentInnboks(1)
    res must be(List(brev))
  }

  test("fp dependency injection example") {
    val getInnboks = Innboks.hentInnboks(TestKontoDao, TestBrevDao)_
    val res = getInnboks(1)
    res must be(List(brev))
  }

  test("fp dependency injection with implicits example") {
    val res = Innboks.hentInnboks2(1)
    res must be(List(brev))
  }
}
