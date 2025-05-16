package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class BuildingSpec extends AnyWordSpec {

  "rollDice" should {
    "return Error indicating already moved and bought" in {
      val controller = new Controller
      val state = new Building
      val result = state.rollDice(controller, 1, 2)
      result shouldBe Error("You already moved and bought.")
    }
  }

  "move" should {
    "return Error indicating already moved" in {
      val controller = new Controller
      val state = new Building
      val result = state.move(controller, 1)
      result shouldBe Error("You already moved.")
    }
  }

  "buy" should {
    "return Error indicating already bought or skipped" in {
      val controller = new Controller
      val state = new Building
      val result = state.buy(controller)
      result shouldBe Error("You already bought or skipped buying.")
    }
  }

  "buildHouse" should {
    "return Success after building house" in {
      val controller = new Controller
      val state = new Building
      val result = state.buildHouse(controller, 1)
      result shouldBe Success()
    }
  }

  "buildHotel" should {
    "return Success after building hotel" in {
      val controller = new Controller
      val state = new Building
      val result = state.buildHotel(controller, 1)
      result shouldBe Success()
    }
  }

  "endTurn" should {
    "return Success and set state to InJail if next player is in jail" in {
      val player1 = Player("Blue")
      val player2 = Player("Red", inJail = true)
      val controller = new Controller(players = Vector(player1, player2))
      val state = new Building

      val result = state.endTurn(controller)
      result shouldBe Success()
      controller.state shouldBe a [InJail]
    }

    "return Success and set state to WaitingForRoll if next player is not in jail" in {
      val player1 = Player("Blue")
      val player2 = Player("Red")
      val controller = new Controller(players = Vector(player1, player2))
      val state = new Building

      val result = state.endTurn(controller)
      result shouldBe Success()
      controller.state shouldBe a [WaitingForRoll]
    }
  }
}