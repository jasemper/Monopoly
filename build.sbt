val scala3Version = "3.6.4"

lazy val root = project
  .in(file("."))
  .enablePlugins(CoverallsPlugin)
  .settings(
    name := "Monopoly",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalactic" %% "scalactic" % "3.2.14",
      "org.scalatest" %% "scalatest" % "3.2.14" % Test,
      "org.scala-lang.modules" %% "scala-swing" % "3.0.0" // ← Add this line
    ),

    coverageEnabled := true
  )
