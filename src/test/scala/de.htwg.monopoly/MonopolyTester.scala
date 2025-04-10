package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class MonopolyTester extends AnyWordSpec with Matchers {

  "A Player" should {
    "have an initial amount of money equal to 10000" in {
      val player = Player("Blue")
      player.money should be(10000)
    }

    "have the correct color" in {
      val player = Player("Green")
      player.color should be("Green")
    }

    "start at position 0" in {
      val player = Player("Yellow")
      player.position should be(0)
    }

    "not be in jail initially" in {
      val player = Player("Purple")
      player.inJail should be(false)
    }
  }

  "A Property" should {
    "allow ownership to be assigned correctly" in {
      val player = Player("Blue")
      val fieldnr = 0 // Mediterranean Avenue

      // Give ownership
      giveOwner(player, fieldnr)

      // Check that the owner is correctly assigned
      Streets(0).owner should be(Some("Blue"))
    }
  }

  "A Move" should {
    "correctly move a player forward" in {
      val player = Player("Blue")
      val updatedPlayers = movePlayer(Vector(player), 0, 5)

      updatedPlayers(0).position should be(5)
    }

    "pass Go and award $200 when moving past it" in {
      val player = Player("Blue", position = 39) // On Board 39 (next to Go)
      val updatedPlayers = movePlayer(Vector(player), 0, 2)

      updatedPlayers(0).position should be(1) // Passed Go
      updatedPlayers(0).money should be(10200) // $200 bonus
    }
  }
}
