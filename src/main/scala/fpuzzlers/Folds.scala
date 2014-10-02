package fpuzzlers

object Folds {

  case class Page(content: String)

  case class Document(pages: List[Page]) {
    def merge(doc: Document) = new Document(pages ::: doc.pages)
  }

  def combineAllImperative(documents: List[Document]) = {
    var resultDoc = new Document(Nil)
    for (doc <- documents) {
      resultDoc = resultDoc.merge(doc)
    }
    resultDoc
  }

  def combineAllFunctional(documents: List[Document]) =
    documents.foldLeft(new Document(Nil))(_ merge _)
}
