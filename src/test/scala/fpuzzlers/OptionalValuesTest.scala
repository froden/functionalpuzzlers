package fpuzzlers

import fpuzzlers.OptionalValues._
import org.scalatest.{MustMatchers, FunSuite}


class OptionalValuesTest extends FunSuite with MustMatchers {

  val bankWithNormalData =
    Bank(List(
      Account(5000L, Person("Lars")),
      Account(200L, Person("Jack")),
      Account(1000000L, Person("Richie")),
      Account(150000L, Person("Alisha")),
      Account(20000L, Person("Nina")))
    )

  val bankWithoutNameOfWealthiestPerson =
    Bank(List(
      Account(5000L, Person("Lars")),
      Account(200L, Person("Jack")),
      Account(1000000L, Person(null)),
      Account(150000L, Person("Alisha")),
      Account(20000L, Person("Nina")))
    )

  val bankWithoutNullAccountList = Bank(null)

  val bankWithoutEmptyAccountList = Bank(List())

  val bankWithNullAccounts =
    Bank(List(
      null,
      Account(200L, Person("Jack")),
      Account(1000000L, Person("Richie")),
      null,
      Account(20000L, Person("Nina")))
    )

  val bankWithNullWealthiestPerson = Bank(List(
    Account(5000L, Person("Lars")),
    Account(200L, Person("Jack")),
    Account(1000000L, null),
    Account(150000L, Person("Alisha")),
    Account(20000L, Person("Nina")))
  )

  test("imperative get must return '' when bank is null") {
    val name = getNameOfWealthiestPersonImperative(null)
    name must be("")
  }

  test("functional get must return '' when bank is null") {
    val name = getNameOfWealthiestPersonFunctional(null)
    name must be("")
  }

  test("imperative get must return name of wealthiest person") {
    val name = getNameOfWealthiestPersonImperative(bankWithNormalData)
    name must be("Richie")
  }

  test("functional get must return name of wealthiest person") {
    val name = getNameOfWealthiestPersonFunctional(bankWithNormalData)
    name must be("Richie")
  }

  test("imperative get must return 'No name' if riches person has no name") {
    val name = getNameOfWealthiestPersonImperative(bankWithoutNameOfWealthiestPerson)
    name must be("No name")
  }

  test("functional get must return 'No name' if riches person has no name") {
    val name = getNameOfWealthiestPersonFunctional(bankWithoutNameOfWealthiestPerson)
    name must be("No name")
  }

  test("imperative get must return '' if accounts is null") {
    val name = getNameOfWealthiestPersonImperative(bankWithoutNullAccountList)
    name must be("")
  }

  test("functional get must return '' if accounts is null") {
    val name = getNameOfWealthiestPersonFunctional(bankWithoutNullAccountList)
    name must be("")
  }

  test("imperative get must return '' if account list is empty") {
    val name = getNameOfWealthiestPersonImperative(bankWithoutEmptyAccountList)
    name must be("")
  }

  test("functional get must return '' if account list is empty") {
    val name = getNameOfWealthiestPersonFunctional(bankWithoutEmptyAccountList)
    name must be("")
  }

  test("imperative get must return name of wealthiest person even if account list includes null accounts") {
    val name = getNameOfWealthiestPersonImperative(bankWithNullAccounts)
    name must be("Richie")
  }

  test("functional get must return name of wealthiest person even if account list includes null accounts") {
    val name = getNameOfWealthiestPersonFunctional(bankWithNullAccounts)
    name must be("Richie")
  }

  test("imperative get must return '' if riches person is null") {
    val name = getNameOfWealthiestPersonImperative(bankWithNullWealthiestPerson)
    name must be("")
  }

  test("functional get must return '' if riches person is null") {
    val name = getNameOfWealthiestPersonFunctional(bankWithNullWealthiestPerson)
    name must be("")
  }

}
