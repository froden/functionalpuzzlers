package fpuzzlers

import scala.collection.mutable

object Mapping {

  case class Message(id: String)

  case class Receipt(messageId: String)


  /**
   *  Et system sender meldinger og får deretter asynkrone kvitteringer på at meldingene er levert.
   *  Etter en batch-utsending sitter man med en liste av sendte meldinger og kvitteringer.
   *  Oppgaven går ut på å matche sendte meldinger med tilhørende kvittering.
   *  Det er snakke om ganske mange meldinger og kvitteringer.
   *
   *  Dette eksemplet viser en typisk imperativ løsning på problemet.
   */
  def imperativeMapReceiptsToMessages(messages: List[Message], receipts: List[Receipt]): List[(Message, Option[Receipt])] = {

    val mappedReceipts = new mutable.HashMap[String, Receipt]()
    for (receipt <- receipts) {
      mappedReceipts.put(receipt.messageId, receipt)
    }

    val result = new mutable.ListBuffer[(Message, Option[Receipt])]
    for (message <- messages) {
      result += ((message, mappedReceipts.get(message.id)))
    }

    result.toList
  }

  /**
   *  Et system sender meldinger og får deretter asynkrone kvitteringer på at meldingene er levert.
   *  Etter en batch-utsending sitter man med en liste av sendte meldinger og kvitteringer.
   *  Oppgaven går ut på å matche sendte meldinger med tilhørende kvittering.
   *  Det er snakke om ganske mange meldinger og kvitteringer.
   *
   *  Løs oppgaven på en funksjonell måte. Dvs:
   *  * Ingen var
   *  * Ingen while/for-each
   *  * Ingen side-effects/mutable state
   */
  def functionalMapReceiptsToMessages(messages: List[Message], receipts: List[Receipt]): List[(Message, Option[Receipt])] = ??? //TODO: implementer denne
}
