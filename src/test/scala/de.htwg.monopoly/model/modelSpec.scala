package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class modelSpec extends AnyWordSpec {
    "model" should {
        "have a player with pasch 0" in {
            val player: Player = Player("Test Player")
            player.pasch shouldBe 0
        }
        "be creatable with a name and colorGroup for Street" in {
        val str: Property = Street("Test Street", Some("Red"), 1, 0, "Red")
        str.name shouldBe "Test Street"
        str.owner shouldBe Some("Red")
        }
        "create a street with base values" in {
            val str: Street = Street("Test Street", colorGroup = "Red")
            str.owner shouldBe None
            str.buildings shouldBe 0
            str.hotels shouldBe 0
        }
        "be creatable with a name for Railroad" in {
        val rr: Property = Train("Test Railroad", Some("Player"))
        rr.name shouldBe "Test Railroad"
        rr.owner shouldBe Some("Player")
        }
        "create a railroad with base values" in {
            val rr: Train = Train("Test Railroad")
            rr.owner shouldBe None
        }
        "be creatable with a name for Utility" in {
        val util: Property = Utility("Test Utility", Some("Player"))
        util.name shouldBe "Test Utility"
        util.owner shouldBe Some("Player")
        }
        "create a utility with base values" in {
            val util: Utility = Utility("Test Utility")
            util.owner shouldBe None
        }
    }
}