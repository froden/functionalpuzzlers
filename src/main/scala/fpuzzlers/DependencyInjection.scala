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

  class InnboksService(kontoDao: KontoDao, brevDao: BrevDao) {

    def hentInnboks(brukerId: Int): List[Brev] = {
      val bruker = kontoDao.hentBruker(brukerId)
      brevDao.hentAlleBrev(bruker)
    }
  }

  object Innboks {

    def hentInnboks(kontoDao: KontoDao, brevDao: BrevDao)(brukerId: Int): List[Brev] = {
      val bruker = kontoDao.hentBruker(brukerId)
      brevDao.hentAlleBrev(bruker)
    }

    def hentInnboks2(brukerId: Int)(implicit kontoDao: KontoDao, brevDao: BrevDao): List[Brev] = {
      val bruker = kontoDao.hentBruker(brukerId)
      brevDao.hentAlleBrev(bruker)
    }
  }

}
