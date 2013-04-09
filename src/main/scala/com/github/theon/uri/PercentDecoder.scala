package com.github.theon.uri

import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer

object PercentDecoder {
  def decode(string: String): Option[String] = {
    object hex {
      def unapply(c: Char): Option[Char] =
        if (c >= '0' && c <= '9' || c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F') Some(c) else None
    }
    @tailrec
    def impl(buffer: ArrayBuffer[Byte], string: List[Char]): Option[ArrayBuffer[Byte]] =
      string match {
        case Nil ⇒ Some(buffer)
        case '%' :: hex(h) :: hex(l) :: rest ⇒
          impl(buffer :+ Integer.parseInt(h.toString + l.toString, 16).toByte, rest)
        case '%' :: _ ⇒ None
        case x :: rest ⇒ impl(buffer :+ x.toByte, rest)
      }
    impl(ArrayBuffer(), string.toList).map(buffer ⇒ new String(buffer.toArray, "UTF-8"))
  }
}
