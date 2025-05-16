package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class WaitingForRollSpec extends AnyWordSpec {

  "rollDice" should {
    "return Success with Some(total) when no dice are provided" in {
      val controller = new Controller
      val state = new WaitingForRoll
      val result = state.rollDice(controller)
      result shouldBe a [Success]
    }

    "return Success with Some(4) when dice (1, 3) are passed" in {
      val controller = new Controller
      val state = new WaitingForRoll
      val result = state.rollDice(controller, Some(1), Some(3))
      result shouldBe Success(Some(4))
    }

    "return Success with Some(5) when only d1 is passed" in {
      val controller = new Controller
      val state = new WaitingForRoll
      val result = state.rollDice(controller, Some(5))
      result shouldBe Success(Some(5))
    }

    "return Success with Some(6) when only d2 is passed" in {
      val controller = new Controller
      val state = new WaitingForRoll
      val result = state.rollDice(controller, None, Some(6))
      result shouldBe Success(Some(6))
    }
  }

  "move" should {
    "return Error indicating dice must be rolled first" in {
      val controller = new Controller
      val state = new WaitingForRoll
      val result = state.move(controller, 1)
      result shouldBe Error("You must roll the dice before moving.")
    }
  }

  "buy" should {
    "return Error indicating move must occur first" in {
      val controller = new Controller
      val state = new WaitingForRoll
      val result = state.buy(controller)
      result shouldBe Error("You can't buy anything before moving.")
    }
  }

  "buildHouse" should {
    "return Error indicating move must be finished" in {
      val controller = new Controller
      val state = new WaitingForRoll
      val result = state.buildHouse(controller, 1)
      result shouldBe Error("You must finish your move before building.")
    }
  }

  "buildHotel" should {
    "return Error indicating move must be finished" in {
      val controller = new Controller
      val state = new WaitingForRoll
      val result = state.buildHotel(controller, 1)
      result shouldBe Error("You must finish your move before building.")
    }
  }

  "endTurn" should {
    "return Error indicating dice must be rolled and moved first" in {
      val controller = new Controller
      val state = new WaitingForRoll
      val result = state.endTurn(controller)
      result shouldBe Error("You must roll and move first.")
    }
  }
}