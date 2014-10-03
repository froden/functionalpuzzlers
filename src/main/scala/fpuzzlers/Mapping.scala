package fpuzzlers

import scala.collection.mutable

object Mapping {

  case class Message(id: String)

  case class Receipt(messageId: String)


  def imperativeMapReceiptsToMessages(messages: List[Message], receipts: List[Receipt]): List[(Message, Option[Receipt])] = {

    val mappedReceipts = new mutable.HashMap[String, Receipt]()
    for (receipt <- receipts) {
      mappedReceipts.put(receipt.messageId, receipt)
    }

    val result = new mutable.ListBuffer[(Message, Option[Receipt])]
    for (message <- messages) {
      result += ((message, mappedReceipts.get(message.id)))
      //      result += ((message, receipts.find(r => r.messageId == message.id)))
    }

    result.toList
  }


  def functionalMapReceiptsToMessages(messages: List[Message], receipts: List[Receipt]): List[(Message, Option[Receipt])] = {
    val mappedReceipts = receipts.map(r => (r.messageId, r)).toMap
    messages.map(m => (m, mappedReceipts.get(m.id)))
//    messages.map(m => (m, receipts.find(r => r.messageId == m.id)))
  }
}
