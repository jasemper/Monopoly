package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class MovingSpec extends AnyWordSpec {

  "rollDice" should {
    "return Error because you can't roll while moving" in {
      val controller = new Controller
      val state = new Moving
      val result = state.rollDice(controller, 1, 2)
      result shouldBe Error("You already rolled this turn.")
    }
  }

  "move" should {
    "return Success when movement is done" in {
      val controller = new Controller
      val state = new Moving
      val result = state.move(controller, 1)
      result shouldBe Success()
    }
  }

  "buy" should {
    "return Error because you need to finish moving first" in {
      val controller = new Controller
      val state = new Moving
      val result = state.buy(controller)
      result shouldBe Error("You first need to move this turn.")
    }
  }

  "buildHouse" should {
    "return Error because you need to finish moving first" in {
      val controller = new Controller
      val state = new Moving
      val result = state.buildHouse(controller, 1)
      result shouldBe Error("You must finish your move before building.")
    }
  }

  "buildHotel" should {
    "return Error because you need to finish moving first" in {
      val controller = new Controller
      val state = new Moving
      val result = state.buildHotel(controller, 1)
      result shouldBe Error("You must finish your move before building.")
    }
  }

  "endTurn" should {
    "return Success and set state to InJail if next player is in jail" in {
      val player1 = Player("Blue")
      val player2 = Player("Red", inJail = true)
      val controller = new Controller(players = Vector(player1, player2))
      val state = new Moving

      val result = state.endTurn(controller)
      result shouldBe Success()
      controller.state shouldBe a [InJail]
    }

    "return Success and set state to WaitingForRoll if next player is not in jail" in {
      val player1 = Player("Blue")
      val player2 = Player("Red")
      val controller = new Controller(players = Vector(player1, player2))
      val state = new Moving

      val result = state.endTurn(controller)
      result shouldBe Success()
      controller.state shouldBe a [WaitingForRoll]
    }
  }
}