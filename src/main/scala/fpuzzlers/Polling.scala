package fpuzzlers

import scala.collection.mutable.MutableList
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

object Polling {

  trait JmxService {
    def pollCpu(): Long
  }

  class JmxCpuPoller(jmxService: JmxService) extends Runnable {
    var running = true

    val results: MutableList[Long] = new MutableList[Long]

    override def run() {
      while(running) {
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


object Polling2 {

  trait JmxService {
    def poll(): Long
  }

  def pollJmxCpu(jmxService: JmxService): Stream[Long] =
    Try(jmxService.poll()) match {
      case Success(l) => l #:: pollJmxCpu(jmxService)
      case Failure(e) => pollJmxCpu(jmxService)
    }

  def getJmxCpuStats(jmxService: JmxService, running: () => Boolean): Future[List[Long]] = Future {
    pollJmxCpu(jmxService)
      .takeWhile(_ => running())
      .map(l => {Thread.sleep(500); l})
      .foldLeft(List[Long]()) ((acc, l) => l :: acc)
  }
}