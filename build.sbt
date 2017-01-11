lazy val root = (project in file(".")).
  settings(
    name := "SemanticVersion",
    version := "0.0.1-alpha+local",
    scalaVersion := "2.11.8",
    // libraryDependencies += "org.scalatest" % "scalatest_2.11" % "3.0.1" % "test",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1",
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"

  )
