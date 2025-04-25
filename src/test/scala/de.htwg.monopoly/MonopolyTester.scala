package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.monopoly.Main
import java.io.{ByteArrayOutputStream, PrintStream}

class MonopolyTester extends AnyWordSpec {

  "Main" should {
    "print the correct gamestate message to stdout" in {
      val out = new ByteArrayOutputStream()
      Console.withOut(new PrintStream(out)) {
        Main.hello()
      }
      //out.toString shouldBe "    Hello Players.\n  Wanna play a game?\n"
      out.toString shouldBe """Current Player Status:
Player Blue: 10000 dollars
Player Green: 10000 dollars
Player Yellow: 10000 dollars
Player Orange: 10000 dollars
Player Purple: 10000 dollars

Game Board Status:
Field 0: Go
  Players standing here: Blue, Green, Yellow, Orange, Purple
----------------------------------------
Field 1: Mediterranean Avenue
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 2: Community Chest
  Players standing here: No one
----------------------------------------
Field 3: Baltic Avenue
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 4: Income Tax
  Players standing here: No one
----------------------------------------
Field 5: Reading Railroad
  Owner: No owner
  Players standing here: No one
----------------------------------------
Field 6: Oriental Avenue
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 7: Chance
  Players standing here: No one
----------------------------------------
Field 8: Vermont Avenue
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 9: Connecticut Avenue
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 10: Jail / Just Visiting
  Players standing here: No one
----------------------------------------
Field 11: St. Charles Place
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 12: Electric Company
  Owner: No owner
  Players standing here: No one
----------------------------------------
Field 13: States Avenue
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 14: Virginia Avenue
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 15: Pennsylvania Railroad
  Owner: No owner
  Players standing here: No one
----------------------------------------
Field 16: St. James Place
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 17: Community Chest
  Players standing here: No one
----------------------------------------
Field 18: Tennessee Avenue
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 19: New York Avenue
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 20: Free Parking
  Players standing here: No one
----------------------------------------
Field 21: Kentucky Avenue
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 22: Chance
  Players standing here: No one
----------------------------------------
Field 23: Indiana Avenue
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 24: Illinois Avenue
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 25: B&O Railroad
  Owner: No owner
  Players standing here: No one
----------------------------------------
Field 26: Atlantic Avenue
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 27: Ventnor Avenue
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 28: Water Works
  Owner: No owner
  Players standing here: No one
----------------------------------------
Field 29: Marvin Gardens
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 30: Go To Jail
  Players standing here: No one
----------------------------------------
Field 31: Pacific Avenue
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 32: North Carolina Avenue
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 33: Community Chest
  Players standing here: No one
----------------------------------------
Field 34: Pennsylvania Avenue
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 35: Short Line
  Owner: No owner
  Players standing here: No one
----------------------------------------
Field 36: Chance
  Players standing here: No one
----------------------------------------
Field 37: Park Place
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
Field 38: Luxury Tax
  Players standing here: No one
----------------------------------------
Field 39: Boardwalk
  Owner: No owner
  Houses: 0
  Hotels: 0
  Players standing here: No one
----------------------------------------
"""
    }
  }

  "A Player" should {

    "get money when called with positive" in {
      val player = Player("Blue")
      val updatedPlayers = Main.addMoney(Vector(player), 0, 500)
      updatedPlayers(0)._2 should be(10500)
    }
    "loose money when called with negative" in {
      val player = Player("Blue")
      val updatedPlayers = Main.addMoney(Vector(player), 0, -500)
      updatedPlayers(0)._2 should be(9500)
    }

    "gain and then lose money, keeping updates in the same variable" in {
      var players = Vector(Player("Blue"))
      players = Main.addMoney(players, 0, 500)
      players = Main.addMoney(players, 0, -500)
      players(0).money should be(10000)
    }

    "move correctly" in {
      val player = Player("Blue")
      val updatedPlayers = Main.movePlayer(Vector(player), 0, 5)
      updatedPlayers(0).position should be(5)
    }
    "update position and money when passing Go" in {
      val player = Player("Blue")
      val updatedPlayers = Main.movePlayer(Vector(player), 0, 42)
      updatedPlayers(0).position should be(2)
      updatedPlayers(0).money should be(10200)
    }
    "update position and money when landing on Go" in {
      val player = Player("Blue")
      val updatedPlayers = Main.movePlayer(Vector(player), 0, 40)
      updatedPlayers(0).position should be(0)
      updatedPlayers(0).money should be(10400)
    }
    "don't get money when just moving" in {
      val player = Player("Blue")
      val updatedPlayers = Main.movePlayer(Vector(player), 0, 7)
      updatedPlayers(0).position should be(7)
      updatedPlayers(0).money should be(10000)
    }
    
    "own Mediterranean Avenue after calling giveOwner" in {
      val player = Player("Blue")
      val (updatedStreets, _, _) = Main.giveOwner(player, 1)
      updatedStreets(0).owner should be(Some("Blue"))
    }
    "own Short line after calling give Owner" in {
      val player = Player("Blue")
      val (_,updatedTrains, _) = Main.giveOwner(player, 35)
      updatedTrains(3).owner should be(Some("Blue"))
    }
    "own first utility after calling give Owner" in {
      val player = Player("Blue")
      val (_, _, updatedUtility) = Main.giveOwner(player, 12)
      updatedUtility(0).owner should be(Some("Blue"))
    }
  }
   
  "A Street" should {
    "be creatable with a name and colorGroup" in {
      val str = Street("Test Street", Some("Red"), 1, 0, "Red")
      str.name shouldBe "Test Street"
      str.owner shouldBe Some("Red")
      str.colorGroup shouldBe "Red"
    }
  }

  "A Railroad" should {
    "be creatable with a name" in {
      val rr = Railroad("Test Railroad", Some("Player"))
      rr.name shouldBe "Test Railroad"
      rr.owner shouldBe Some("Player")
    }
  }

  "A Utility" should {
    "be creatable with a name" in {
      val util = Utility("Test Utility", Some("Player"))
      util.name shouldBe "Test Utility"
      util.owner shouldBe Some("Player")
    }
  }
}