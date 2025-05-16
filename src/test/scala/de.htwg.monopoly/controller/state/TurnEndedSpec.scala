package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class TurnEndedSpec extends AnyWordSpec {

  "rollDice" should {
    "return Error indicating turn has ended" in {
      val controller = new Controller
      val state = new TurnEnded
      val result = state.rollDice(controller, 1, 2)
      result shouldBe Error("Wait for your next turn.")
    }
  }

  "move" should {
    "return Error indicating turn has ended" in {
      val controller = new Controller
      val state = new TurnEnded
      val result = state.move(controller, 1)
      result shouldBe Error("Wait for your next turn.")
    }
  }

  "buy" should {
    "return Error indicating turn has ended" in {
      val controller = new Controller
      val state = new TurnEnded
      val result = state.buy(controller)
      result shouldBe Error("Wait for your next turn.")
    }
  }

  "buildHouse" should {
    "return Error indicating turn has ended" in {
      val controller = new Controller
      val state = new TurnEnded
      val result = state.buildHouse(controller, 1)
      result shouldBe Error("Wait for your next turn.")
    }
  }

  "buildHotel" should {
    "return Error indicating turn has ended" in {
      val controller = new Controller
      val state = new TurnEnded
      val result = state.buildHotel(controller, 1)
      result shouldBe Error("Wait for your next turn.")
    }
  }

  "endTurn" should {
    "return Success and set state to InJail if next player is in jail" in {
      val player1 = Player("Blue")
      val player2 = Player("Red", inJail = true)
      val controller = new Controller(players = Vector(player1, player2))
      val state = new TurnEnded

      val result = state.endTurn(controller)
      result shouldBe Success()
      controller.state shouldBe a [InJail]
    }

    "return Success and set state to WaitingForRoll if next player is not in jail" in {
      val player1 = Player("Blue")
      val player2 = Player("Red")
      val controller = new Controller(players = Vector(player1, player2))
      val state = new TurnEnded

      val result = state.endTurn(controller)
      result shouldBe Success()
      controller.state shouldBe a [WaitingForRoll]
    }
  }
}
