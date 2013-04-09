package com.github.theon.urlutils

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import com.github.theon.uri.Uri._

class ParsingTests extends FlatSpec with ShouldMatchers {
  "Parsing an absolute URI" should "result in a valid Uri object" in {
    val uri = parseUri("http://theon.github.com/uris-in-scala.html")
    uri.protocol should equal (Some("http"))
    uri.host should equal (Some("theon.github.com"))
    uri.path should equal ("/uris-in-scala.html")
  }

  "Parsing a relative URI" should "result in a valid Uri object" in {
    val uri = parseUri("/uris-in-scala.html")
    uri.protocol should equal (None)
    uri.host should equal (None)
    uri.path should equal ("/uris-in-scala.html")
  }

  "Parsing a URI with querystring paramteres" should "result in a valid Uri object" in {
    val uri = parseUri("/uris-in-scala.html?query_param_one=hello&query_param_one=goodbye&query_param_two=false")
    uri.query.params should equal (
      Map(
        ("query_param_two" -> List("false")),
        ("query_param_one" -> List("hello", "goodbye"))
      )
    )
  }

  "Parsing a url with relative protocol" should "result in a Uri with None for protocol" in {
    val uri = parseUri("//theon.github.com/uris-in-scala.html")
    uri.protocol should equal (None)
    uri.toString should equal ("//theon.github.com/uris-in-scala.html")
  }

  "Parsing URIs" should "be idempotent" in {
    val source = com.github.theon.uri.Uri(
      Some("http"),
      Some("xn--ls8h.example.net"),
      None,
      List("", "path with spaces"),
      com.github.theon.uri.Querystring(Map("a b" → List("c d")))
    )
    val parsed = parseUri(source.toString)
    parsed should equal(source)
  }
}
