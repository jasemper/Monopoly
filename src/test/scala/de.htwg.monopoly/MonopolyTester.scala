package de.htwg.monopoly

import de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import java.io.{ByteArrayOutputStream, PrintStream}

class MonopolyTester extends AnyWordSpec {

  "data" should {
    "hold the data for players" in {
      InitPlayers contains Player("Green") shouldBe true
    }
    "hold the data for streets" in {
      InitStreets contains Street("Tennessee Avenue", None, 0, 0, "Orange") shouldBe true
    }
    "hold the data for trains" in {
      InitTrains contains Railroad("B&O Railroad", None) shouldBe true
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
  //-------------------------------------------------------------------------------------------------------------------   
  "model" should {
    "be creatable with a name and colorGroup for Street" in {
      val str: Property = Street("Test Street", Some("Red"), 1, 0, "Red")
      str.name shouldBe "Test Street"
      str.owner shouldBe Some("Red")
    }
    "be creatable with a name for Railroad" in {
      val rr: Property = Railroad("Test Railroad", Some("Player"))
      rr.name shouldBe "Test Railroad"
      rr.owner shouldBe Some("Player")
    }
    "be creatable with a name for Utility" in {
      val util: Property = Utility("Test Utility", Some("Player"))
      util.name shouldBe "Test Utility"
      util.owner shouldBe Some("Player")
    }
  }
}