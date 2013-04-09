package com.github.theon.uri

import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer

object PercentDecoder {
  def decode(string: String): String = {
    @tailrec
    def impl(buffer: ArrayBuffer[Byte], string: List[Char]): ArrayBuffer[Byte] =
      string match {
        case Nil ⇒ buffer
        case '%' :: h :: l :: rest ⇒
          impl(buffer :+ Integer.parseInt(s"$h$l", 16).toByte, rest)
        case x :: rest ⇒ impl(buffer :+ x.toByte, rest)
      }
    new String(impl(ArrayBuffer(), string.toList).toArray, "UTF-8")
  }
}
