package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class MonopolyTester extends AnyFlatSpec with should.Matchers {

  "A Player" should "own Mediterranean Avenue after calling giveOwner" in {
    val player = Player("Blue")
    Main.giveOwner(player, 0)  // Accessing the giveOwner method from Main
    Main.Streets(0).owner should be(Some("Blue"))
  }

  it should "move correctly" in {
    val player = Player("Blue")
    val updatedPlayers = Main.movePlayer(Vector(player), 0, 5)
    updatedPlayers(0).position should be(5)
  }

  it should "update position and money when passing Go" in {
    val player = Player("Blue")
    val updatedPlayers = Main.movePlayer(Vector(player), 0, 40)  // Move to 40, which is "Go"
    updatedPlayers(0).position should be(0)  // Back to Go
    updatedPlayers(0).money should be(10200)  // Bonus of 200
  }
}
