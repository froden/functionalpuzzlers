package fpuzzlers

import fpuzzlers.Polling.ImperativePolling.JmxCpuPoller
import fpuzzlers.Polling.{FunctionalPolling, JmxService}
import org.scalatest.{FunSuite, MustMatchers}

import scala.concurrent.Await
import scala.concurrent.duration._

class PollingTest extends FunSuite with MustMatchers {

  def service = new JmxService {
    val values = List(1, 4, 6, 8, 1, 4).iterator
    override def pollCpu(): Long = values.next()
  }

  test("imperative polling should return 3 results") {
    val poller: JmxCpuPoller = new JmxCpuPoller(service)
    val pollerThread = new Thread(poller)
    pollerThread.start()
    Thread.sleep(1250)
    poller.stop()
    pollerThread.join()
    poller.results must be(List(1, 4, 6))
  }

  test("functional polling should return 3 results") {
    var running = true
    def isRunning = running
    val stats = FunctionalPolling.getJmxCpuStats(service, isRunning _)
    Thread.sleep(1250)
    running = false
    val result = Await.result(stats, 500 millis)
    result must be(List(1, 4, 6))
  }
}
