package de.htwg.monopoly

import de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import java.io.{ByteArrayOutputStream, PrintStream}

class ControllerSpec extends AnyWordSpec {
  "controller" should {
    "advance to the next player correctly" in {
      val player1 = Player("Blue")
      val player2 = Player("Red")
      val controller = new Controller(players = Vector(player1, player2))

      controller.currentPlayerIndex should be(0) // Start at 0
      controller.nextTurn()
      controller.currentPlayerIndex should be(1) // Now at 1
      controller.nextTurn()
      controller.currentPlayerIndex should be(0) // Wraps back to 0
    }
    "move the player correctly" in {
      val player = Player("Blue")
      val controller = new Controller(players = Vector(player))
      controller.moveCurrentPlayer(5)
      controller.players(0).position should be(5)
    }
    "get the correct field name" in {
      val player = Player("Blue", position = 5)
      val controller = new Controller(players = Vector(player))
      controller.getCurrentFieldName should be("Reading Railroad")
    }
    "buy a property correctly" in {
      val player = Player("Blue", position = 5) //Reading Railroad
      val controller = new Controller(players = Vector(player))
      controller.buyCurrentProperty()
      controller.getCurrentOwner should be("Blue")
    }
    "build a house correctly" in {
      val player = Player("Blue", position = 1) //Mediterranean Avenue
      val controller = new Controller(players = Vector(player))
      controller.buyCurrentProperty()
      controller.getCurrentOwner should be("Blue")
      controller.buildHouse(1)
      controller.streets(0).buildings should be(1)
    }
    "build a hotel correctly" in {
      val player = Player("Blue", position = 1) //Mediterranean Avenue
      val controller = new Controller(players = Vector(player))
      controller.buyCurrentProperty()
      controller.getCurrentOwner should be("Blue")
      controller.buildHotel(1)
      controller.streets(0).hotels should be(1)
    }
    "get the winner correctly" in {
      val player = Player("Blue", position = 1, money = 0)
      val controller = new Controller(players = Vector(player))
      controller.getWinnerIfAny should be(None)
    }
    "get the winner correctly when there is one" in {
      val player = Player("Blue", position = 1, money = 100)
      val controller = new Controller(players = Vector(player))
      controller.getWinnerIfAny should be(Some("Blue"))
    }
    "get the correct owner of a property" in {
      val player = Player("Blue", position = 1) //Mediterranean Avenue
      val controller = new Controller(players = Vector(player))
      controller.buyCurrentProperty()
      controller.getCurrentOwner should be("Blue")
    }
    "return the correct game state" in {
      val controller = new Controller()
      val (players, streets, trains, utilities) = controller.getGameState
      players.length shouldBe 5
      players.map(_.color) should contain allOf ("Blue", "Green", "Yellow", "Orange", "Purple")
      streets.length shouldBe 22
      all (streets.map(_.owner)) shouldBe None
      trains.length shouldBe 4
      all (trains.map(_.owner)) shouldBe None
      utilities.length shouldBe 2
      all (utilities.map(_.owner)) shouldBe None
    }
    "get money when called with positive" in {
      val controller = new Controller()
      val player = Player("Blue")
      val updatedPlayers = controller.addMoney(Vector(player), 0, 500)
      updatedPlayers(0)._2 should be(10500)
    }
    "loose money when called with negative" in {
      val controller = new Controller()
      val player = Player("Blue")
      val updatedPlayers = controller.addMoney(Vector(player), 0, -500)
      updatedPlayers(0)._2 should be(9500)
    }
    "gain and then lose money, keeping updates in the same variable" in {
      val controller = new Controller()
      var player = Vector(Player("Blue"))
      player = controller.addMoney(player, 0, 500)
      player = controller.addMoney(player, 0, -500)
      player(0).money should be(10000)
    }

    "move correctly" in {
      val controller = new Controller()
      val player = Player("Blue")
      val updatedPlayers = controller.movePlayer(Vector(player), 0, 5)
      updatedPlayers(0).position should be(5)
    }
    "update position and money when passing Go" in {
      val controller = new Controller()
      val player = Player("Blue")
      val updatedPlayers = controller.movePlayer(Vector(player), 0, 42)
      updatedPlayers(0).position should be(2)
      updatedPlayers(0).money should be(10200)
    }
    "update position and money when landing on Go" in {
      val controller = new Controller()
      val player = Player("Blue")
      val updatedPlayers = controller.movePlayer(Vector(player), 0, 40)
      updatedPlayers(0).position should be(0)
      updatedPlayers(0).money should be(10400)
    }
    "don't get money when just moving" in {
      val controller = new Controller()
      val player = Player("Blue")
      val updatedPlayers = controller.movePlayer(Vector(player), 0, 7)
      updatedPlayers(0).position should be(7)
      updatedPlayers(0).money should be(10000)
    }

    "own Mediterranean Avenue after calling giveOwner" in {
      val controller = new Controller()
      val player = Player("Blue")
      val (updatedStreets, _, _) = controller.giveOwner(player, 1, InitStreets, InitTrains, InitUtilities)
      updatedStreets(0).owner should be(Some("Blue"))
    }
    "own Short line after calling give Owner" in {
      val controller = new Controller()
      val player = Player("Blue")
      val (_,updatedTrains, _) = controller.giveOwner(player, 35, InitStreets, InitTrains, InitUtilities)
      updatedTrains(3).owner should be(Some("Blue"))
    }
    "own first utility after calling give Owner" in {
      val controller = new Controller()
      val player = Player("Blue")
      val (_, _, updatedUtility) = controller.giveOwner(player, 12, InitStreets, InitTrains, InitUtilities)
      updatedUtility(0).owner should be(Some("Blue"))
    }

    "win if they're the only one with money" in {
      val controller = new Controller()
      var updatedPlayers = InitPlayers.map {
        case player if player.color != "Blue" => player.copy(money = 0)
        case player => player
      }
      controller.getWinner(updatedPlayers) shouldBe "Blue"
    }
    "not win if they're not the only one with money" in {
      val controller = new Controller()
      var updatedPlayers = InitPlayers.map {
        case player if player.color == "Green" => player.copy(money = 0)
        case player => player
      }
      controller.getWinner(updatedPlayers) shouldBe ""
    }

    "be returned as owner" in {
      val controller = new Controller()
      val player = Player("Blue")
      val (updatedStreets, _, _) = controller.giveOwner(player, 14, InitStreets, InitTrains, InitUtilities)
      controller.getOwner(14, updatedStreets, InitTrains, InitUtilities) shouldBe "Blue"
    }
    "be empty on wrongful getOwner" in {
      val controller = new Controller()
      val player = Player("Blue")
      val (updatedStreets, _, _) = controller.giveOwner(player, 17, InitStreets, InitTrains, InitUtilities)
      controller.getOwner(17, updatedStreets, InitTrains, InitUtilities) shouldBe ""
    }
    "be empty on OutOfBounce in getOwner" in {
      val controller = new Controller()
      controller.getOwner(50, InitStreets, InitTrains, InitUtilities) shouldBe ""
    }
    "be empty on OutOfBounce in getHouses" in {
      val controller = new Controller()
      controller.getHouses(50, InitStreets, InitTrains, InitUtilities) shouldBe 0
    }
    "be empty on OutOfBounce in getHotels" in {
      val controller = new Controller()
      controller.getHotels(50, InitStreets, InitTrains, InitUtilities) shouldBe 0
    }
    "return amount of houses" in {
      val controller = new Controller()
      val player = Player("Blue")
      val (updatedStreets, _, _) = controller.giveOwner(player, 14, InitStreets, InitTrains, InitUtilities)
      controller.getHouses(14, updatedStreets, InitTrains, InitUtilities) shouldBe 0
    }
    "return amount of hotels" in {
      val controller = new Controller()
      val player = Player("Blue")
      val (updatedStreets, _, _) = controller.giveOwner(player, 14, InitStreets, InitTrains, InitUtilities)
      controller.getHotels(14, updatedStreets, InitTrains, InitUtilities) shouldBe 0
    }
  }
}