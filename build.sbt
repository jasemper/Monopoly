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
      "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
      "com.typesafe.play" %% "play-json" % "2.10.0",
      "org.scala-lang.modules" %% "scala-xml" % "2.0.1"
    ),

    coverageEnabled := true
  )
