package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class dataSpec extends AnyWordSpec {
  "data" should {
    "hold the data for players" in {
      InitPlayers contains Player("Green") shouldBe true
    }
    "hold the data for streets" in {
      InitStreets contains Street("Tennessee Avenue", None, 0, 0, "Orange") shouldBe true
    }
    "hold the data for trains" in {
      InitTrains contains Train("B&O Railroad", None) shouldBe true
    }
    "hold the data for utilities" in {
      InitUtilities contains Utility("Water Works", None) shouldBe true
    }
    "hold the data for events" in {
      Events contains Event("Go back 3 spaces", MoveSpaces(-3)) shouldBe true
    }
    "hold the data for boards" in {
      Board contains 0 -> "Go" shouldBe true
    }
  }
}