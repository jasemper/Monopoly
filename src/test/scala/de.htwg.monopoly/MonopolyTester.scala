package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class MonopolyTester extends AnyWordSpec {

  "A Player" should {
    
    "own Mediterranean Avenue after calling giveOwner" in {
      val player = Player("Blue")
      Main.giveOwner(player, 0)  // Accessing the giveOwner method from Main
      Main.Streets(0).owner should be(Some("Blue"))
    }

    "move correctly" in {
      val player = Player("Blue")
      val updatedPlayers = Main.movePlayer(Vector(player), 0, 5)
      updatedPlayers(0).position should be(5)
    }

    "update position and money when passing Go" in {
      val player = Player("Blue")
      val updatedPlayers = Main.movePlayer(Vector(player), 0, 42)  // Move to 2, after doing a full run
      updatedPlayers(0).position should be(2)
      updatedPlayers(0).money should be(10200)  // Bonus of 200
    }

    "update position and money when landing on Go" in {
      val player = Player("Blue")
      val updatedPlayers = Main.movePlayer(Vector(player), 0, 40)  // Move to Go, after doing a full run
      updatedPlayers(0).position should be(0)
      updatedPlayers(0).money should be(10400)  // Bonus of 400
    }

    "don't get money when just moving" in {
      val player = Player("Blue")
      val updatedPlayers = Main.movePlayer(Vector(player), 0, 7)  // Move to 7
      updatedPlayers(0).position should be(7)
      updatedPlayers(0).money should be(10000)  // Bonus of 200
    }
  }
}
