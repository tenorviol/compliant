package compliant

class SemanticVersion(val versionString: String) {
  // Perhaps replace some of this with proper RegExs?
  // I'm not sure which is more performant
  val trimmedVersionString = versionString.trim.replaceAll("\\s", "")
  val splitByPeriods = trimmedVersionString.split("\\.")
  val major = splitByPeriods(0)
  val minor = splitByPeriods(1)
  val patch = splitByPeriods.slice(2,splitByPeriods.length).mkString(".").split("\\+|\\-")(0)

  // standard attributes
  def fullVersionString: String = {
    this.trimmedVersionString
  }

  def majorVersion : String = {
    this.major
  }

  //
  def minorVersion : String = {
    this.minor
  }

  def patchVersion : String = {
    this.patch
  }

  // slightly less straightforward attributes
  def versionStringWithoutBuildMetadata : String = {
    this.trimmedVersionString.split("\\+").head
  }

  def prerelease : String = {
    val splitByDashes = this.trimmedVersionString.split("\\-")
    splitByDashes.slice(1, splitByDashes.length).mkString("-").split("\\+").head
  }

  //
  def buildMetadata : String = {
    val splitByPlus = this.trimmedVersionString.split("\\+")
    // join with plus to recreate original string
    splitByPlus.slice(1,splitByPlus.length).mkString("+")
  }

  // comparators
  def ==(otherVersionString: String) : Boolean = {
    val otherVersion = new SemanticVersion(otherVersionString)
    this == otherVersion
  }

  def ==(otherVersion: SemanticVersion) : Boolean = {
    this.versionStringWithoutBuildMetadata == otherVersion.versionStringWithoutBuildMetadata
  }

  //
  def >=(otherVersion: SemanticVersion) : Boolean = {
    val greaterThan = this > otherVersion
    val equalTo = this == otherVersion
    greaterThan || equalTo
  }

  // The heart of comparisons is based off of this
  // Suggestions for improvement welcome
  def >(otherVersion: SemanticVersion) : Boolean = {
    if (this.majorVersion > otherVersion.majorVersion) {
      true
    } else if (this.majorVersion == otherVersion.majorVersion) {
      if (this.minorVersion > otherVersion.minorVersion) {
        true
      } else if (this.minorVersion == otherVersion.minorVersion) {
        if (this.patchVersion > otherVersion.patchVersion) {
          true
        } else if (this.patchVersion == otherVersion.patchVersion && this.prerelease > otherVersion.prerelease) {
          true
        } else {
          false // major, minor, patch version are same, prerelease is less
        }
      } else {
        false // major version is same, and minor version is less
      }
    } else {
      false // major version is less
    }
  }

  //
  def <=(otherVersion: SemanticVersion) : Boolean = {
    if (this == otherVersion) {
      true
    } else if (this > otherVersion) {
      false
    } else {
      true
    }
  }

  //
  def <(otherVersion: SemanticVersion) : Boolean = {
    val notGreaterThan = !(this > otherVersion)
    val notEqualTo = !(this == otherVersion)
    notGreaterThan && notEqualTo
  }

  // TODO: Add a validation method?
}
