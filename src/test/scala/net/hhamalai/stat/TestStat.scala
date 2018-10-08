package net.hhamalai.stat

import org.scalatest.{FlatSpec, Matchers}

class TestStat extends FlatSpec with Matchers {
  "A" should "" in {
    Stat.time {
      println("foo")
    }
    Stat.time {
      println("bar")
    }
    Stat.time {
      Thread.sleep(100)
      println("bz")
    }
    Stat.print(System.out)
  }
}