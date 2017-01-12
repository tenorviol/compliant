package compliant

import scala.util.matching.Regex

object SemanticVersion {

  val re: Regex = """(\d+)\.(\d+)\.(\d+)(-([^+]+))?(\+(.+))?""".r

}

class SemanticVersion(val versionString: String) extends Ordered[SemanticVersion] {

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

  def compare(that: SemanticVersion) = {
    if (this == that) {
      0
    } else if (this.majorVersion > that.majorVersion) {
      1
    } else if (this.majorVersion == that.majorVersion) {
      if (this.minorVersion > that.minorVersion) {
        1
      } else if (this.minorVersion == that.minorVersion) {
        if (this.patchVersion > that.patchVersion) {
          1
        } else if (this.patchVersion == that.patchVersion && this.prerelease > that.prerelease) {
          1
        } else {
          -1 // major, minor, patch version are same, prerelease is less
        }
      } else {
        -1 // major version is same, and minor version is less
      }
    } else {
      -1 // major version is less
    }
  }
}
