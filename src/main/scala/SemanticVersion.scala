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
    // TODO: Implement this
    throw new NotYetImplementedException()
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
    // TODO: Implement this
    throw new NotYetImplementedException()
  }

  def >(otherVersion: SemanticVersion) : Boolean = {
    // TODO: Implement this
    throw new NotYetImplementedException()
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
