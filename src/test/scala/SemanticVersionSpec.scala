import semanticversion.SemanticVersion

import org.scalatest._
import org.scalatest.FlatSpec

class SemanticVersionSpec extends FlatSpec {
  "A SemanticVersion" should "contain a major version" in {
    val version = new SemanticVersion("1.2.3-alpha+meta")
    assert(version.major == "1")
  }
}
