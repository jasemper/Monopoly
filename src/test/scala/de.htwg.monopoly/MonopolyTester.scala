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
  "functions" should {
    "get money when called with positive" in {
      val player = Player("Blue")
      val updatedPlayers = addMoney(Vector(player), 0, 500)
      updatedPlayers(0)._2 should be(10500)
    }
    "loose money when called with negative" in {
      val player = Player("Blue")
      val updatedPlayers = addMoney(Vector(player), 0, -500)
      updatedPlayers(0)._2 should be(9500)
    }
    "gain and then lose money, keeping updates in the same variable" in {
      var player = Vector(Player("Blue"))
      player = addMoney(player, 0, 500)
      player = addMoney(player, 0, -500)
      player(0).money should be(10000)
    }

    "move correctly" in {
      val player = Player("Blue")
      val updatedPlayers = movePlayer(Vector(player), 0, 5)
      updatedPlayers(0).position should be(5)
    }
    "update position and money when passing Go" in {
      val player = Player("Blue")
      val updatedPlayers = movePlayer(Vector(player), 0, 42)
      updatedPlayers(0).position should be(2)
      updatedPlayers(0).money should be(10200)
    }
    "update position and money when landing on Go" in {
      val player = Player("Blue")
      val updatedPlayers = movePlayer(Vector(player), 0, 40)
      updatedPlayers(0).position should be(0)
      updatedPlayers(0).money should be(10400)
    }
    "don't get money when just moving" in {
      val player = Player("Blue")
      val updatedPlayers = movePlayer(Vector(player), 0, 7)
      updatedPlayers(0).position should be(7)
      updatedPlayers(0).money should be(10000)
    }
    
    "own Mediterranean Avenue after calling giveOwner" in {
      val player = Player("Blue")
      val (updatedStreets, _, _) = giveOwner(player, 1, InitStreets, InitTrains, InitUtilities)
      updatedStreets(0).owner should be(Some("Blue"))
    }
    "own Short line after calling give Owner" in {
      val player = Player("Blue")
      val (_,updatedTrains, _) = giveOwner(player, 35, InitStreets, InitTrains, InitUtilities)
      updatedTrains(3).owner should be(Some("Blue"))
    }
    "own first utility after calling give Owner" in {
      val player = Player("Blue")
      val (_, _, updatedUtility) = giveOwner(player, 12, InitStreets, InitTrains, InitUtilities)
      updatedUtility(0).owner should be(Some("Blue"))
    }

    "win if they're the only one with money" in {
      var updatedPlayers = InitPlayers.map {
        case player if player.color != "Blue" => player.copy(money = 0)
        case player => player
      }
      getWinner(updatedPlayers) shouldBe "Blue"
    }
    "not win if they're not the only one with money" in {
      var updatedPlayers = InitPlayers.map {
        case player if player.color == "Green" => player.copy(money = 0)
        case player => player
      }
      getWinner(updatedPlayers) shouldBe ""
    }

    "be returned as owner" in {
      val player = Player("Blue")
      val (updatedStreets, _, _) = giveOwner(player, 14, InitStreets, InitTrains, InitUtilities)
      getOwner(14, updatedStreets, InitTrains, InitUtilities) shouldBe "Blue"
    }
    "be empty on wrongful getOwner" in {
      val player = Player("Blue")
      val (updatedStreets, _, _) = giveOwner(player, 17, InitStreets, InitTrains, InitUtilities)
      getOwner(17, updatedStreets, InitTrains, InitUtilities) shouldBe ""
    }
    "be empty on OutOfBounce in getOwner" in {
      getOwner(50, InitStreets, InitTrains, InitUtilities) shouldBe ""
    }
    "be empty on OutOfBounce in getHouses" in {
      getHouses(50, InitStreets, InitTrains, InitUtilities) shouldBe 0
    }
    "be empty on OutOfBounce in getHotels" in {
      getHotels(50, InitStreets, InitTrains, InitUtilities) shouldBe 0
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