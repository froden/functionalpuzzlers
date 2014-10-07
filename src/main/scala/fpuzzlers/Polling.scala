package fpuzzlers

import scala.collection.mutable
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

object Polling {

  trait JmxService {
    def pollCpu(): Long
  }

  object ImperativePolling {

    /**
     * Denne klassen kjøres som en egen tråd og poller en ekstern
     * JVM for å overvåke cpu-load på den eksterne serveren.
     * Den poller etter cpu-bruk via jmx med 500ms intervaller.
     * Når den får signal om å slutt returneres resultatene som en liste.
     */
    class JmxCpuPoller(jmxService: JmxService) extends Runnable {
      var running = true

      val results: mutable.MutableList[Long] = new mutable.MutableList[Long]

      override def run() {
        while (running) {
          try {
            results += jmxService.pollCpu()
            Thread.sleep(500)
          } catch {
            case e: Exception => ()
          }
        }
      }

      def stop() = running = false
    }

  }

  object FunctionalPolling {

    /**
     * Denne hjelpefunksjonen returner en uendelig strøm av cpu-resultateter fra en ekstern JVM
     */
    private def pollJmxCpu(jmxService: JmxService): Stream[Long] =
      Try(jmxService.pollCpu()) match {
        case Success(l) => l #:: pollJmxCpu(jmxService)
        case Failure(e) => pollJmxCpu(jmxService)
      }

    /**
     * Denne funksjonen brukes til å polle en ekstern JVM for cpu-bruk med 500ms intervaller.
     * Resultatet er en liste som returneres asynkront via en future.
     */
    def getJmxCpuStats(jmxService: JmxService, running: () => Boolean): Future[List[Long]] =
      Future {
        pollJmxCpu(jmxService)
          .takeWhile(_ => running())
          .map(l => {Thread.sleep(500); l})
          .toList
      }
  }
}