package net.hhamalai.stat

import java.io.PrintStream
import java.util.concurrent.TimeUnit


case class Measurement(time: Long, value: Float)

trait Stat {
  var stat: List[Measurement] = List.empty
  var min: Option[Measurement] = None
  var max: Option[Measurement] = None

  def add(x: Float) = {
    val measurement = Measurement(System.nanoTime, x)
    stat = stat :+ measurement
    if (min.isEmpty || min.get.value > x) {
      min = Some(measurement)
    }
    if (max.isEmpty || max.get.value < x) {
      max = Some(measurement)
    }
    stat = stat.filter(_.time > measurement.time - 3600 * 1000000000)
  }

  def time[A](f: => A): A = {
    val elapsed = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS)
    try {
      f
    } finally {
      add(TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS) - elapsed)
    }
  }

  def print(out: PrintStream) = {
    out.println(min, max, stat)
  }
}

object Stat extends Stat