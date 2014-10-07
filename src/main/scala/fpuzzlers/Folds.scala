package fpuzzlers

object Folds {

  case class Page(content: String)

  case class Document(pages: List[Page]) {
    def merge(doc: Document) = new Document(pages ::: doc.pages)
  }

  /**
   * Funksjonene kombinerer en liste med dokumenter til ett dokument
   * ved å lage et nytt dokument som inneholder alle sidene til de
   * opprinnelige dokumentene.
   */
  def combineAllImperative(documents: List[Document]): Document = {
    var resultDoc = new Document(Nil)
    for (doc <- documents) {
      resultDoc = resultDoc.merge(doc)
    }
    resultDoc
  }

  /**
   * Slå sammen alle dokumentene i listen på en funksjonell måte.
   */
  def combineAllFunctional(documents: List[Document]): Document = ??? //TODO: implementer denne
}
