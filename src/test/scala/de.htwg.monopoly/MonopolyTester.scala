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
        val player = Player("Blue")
        val updatedPlayers = Main.addMoney(Vector(player), 0, -500)
        val (updatedStreets, _, _) = Main.giveOwner(player, 14)
        Main.statusReport(updatedPlayers, Main.Streets, Main.Trains, Main.Utilities)
      }
      out.toString.contains("error") shouldBe false
      out.toString.contains("Blue:   9500") shouldBe true
      out.toString.contains("Virginia Avenue") shouldBe true
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