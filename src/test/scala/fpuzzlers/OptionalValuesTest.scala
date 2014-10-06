package fpuzzlers

import fpuzzlers.OptionalValues._
import org.scalatest.{MustMatchers, FunSuite}


class OptionalValuesTest extends FunSuite with MustMatchers {

  val bankWithNormalDataLegacy =
    BankLegacy(List(
      AccountLegacy(5000L, PersonLegacy("Lars")),
      AccountLegacy(200L, PersonLegacy("Jack")),
      AccountLegacy(1000000L, PersonLegacy("Richie")),
      AccountLegacy(150000L, PersonLegacy("Alisha")),
      AccountLegacy(20000L, PersonLegacy("Nina")))
    )

  val bankWithoutNameOfWealthiestPersonLegacy =
    BankLegacy(List(
      AccountLegacy(5000L, PersonLegacy("Lars")),
      AccountLegacy(200L, PersonLegacy("Jack")),
      AccountLegacy(1000000L, PersonLegacy(null)),
      AccountLegacy(150000L, PersonLegacy("Alisha")),
      AccountLegacy(20000L, PersonLegacy("Nina")))
    )

  val bankWithoutNullAccountListLegacy = BankLegacy(null)

  val bankWithoutEmptyAccountListLegacy = BankLegacy(List())

  val bankWithNullWealthiestPersonLegacy = BankLegacy(List(
    AccountLegacy(5000L, PersonLegacy("Lars")),
    AccountLegacy(200L, PersonLegacy("Jack")),
    AccountLegacy(1000000L, null),
    AccountLegacy(150000L, PersonLegacy("Alisha")),
    AccountLegacy(20000L, PersonLegacy("Nina")))
  )

  test("getNameOfWealthiestPersonImperative must return '' when bank is null") {
    val name = getNameOfWealthiestPersonImperative(null)
    name must be("")
  }

  test("getNameOfWealthiestPersonImperative must return name of wealthiest person") {
    val name = getNameOfWealthiestPersonImperative(bankWithNormalDataLegacy)
    name must be("Richie")
  }

  test("getNameOfWealthiestPersonImperative must return 'No name' if riches person has no name") {
    val name = getNameOfWealthiestPersonImperative(bankWithoutNameOfWealthiestPersonLegacy)
    name must be("No name")
  }

  test("getNameOfWealthiestPersonImperative must return '' if accounts is null") {
    val name = getNameOfWealthiestPersonImperative(bankWithoutNullAccountListLegacy)
    name must be("")
  }

  test("getNameOfWealthiestPersonImperative must return '' if account list is empty") {
    val name = getNameOfWealthiestPersonImperative(bankWithoutEmptyAccountListLegacy)
    name must be("")
  }
  
  test("getNameOfWealthiestPersonImperative must return '' if riches person is null") {
    val name = getNameOfWealthiestPersonImperative(bankWithNullWealthiestPersonLegacy)
    name must be("")
  }

  test("getNameOfWealthiestPersonFunctionalLegacy must return '' when bank is null") {
    val name = getNameOfWealthiestPersonFunctionalLegacy(null)
    name must be("")
  }

  test("getNameOfWealthiestPersonFunctionalLegacy must return name of wealthiest person") {
    val name = getNameOfWealthiestPersonFunctionalLegacy(bankWithNormalDataLegacy)
    name must be("Richie")
  }

  test("getNameOfWealthiestPersonFunctionalLegacy must return 'No name' if riches person has no name") {
    val name = getNameOfWealthiestPersonFunctionalLegacy(bankWithoutNameOfWealthiestPersonLegacy)
    name must be("No name")
  }

  test("getNameOfWealthiestPersonFunctionalLegacy must return '' if accounts is null") {
    val name = getNameOfWealthiestPersonFunctionalLegacy(bankWithoutNullAccountListLegacy)
    name must be("")
  }

  test("getNameOfWealthiestPersonFunctionalLegacy must return '' if account list is empty") {
    val name = getNameOfWealthiestPersonFunctionalLegacy(bankWithoutEmptyAccountListLegacy)
    name must be("")
  }

  test("getNameOfWealthiestPersonFunctionalLegacy must return '' if riches person is null") {
    val name = getNameOfWealthiestPersonFunctionalLegacy(bankWithNullWealthiestPersonLegacy)
    name must be("")
  }

  val bankWithNormalData =
    Bank(Some(List(
      Account(5000L, Some(Person(Some("Lars")))),
      Account(200L, Some(Person(Some("Jack")))),
      Account(1000000L, Some(Person(Some("Richie")))),
      Account(150000L, Some(Person(Some("Alisha")))),
      Account(20000L, Some(Person(Some("Nina"))))
    )))

  val bankWithoutNameOfWealthiestPerson =
    Bank(Some(List(
      Account(5000L, Some(Person(Some("Lars")))),
      Account(200L, Some(Person(Some("Jack")))),
      Account(1000000L, Some(Person(None))),
      Account(150000L, Some(Person(Some("Alisha")))),
      Account(20000L, Some(Person(Some("Nina"))))
    )))

  val bankWithoutNullAccountList = Bank(None)

  val bankWithoutEmptyAccountList = Bank(Some(List()))

  val bankWithNullWealthiestPerson =
    Bank(Some(List(
      Account(5000L, Some(Person(Some("Lars")))),
      Account(200L, Some(Person(Some("Jack")))),
      Account(1000000L, None),
      Account(150000L, Some(Person(Some("Alisha")))),
      Account(20000L, Some(Person(Some("Nina"))))
    )))

  test("getNameOfWealthiestPersonFunctional must return '' when bank is null") {
    val name = getNameOfWealthiestPersonFunctional(None)
    name must be("")
  }

  test("getNameOfWealthiestPersonFunctional must return name of wealthiest person") {
    val name = getNameOfWealthiestPersonFunctional(Some(bankWithNormalData))
    name must be("Richie")
  }

  test("getNameOfWealthiestPersonFunctional must return 'No name' if riches person has no name") {
    val name = getNameOfWealthiestPersonFunctional(Some(bankWithoutNameOfWealthiestPerson))
    name must be("No name")
  }

  test("getNameOfWealthiestPersonFunctional must return '' if accounts is null") {
    val name = getNameOfWealthiestPersonFunctional(Some(bankWithoutNullAccountList))
    name must be("")
  }

  test("getNameOfWealthiestPersonFunctional must return '' if account list is empty") {
    val name = getNameOfWealthiestPersonFunctional(Some(bankWithoutEmptyAccountList))
    name must be("")
  }

  test("getNameOfWealthiestPersonFunctional must return '' if riches person is null") {
    val name = getNameOfWealthiestPersonFunctional(Some(bankWithNullWealthiestPerson))
    name must be("")
  }

}
