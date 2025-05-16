package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class GameStateSpec extends AnyWordSpec {

  // Since GameState is a trait, you can create a minimal dummy implementation for testing
  class DummyState extends GameState {
    override def rollDice(controller: Controller, dice1: Option[Int], dice2: Option[Int]): GameResult = {
      val d1 = dice1.getOrElse(0)
      val d2 = dice2.getOrElse(0)
      Success(Some(d1 + d2))
    }
    override def move(controller: Controller, spaces: Int): GameResult = Success(Some(spaces))
    override def buy(controller: Controller): GameResult = Success()
    override def buildHouse(controller: Controller, fieldNr: Int): GameResult = Success()
    override def buildHotel(controller: Controller, fieldNr: Int): GameResult = Success()
    override def endTurn(controller: Controller): GameResult = Success()
  }

  "A GameState" should {
    "return Success with the sum of dice on rollDice" in {
      val state = new DummyState
      val controller = new Controller
      val result = state.rollDice(controller, Some(4), Some(3))
      result shouldBe Success(Some(7))
    }

    "return Success with spaces moved on move" in {
      val state = new DummyState
      val controller = new Controller
      val result = state.move(controller, 5)
      result shouldBe Success(Some(5))
    }

    "return Success on buy" in {
      val state = new DummyState
      val controller = new Controller
      state.buy(controller) shouldBe a[Success]
    }

    "return Success on buildHouse" in {
      val state = new DummyState
      val controller = new Controller
      state.buildHouse(controller, 1) shouldBe a[Success]
    }

    "return Success on buildHotel" in {
      val state = new DummyState
      val controller = new Controller
      state.buildHotel(controller, 1) shouldBe a[Success]
    }

    "return Success on endTurn" in {
      val state = new DummyState
      val controller = new Controller
      state.endTurn(controller) shouldBe a[Success]
    }
  }
}
