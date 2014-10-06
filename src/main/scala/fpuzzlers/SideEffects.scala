package fpuzzlers

object SideEffects {

  class CreditCard

  class Coffee {
    def price = 1.50
  }

  object Imperative {

    class Cafe {
      def buyCoffee(cc: CreditCard, p: Payments): Coffee = {
        val cup = new Coffee()
        p.charge(cc, cup.price)
        cup
      }

      def buyCoffees(cc: CreditCard, n: Int, p: Payments): List[Coffee] = (0 to n).map(_ => buyCoffee(cc, p)).toList
    }

    class Payments {
      def charge(cc: CreditCard, price: Double): Unit = ??? //side effects calls credit card company
    }
  }

  object Functional {

    case class Charge(cc: CreditCard, amount: Double) {
      def combine(other: Charge): Charge =
        if (cc == other.cc)
          Charge(cc, amount + other.amount)
        else
          throw new Exception("Can't combine charges to different cards")
    }

    class Cafe {
      def buyCoffee(cc: CreditCard): (Coffee, Charge) = {
        val cup = new Coffee()
        (cup, Charge(cc, cup.price))
      }

      def buyCoffees(cc: CreditCard, n: Int): (List[Coffee], Charge) = {
        val purchases: List[(Coffee, Charge)] = List.fill(n)(buyCoffee(cc))
        val (coffees, charges) = purchases.unzip
        (coffees, charges.reduce((c1,c2) => c1.combine(c2)))
      }
    }

    class Payments {
      def charge(cc: CreditCard, price: Double): Unit = ??? //side effects calls credit card company

      def charge(charges: List[Charge]): Unit =
        groupByCC(charges).foreach(c => charge(c.cc, c.amount))

      def groupByCC(charges: List[Charge]) =
        charges.groupBy(_.cc).values.map(_.reduce(_ combine _)).toList
    }
  }

}
