package de.htwg.monopoly

import de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class ControllerSpec extends AnyWordSpec {
  "rolldice" should {
    "roll the dice correctly" in {
      val controller = new Controller()
      val result = controller.rollDice()
      result should be >= 2
      result should be <= 12
    }
    "call on Jail" in {
      val controller = new Controller()
      val result = controller.rollDice(4,4)
      result should be(8)
    }
  }
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
    "not build a house on a property not owned" in {
      val player = Player("Blue", position = 1) //Mediterranean Avenue
      val controller = new Controller(players = Vector(player))
      controller.buildHouse(1)
      controller.streets(0).buildings should be(0)
    }
    "build a hotel correctly" in {
      val player = Player("Blue", position = 1) //Mediterranean Avenue
      val controller = new Controller(players = Vector(player))
      controller.buyCurrentProperty()
      controller.getCurrentOwner should be("Blue")
      controller.buildHotel(1)
      controller.streets(0).hotels should be(1)
    }
    "not build a hotel on a property not owned" in {
      val player = Player("Blue", position = 1) //Mediterranean Avenue
      val controller = new Controller(players = Vector(player))
      controller.buildHotel(1)
      controller.streets(0).hotels should be(0)
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
  }
  "CurrentPlayerPasch" should {
    "send the player to jail" in {
      val player = Player ("Blue", pasch = 3)
      val controller = new Controller(players = Vector(player))
      controller.currentPlayer.inJail should be (false)
      controller.currentPlayerPasch()
      controller.currentPlayer.inJail should be (true)
    }
  }
  "moveCurrentPlayer" should {
    "move the player correctly" in {
      val player = Player("Blue", position = 0)
      val controller = new Controller(players = Vector(player))
      controller.moveCurrentPlayer(5)
      controller.players(0).position should be(5)
    }
    "move the player to jail" in {
      val player = Player("Blue", position = 0)
      val controller = new Controller(players = Vector(player))
      controller.toJail()
      controller.players(0).inJail should be(true)
    }
    "have the player pay rent" in {
      val controller = new Controller()
      controller.moveCurrentPlayer(1)
      controller.buyCurrentProperty()
      controller.nextTurn()
      controller.moveCurrentPlayer(1)
      controller.players(0).money should be(9100)
      controller.players(1).money should be(9900)
    }
  }
  "buyCurrentProperty" should {
    "buy the property correctly" in {
      val player = Player("Blue", position = 1) //Mediterranean Avenue
      val controller = new Controller(players = Vector(player))
      controller.buyCurrentProperty()
      controller.getCurrentOwner should be("Blue")
    }
    "not buy a property already owned" in {
      val controller = new Controller()
      controller.moveCurrentPlayer(1)
      controller.buyCurrentProperty()
      controller.getCurrentOwner should be("Blue")
      controller.nextTurn()
      controller.moveCurrentPlayer(1)
      controller.buyCurrentProperty()
      controller.getCurrentOwner should be("Blue")
    }
  }
}