package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class InJailSpec extends AnyWordSpec {

  "rollDice" should {
    "get free with pasch (double)" in {
      val player1 = Player("Blue", inJail = true)
      val controller = new Controller(players = Vector(player1))
      val state = new InJail

      val result = state.rollDice(controller, Some(1), Some(1))
      result shouldBe Success(Some(2))
      controller.players(0).inJail shouldBe false
      controller.state shouldBe a [Buying]
    }

    "stay in jail without pasch" in {
      val player1 = Player("Blue", inJail = true)
      val controller = new Controller(players = Vector(player1))
      val state = new InJail

      val result = state.rollDice(controller, Some(1), Some(2))
      result shouldBe Success(None)
      controller.players(0).inJail shouldBe true
      controller.state shouldBe a [TurnEnded]
    }
    "stay in jail without pasch X None" in {
      val player1 = Player("Blue", inJail = true)
      val controller = new Controller(players = Vector(player1))
      val state = new InJail

      val result = state.rollDice(controller, Some(1), None)
      result shouldBe Success(None)
      controller.players(0).inJail shouldBe true
      controller.state shouldBe a [TurnEnded]
    }
    "stay in jail without pasch None X" in {
      val player1 = Player("Blue", inJail = true)
      val controller = new Controller(players = Vector(player1))
      val state = new InJail

      val result = state.rollDice(controller, None, Some(1))
      result shouldBe Success(None)
      controller.players(0).inJail shouldBe true
      controller.state shouldBe a [TurnEnded]
    }
    "stay in jail without pasch None None" in {
      val player1 = Player("Blue", inJail = true)
      val controller = new Controller(players = Vector(player1))
      var state = new InJail
      var result = state.rollDice(controller, None, None)

      while (result != Success(None)) {
        state = new InJail
        result = state.rollDice(controller, None, None)
      }
      
      result shouldBe Success(None)
      controller.players(0).inJail shouldBe true
      controller.state shouldBe a [TurnEnded]
    }
  }

  "move" should {
    "return Error indicating player is in jail" in {
      val controller = new Controller
      val state = new InJail
      val result = state.move(controller, 1)
      result shouldBe Error("You're in jail. Roll a double to get out.")
    }
  }

  "buy" should {
    "return Error indicating player is in jail" in {
      val controller = new Controller
      val state = new InJail
      val result = state.buy(controller)
      result shouldBe Error("You're in jail.")
    }
  }

  "buildHouse" should {
    "return Error indicating player is in jail" in {
      val controller = new Controller
      val state = new InJail
      val result = state.buildHouse(controller, 1)
      result shouldBe Error("You're in jail. Can't build now.")
    }
  }

  "buildHotel" should {
    "return Error indicating player is in jail" in {
      val controller = new Controller
      val state = new InJail
      val result = state.buildHotel(controller, 1)
      result shouldBe Error("You're in jail. Can't build now.")
    }
  }

  "endTurn" should {
    "return Success and set state to InJail if next player is in jail" in {
      val player1 = Player("Blue")
      val player2 = Player("Red", inJail = true)
      val controller = new Controller(players = Vector(player1, player2))
      val state = new InJail

      val result = state.endTurn(controller)
      result shouldBe Success()
      controller.state shouldBe a [InJail]
    }

    "return Success and set state to WaitingForRoll if next player is not in jail" in {
      val player1 = Player("Blue")
      val player2 = Player("Red")
      val controller = new Controller(players = Vector(player1, player2))
      val state = new InJail

      val result = state.endTurn(controller)
      result shouldBe Success()
      controller.state shouldBe a [WaitingForRoll]
    }
  }
}