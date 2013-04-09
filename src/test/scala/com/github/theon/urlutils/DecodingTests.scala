package com.github.theon.urlutils

import com.github.theon.uri.Encoders.{ PercentEncoder, encode }
import com.github.theon.uri.PercentDecoder
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.prop.GeneratorDrivenPropertyChecks

class PercentDecoderTests extends FlatSpec with ShouldMatchers with GeneratorDrivenPropertyChecks {
  "Decoding" should "be the inverse of encoding" in {
    forAll { (string: String) ⇒
      PercentDecoder.decode(encode(string, PercentEncoder)) should equal (string)
    }
  }
}
