package fpuzzlers

object DependencyInjection {
  class Bruker
  class Brev

  trait KontoDao {
    def hentBruker(i: Int): Bruker
  }

  trait BrevDao {
    def hentAlleBrev(bruker: Bruker): List[Brev]
  }

  /**
   * Klassis OO dependecy injection der vi gjennom konstruktøren
   * sender med alle avhengigheter og grupperer funksjoner som trenger
   * disse avhengighetene i Service-klaser
   */
  class InnboksService(kontoDao: KontoDao, brevDao: BrevDao) {

    /**
     * I en meldingstjeneste brukes denne metoden til å hente alle brevene
     * i postkassen til en bruker.
     */
    def hentInnboks(brukerId: Int): List[Brev] = {
      val bruker = kontoDao.hentBruker(brukerId)
      brevDao.hentAlleBrev(bruker)
    }
  }

  /**
   * I en funksjonell variant av meldingstjenesten er vi ikke avhengig av å gruppere
   * funksjoner i servicer bare fordi de har omtrent de samme avhengighetene.
   * Det holder å ha en samling funksjoner i et objekt (scala) eller samling statiske
   * metoder (java). Dette gjør det enklere å refactore og man unngår Service-klasser
   * som inneholder metoder som kun bruker et subset av avhengighetene.
   */
  object Innboks {

    /**
     * Det enkleste tilfellet er å ta inn alle avhengigheter som en egen parameterliste
     * først i funksjonene. Når applikasjonen "wires opp" kan man bruke partial application
     * og binder alle avhengighetene til funksjonen og få en ny funksjon som kun forventer
     * brukerId som parameter.
     *
     * @see fpuzzlers.DependencyInjectionTest
     */
    def hentInnboks(kontoDao: KontoDao, brevDao: BrevDao)(brukerId: Int): List[Brev] = {
      val bruker = kontoDao.hentBruker(brukerId)
      brevDao.hentAlleBrev(bruker)
    }

    /**
     * I dette eksemplet sendes avhengigheten som en egen implicit parameterliste til slutt.
     * Dette gjør det mulig for kompilatoren å sende med avhengighetene automatisk så lenge
     * det finnes en implicit instans i scop. Dermed kan man importere avhengigheter for produksjon
     * i produksjonskoden, men man står fritt til å sende med stubs/mocks i tester.
     *
     * @see fpuzzlers.DependencyInjectionTest
     */
    def hentInnboks2(brukerId: Int)(implicit kontoDao: KontoDao, brevDao: BrevDao): List[Brev] = {
      val bruker = kontoDao.hentBruker(brukerId)
      brevDao.hentAlleBrev(bruker)
    }
  }

}
