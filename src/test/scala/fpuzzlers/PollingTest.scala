package fpuzzlers

import fpuzzlers.Polling.JmxService

class PollingTest {

  def test() {
    val values = List(1, 4, 6, 8, 1, 4)

    val service = new JmxService {
      var left = values
      override def pollCpu(): Long = {
        val head = left.head
        left = left.tail
        head
      }
    }
  }
}
