val scala3Version = "3.6.4"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Monopoly",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    //libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test
    //libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.14"
    //libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.14" % "test"

    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "org.scalactic" %% "scalactic" % "3.2.14",
      "org.scalatest" %% "scalatest" % "3.2.14" % Test
    )
  )
