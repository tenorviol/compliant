package semanticversion

import semanticversion._

class SemanticVersion(val versionString: String) {
  val trimmedVersionString = versionString.trim.replaceAll("\\s", "")
  // val major = "ma" // reg ex: ^([^.]*).*
  val splitByPeriods = trimmedVersionString.split("\\.")
  // val major = "^([^.]*)".r.findFirstIn(trimmedVersionString).getOrElse("")
  val major = splitByPeriods(0)
  val minor = splitByPeriods(1)
  val patch = splitByPeriods.slice(2,splitByPeriods.length).mkString(".").split("\\+|\\-")(0)

  def fullVersionString: String = {
    this.trimmedVersionString
  }

  def majorVersion : String = {
    this.major
  }

  def minorVersion : String = {
    this.minor
  }

  def patchVersion : String = {
    this.patch
  }

  def versionStringWithoutBuildMetadata : String = {
    this.trimmedVersionString.split("\\+").head
  }

  def prerelease : String = {
    val splitByDashes = this.trimmedVersionString.split("\\-")
    splitByDashes.slice(1, splitByDashes.length).mkString("-").split("\\+").head
  }

  def buildMetadata : String = {
    val splitByPlus = this.trimmedVersionString.split("\\+")
    // join with plus to recreate original string
    splitByPlus.slice(1,splitByPlus.length).mkString("+")
  }

  def ==(otherVersionString: String) : Boolean = {
    val otherVersion = new SemanticVersion(otherVersionString)
    this == otherVersion
  }

  def ==(otherVersion: SemanticVersion) : Boolean = {
    this.versionStringWithoutBuildMetadata == otherVersion.versionStringWithoutBuildMetadata
  }

  def >=(otherVersion: SemanticVersion) : Boolean = {
    val greaterThan = this > otherVersion
    val equalTo = this == otherVersion
    greaterThan || equalTo
  }

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

  def <=(otherVersion: SemanticVersion) : Boolean = {
    // TODO: Implement this
    throw new NotYetImplementedException()
  }

  def <(otherVersion: SemanticVersion) : Boolean = {
    // TODO: Implement this
    throw new NotYetImplementedException()
  }
}
