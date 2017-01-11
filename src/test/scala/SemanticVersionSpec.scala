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
      // equality
      "give numeric identifiers lower precedence" in {
        val numericIdentiferVersion = new SemanticVersion("1.1.0-20160605")
        val nonNumericIdentifierVersion = new SemanticVersion("1.1.0-alpha")
        val resultOne = numericIdentiferVersion < nonNumericIdentifierVersion
        assert(resultOne == true)

        val resultTwo = nonNumericIdentifierVersion < numericIdentiferVersion
        assert(resultTwo == false)
      }

      // greater than
      "properly determine greater than for simple versions" in {
        val lesserVersion = new SemanticVersion("1.0.0")
        val greaterVersion = new SemanticVersion("2.0.0")
        val resultOne = greaterVersion > lesserVersion
        assert(resultOne == true)

        val resultTwo = lesserVersion > greaterVersion
        assert(resultTwo == false)
      }

      "properly determine greater than for simple versions with metadata" in {
        val lesserVersion = new SemanticVersion("1.0.0+meta1")
        val greaterVersion = new SemanticVersion("1.0.0+meta2")
        val resultOne = greaterVersion > lesserVersion
        assert(resultOne == false)

        val resultTwo = lesserVersion > greaterVersion
        assert(resultTwo == false)
      }

      "properly determine greater than for prerelease versions" in {
        val lesserVersion = new SemanticVersion("1.0.0-alpha")
        val greaterVersion = new SemanticVersion("1.0.0-beta")
        val resultOne = greaterVersion > lesserVersion
        assert(resultOne == true)

        val resultTwo = lesserVersion > greaterVersion
        assert(resultTwo == false)
      }

      "property determine greater than for prerelease versions with dates" in {
        val lesserVersion = new SemanticVersion("1.0.0-20170109")
        val greaterVersion = new SemanticVersion("1.0.0-20170110")
        val resultOne = greaterVersion > lesserVersion
        assert(resultOne == true)

        val resultTwo = lesserVersion > greaterVersion
        assert(resultTwo == false)
      }

      // Less than
      "properly determine less than for simple versions" in {
        val lesserVersion = new SemanticVersion("1.0.0")
        val greaterVersion = new SemanticVersion("2.0.0")
        val resultOne = lesserVersion < greaterVersion
        assert(resultOne == true)

        val resultTwo = greaterVersion < lesserVersion
        assert(resultTwo == false)
      }

      "properly determine less than for simple versions with metadata" in {
        val lesserVersion = new SemanticVersion("1.0.0+meta1")
        val greaterVersion = new SemanticVersion("1.0.0+meta2")
        val resultOne = lesserVersion < greaterVersion
        assert(resultOne == false)

        val resultTwo = greaterVersion < lesserVersion
        assert(resultTwo == false)
      }

      "properly determine less than for prerelease versions" in {
        val lesserVersion = new SemanticVersion("1.0.0-alpha")
        val greaterVersion = new SemanticVersion("1.0.0-beta")
        val resultOne = lesserVersion < greaterVersion
        assert(resultOne == true)

        val resultTwo = greaterVersion < lesserVersion
        assert(resultTwo == false)
      }
    }
  }
}
