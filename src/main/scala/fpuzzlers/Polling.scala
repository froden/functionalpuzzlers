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

    private def pollJmxCpu(jmxService: JmxService): Stream[Long] =
      Try(jmxService.pollCpu()) match {
        case Success(l) => l #:: pollJmxCpu(jmxService)
        case Failure(e) => pollJmxCpu(jmxService)
      }

    def getJmxCpuStats(jmxService: JmxService, running: () => Boolean): Future[List[Long]] = Future {
      pollJmxCpu(jmxService)
        .takeWhile(_ => running())
        .map(l => {Thread.sleep(500); l})
        .toList
    }
  }

}