package fpuzzlers

/*
 * In this exercise you will refactor a lot of nested if-statements to a more functional approach.
 */
object OptionalValues {

  case class PersonLegacy(name: String)
  case class AccountLegacy(balance: Long, owner: PersonLegacy)
  case class BankLegacy(accounts: List[AccountLegacy])


  def getNameOfWealthiestPersonImperative(bank: BankLegacy): String = {
    if(bank != null) {
      if (bank.accounts != null) {
        var accountWithGreatestBalance: AccountLegacy = null
        for (account <- bank.accounts) {
          if (accountWithGreatestBalance == null || accountWithGreatestBalance.balance < account.balance) {
            accountWithGreatestBalance = account
          }
        }

        if (accountWithGreatestBalance != null) {
          val owner = accountWithGreatestBalance.owner
          if (owner != null) {
            if(owner.name != null) {
              return owner.name
            } else {
              return "No name"
            }
          }
        }
      }
    }

    ""
  }

  /*
   * Refactor getNameOfWealthiestPersonImperative in a functional manner.
   * You are not allowed to change the messy legacy data structure that can and will contain null values.
   * You are not allowed to use mutable state (e.g. var), or loops of any kind (e.g. while, for).
   * Try to avoid the use of if-statements and explicit null checks
   * (Hint: For-Comprehension and Option is your friend)
   */
  def getNameOfWealthiestPersonFunctionalLegacy(bank: BankLegacy): String = ??? //TODO: implement this


  case class Person(name: Option[String])
  case class Account(balance: Long, owner: Option[Person])
  case class Bank(accounts: Option[List[Account]])

  /*
   * Refactor getNameOfWealthiestPersonFunctionalLegacy with a more functional data structure.
   * You are not allowed to use mutable state (e.g. var), or loops of any kind (e.g. while, for).
   * Try to avoid the use of if-statements and explicit null checks
   * (Hint: For-Comprehension and Option is your friend)
   */
  def getNameOfWealthiestPersonFunctional(bank: Option[Bank]): String = ??? //TODO: implement this


}
