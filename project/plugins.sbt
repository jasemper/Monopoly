    addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.3.1")
    addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.3.1")
import org.scoverage.coveralls.Imports.CoverallsKeys._

coverallsToken := Some("o71NvgSbV1nCd6tIjpy0f0hTktIW0DKCf")
