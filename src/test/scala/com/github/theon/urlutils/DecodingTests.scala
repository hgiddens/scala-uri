package com.github.theon.urlutils

import com.github.theon.uri.Encoders.{ PercentEncoder, encode }
import com.github.theon.uri.PercentDecoder
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import scala.util.control.Exception.catching

class PercentDecoderTests extends FlatSpec with ShouldMatchers with GeneratorDrivenPropertyChecks {
  import PercentDecoder.decode

  "Decoding" should "handle strings with a trailing percent" in {
    forAll { (s: String) ⇒
      decode(s + "%") should be (None)
    }
  }

  it should "handle strings with an incomplete escape" in {
    forAll { (s: String, c: Char) ⇒
      decode(s + "%" + c) should be (None)
    }
  }

  it should "handle strings with a complete but invalid escape" in {
    forAll { (h: Char, l: Char) ⇒
      val s = h.toString + l
      decode("%" + s) should be (catching(classOf[NumberFormatException]) opt Integer.parseInt(s, 16))
    }
  }

  it should "be the inverse of encoding" in {
    forAll { (s: String) ⇒
      decode(encode(s, PercentEncoder)) should be (Some(s))
    }
  }
}
