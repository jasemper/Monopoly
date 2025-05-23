package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class BuyingSpec extends AnyWordSpec {

  "rollDice" should {
    "return Error indicating dice already rolled this turn" in {
      val controller = new Controller
      val state = new Buying
      val result = state.rollDice(controller, Some(1), Some(2))
      result shouldBe Error("You already rolled this turn.")
    }
  }

  "move" should {
    "return Error indicating already moved this turn" in {
      val controller = new Controller
      val state = new Buying
      val result = state.move(controller, 1)
      result shouldBe Error("You already moved this turn.")
    }
  }

  "buy" should {
    "return Success after buying property and change state to Building" in {
      val controller = new Controller
      val state = new Buying
      val result = state.buy(controller)
      result shouldBe Success()
      controller.state shouldBe a [Building]
    }
  }

  "buildHouse" should {
    "return Success after building house" in {
      val controller = new Controller
      val state = new Buying
      val result = state.buildHouse(controller, 1)
      result shouldBe Success()
    }
  }

  "buildHotel" should {
    "return Success after building hotel" in {
      val controller = new Controller
      val state = new Buying
      val result = state.buildHotel(controller, 1)
      result shouldBe Success()
    }
  }

  "endTurn" should {
    "return Success and set state to InJail if next player is in jail" in {
      val player1 = Player("Blue")
      val player2 = Player("Red", inJail = true)
      val controller = new Controller(players = Vector(player1, player2))
      val state = new Buying

      val result = state.endTurn(controller)
      result shouldBe Success()
      controller.state shouldBe a [InJail]
    }

    "return Success and set state to WaitingForRoll if next player is not in jail" in {
      val player1 = Player("Blue")
      val player2 = Player("Red")
      val controller = new Controller(players = Vector(player1, player2))
      val state = new Buying

      val result = state.endTurn(controller)
      result shouldBe Success()
      controller.state shouldBe a [WaitingForRoll]
    }
  }
}
