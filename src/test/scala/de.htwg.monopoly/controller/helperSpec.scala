package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import java.io.{ByteArrayOutputStream, PrintStream}

class helperSpec extends AnyWordSpec {
  "controller" should {
    "be returned as owner" in {
      val controller = new Controller()
      val player = Player("Blue")
      val (updatedStreets, _, _) = giveOwner(player, 14, InitStreets, InitTrains, InitUtilities)
      getOwner(14, updatedStreets, InitTrains, InitUtilities) shouldBe "Blue"
    }
    "be empty on wrongful getOwner" in {
      val controller = new Controller()
      val player = Player("Blue")
      val (updatedStreets, _, _) = giveOwner(player, 17, InitStreets, InitTrains, InitUtilities)
      getOwner(17, updatedStreets, InitTrains, InitUtilities) shouldBe ""
    }
    "be empty on OutOfBounce in getOwner" in {
      val controller = new Controller()
      getOwner(50, InitStreets, InitTrains, InitUtilities) shouldBe ""
    }
    "be empty on OutOfBounce in getHouses" in {
      val controller = new Controller()
      getHouses(50, InitStreets, InitTrains, InitUtilities) shouldBe 0
    }
    "be empty on OutOfBounce in getHotels" in {
      val controller = new Controller()
      getHotels(50, InitStreets, InitTrains, InitUtilities) shouldBe 0
    }
    "return amount of houses" in {
      val controller = new Controller()
      val player = Player("Blue")
      val (updatedStreets, _, _) = giveOwner(player, 14, InitStreets, InitTrains, InitUtilities)
      getHouses(14, updatedStreets, InitTrains, InitUtilities) shouldBe 0
    }
    "return amount of hotels" in {
      val controller = new Controller()
      val player = Player("Blue")
      val (updatedStreets, _, _) = giveOwner(player, 14, InitStreets, InitTrains, InitUtilities)
      getHotels(14, updatedStreets, InitTrains, InitUtilities) shouldBe 0
    }

    "get money when called with positive" in {
      val controller = new Controller()
      val player = Player("Blue")
      val updatedPlayers = addMoney(Vector(player), 0, 500)
      updatedPlayers(0)._2 should be(10500)
    }
    "loose money when called with negative" in {
      val controller = new Controller()
      val player = Player("Blue")
      val updatedPlayers = addMoney(Vector(player), 0, -500)
      updatedPlayers(0)._2 should be(9500)
    }
    "gain and then lose money, keeping updates in the same variable" in {
      val controller = new Controller()
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
      val controller = new Controller()
      var updatedPlayers = InitPlayers.map {
        case player if player.color != "Blue" => player.copy(money = 0)
        case player => player
      }
      getWinner(updatedPlayers) shouldBe "Blue"
    }
    "not win if they're not the only one with money" in {
      val controller = new Controller()
      var updatedPlayers = InitPlayers.map {
        case player if player.color == "Green" => player.copy(money = 0)
        case player => player
      }
      getWinner(updatedPlayers) shouldBe ""
    }
  }
}