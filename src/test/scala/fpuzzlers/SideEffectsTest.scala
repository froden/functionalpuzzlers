package fpuzzlers

import fpuzzlers.SideEffects.CreditCard
import fpuzzlers.SideEffects.Functional.{Payments, Charge, Cafe}
import org.scalatest.{MustMatchers, FunSuite}

import scala.util.Try

class SideEffectsTest extends FunSuite with MustMatchers {

  val cafe = new Cafe
  val cc1 = new CreditCard
  val cc2 = new CreditCard
  def payments(action: (CreditCard, Double) => Unit) = new Payments {
    override def charge(cc: CreditCard, price: Double): Unit = {
      action(cc, price)
    }
  }

  test("Buying 1 coffee yields cup and charge") {
    val (cup, charge) = cafe.buyCoffee(cc1)
    cup must not be null
    charge must be(Charge(cc1, 1.5))
  }

  test("Combining two charges yields single charge when same cc") {
    val charge1 = new Charge(cc1, 1.5)
    val charge2 = new Charge(cc1, 1.5)
    charge1 combine charge2 must be(Charge(cc1, 3.0))
  }

  test("Cannot combine two charges with different cc") {
    val charge1 = new Charge(cc1, 1.5)
    val charge2 = new Charge(cc2, 1.5)
    Try(charge1 combine charge2).isFailure must be(true)
  }

  test("Buy coffees yields n coffes and one charge") {
    val (cups, charge) = cafe.buyCoffees(cc1, 3)
    cups.size must be(3)
    charge must be(Charge(cc1, 4.5))
  }

  test("Payments can group charges by credit card") {
    val charges = Charge(cc1, 1.5) :: Charge(cc2, 1.5) :: Charge(cc1, 1.5) :: Nil
    val groupedCharges = payments((_, _) => ()).groupByCC(charges)
    groupedCharges.size must be(2)
    groupedCharges must contain(Charge(cc1, 3.0))
    groupedCharges must contain(Charge(cc2, 1.5))
  }

  test("Payments must process charges pr credit card") {
    val charges = Charge(cc1, 1.5) :: Charge(cc2, 1.5) :: Charge(cc1, 1.5) :: Nil
    val pService = payments { (cc: CreditCard, amount: Double) =>
      List(Charge(cc1, 3.0), Charge(cc2, 1.5)) must contain(Charge(cc, amount))
    }
    pService.charge(charges)
  }
}
