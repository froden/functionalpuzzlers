package fpuzzlers

object SideEffects {

  class CreditCard

  class Coffee {
    def price = 1.50
  }

  object Imperative {

    class Cafe {
      /**
       * Problemet her er at byCoffee både gjør en side-effect og returnerer en kaffe.
       * Den blander det å opprette kaffe og beregne pris med det å belaste kortet.
       */
      def buyCoffee(cc: CreditCard, p: Payments): Coffee = {
        val cup = new Coffee()
        p.charge(cc, cup.price)
        cup
      }

      /**
       * Selv om samme kunde kjøper 3 kaffe vil hun bli belastet 3 ganger på kortet
       */
      def buyCoffees(cc: CreditCard, n: Int, p: Payments): List[Coffee] = (0 to n).map(_ => buyCoffee(cc, p)).toList
    }

    /**
     * Betalingstjenesten
     */
    class Payments {
      def charge(cc: CreditCard, price: Double): Unit = ??? //side effects, calls credit card company
    }
  }

  object Functional {

    /**
     * Representerer et beløp som skal belastes et kredittkort
     */
    case class Charge(cc: CreditCard, amount: Double) {
      /**
       * Slår samme to Charges til en slik at et kredittkort kun belastes 1 gang.
       * Dette er kun mulig hvis begge chargene er for samme kort.
       */
      def combine(other: Charge): Charge =
        if (cc == other.cc)
          Charge(cc, amount + other.amount)
        else
          throw new Exception("Can't combine charges to different cards")
    }

    class Cafe {
      /**
       * buyCoffe skal opprette en Charge med kort og pris,
       * opprette en kaffe og returnere disse som et tuple.
       * Denne varianten er referential transparent og er enkel å gjenbruke.
       */
      def buyCoffee(cc: CreditCard): (Coffee, Charge) = {
        val cup = new Coffee()
        (cup, Charge(cc, cup.price))
      }

      /**
       * Hvis kunden kjøper mer enn 1 kaffe skal hun få en liste med kaffe,
       * men skal kun belastes 1 gang. Derfor 1 charge med totalbeløpet.
       */
      def buyCoffees(cc: CreditCard, n: Int): (List[Coffee], Charge) = {
        val purchases: List[(Coffee, Charge)] = List.fill(n)(buyCoffee(cc))
        val (coffees, charges) = purchases.unzip
        (coffees, charges.reduce((c1,c2) => c1.combine(c2)))
      }
    }

    class Payments {
      /**
       * Denne innbiller vi oss kaller den faktiske eksterne kredittkorttjenesten tjenesten så
       * vi skal ikke implementere denne.
       */
      def charge(cc: CreditCard, price: Double): Unit = ??? //utfører io mot kortselskap (side-effekt)

      /**
       * Kassaoppgjør: Belaster en liste med charger tilhørende ulike kunder
       */
      def charge(charges: List[Charge]): Unit =
        groupByCC(charges).foreach(c => charge(c.cc, c.amount))

      /**
       * Hvis en kunde har sittet i kafeen en stund og gjort forskjellig kjøp
       * hadde det vært fint å kunne belaste kunden kun 1 gang for alle kjøpene.
       * Denne funksjonen kombinerer Charge objekter med samme kredittkort.
       */
      def groupByCC(charges: List[Charge]) =
        charges.groupBy(_.cc).values.map(_.reduce(_ combine _)).toList
    }
  }

}
