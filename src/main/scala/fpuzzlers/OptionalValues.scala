package fpuzzlers

/*
 * In this exercise you will refactor a lot of nested if-statements to a more functional approach.
 */
object OptionalValues {

  case class Person(name: String)
  case class Account(balance: Long, owner: Person)
  case class Bank(accounts: List[Account])


  def getNameOfWealthiestPersonImperative(bank: Bank): String = {
    if(bank != null) {
      if (bank.accounts != null) {
        var accountWithGreatestBalance: Account = null
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
  def getNameOfWealthiestPersonFunctional(bank: Bank): String = {
    val name = for {
      bank <- Option(bank)
      accounts <- Option(bank.accounts)
      account <- accounts.sortBy(- _.balance).headOption
      owner <- Option(account.owner)
      name <- Option(owner.name).orElse(Some("No name"))
    } yield name

    name.getOrElse("")
  }


}
