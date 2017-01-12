package compliant

import scala.util.matching.Regex

object SemanticVersion {

  val re: Regex = """(\d+)\.(\d+)\.(\d+)(-([^+]+))?(\+(.+))?""".r

}

class SemanticVersion(val versionString: String) {

  val fullVersionString: String = versionString.trim.replaceAll("\\s", "")

  val (majorVersion, minorVersion, patchVersion, prerelease, buildMetadata) = {
    fullVersionString match {
      case SemanticVersion.re(major, minor, patch, _, pre, _, meta) =>
        (major, minor, patch, Option(pre).getOrElse(""), Option(meta).getOrElse(""))
      case _ =>
        throw new IllegalArgumentException(versionString)
    }
  }

  def versionStringWithoutBuildMetadata : String = {
    this.fullVersionString.split("\\+").head
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
    if (this == otherVersion) {
      true
    } else if (this > otherVersion) {
      false
    } else {
      true
    }
  }

  def <(otherVersion: SemanticVersion) : Boolean = {
    val notGreaterThan = !(this > otherVersion)
    val notEqualTo = !(this == otherVersion)
    notGreaterThan && notEqualTo
  }
}
