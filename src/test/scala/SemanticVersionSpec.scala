package test

import semanticversion.SemanticVersion

class SemanticVersionSpec extends SemanticVersionBaseSpec {
  "A SemanticVersion" when {
    "valid" should {
      "contain a major version" in {
        val version = new SemanticVersion("1.2.3-alpha+meta")
        assert(version.majorVersion == "1")
      }

      "contain a minor version" in {
        val version = new SemanticVersion("1.2.3-alpha+meta")
        assert(version.minorVersion == "2")
      }

      "contain a patch version" in {
        val version = new SemanticVersion("1.2.3-alpha+meta")
        assert(version.patchVersion == "3")
      }

      "contain a prerelease attribute" in {
        val version = new SemanticVersion("1.2.3-alpha+meta")
        assert(version.prerelease == "alpha")
      }

      "contain build metadata" in {
        val version = new SemanticVersion("1.2.3-alpha+meta")
        assert(version.buildMetadata == "meta")
      }
    }

    "evaluting equality of SemanticVersions" should {
      "return true for exactly equal versions" in {
        val firstVersion = new SemanticVersion("1.0.0-alpha+more")
        val secondVersion = new SemanticVersion("1.0.0-alpha+more")
        val result = firstVersion == secondVersion
        assert(result == true)
      }

      "return true for equal versions with different metadata" in {
        val firstVersion = new SemanticVersion("1.0.0-alpha+more")
        val secondVersion = new SemanticVersion("1.0.0-alpha+different")
        val result = firstVersion == secondVersion
        assert(result == true)
      }
    }

    "comparing versions" should {
      "give numeric identifiers lower precedence" in {
        val numericIdentiferVersion = new SemanticVersion("1.1.0-20160605")
        val nonNumericIdentifierVersion = new SemanticVersion("1.1.0-alpha")
        val result = numericIdentiferVersion < nonNumericIdentifierVersion
        assert(result == true)
      }
    }
  }
}
