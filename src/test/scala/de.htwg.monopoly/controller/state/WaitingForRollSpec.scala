package de.htwg.monopoly

import de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class WaitingForRollSpec extends AnyWordSpec {
    "rolldice" should {
        "return a number 2-12 with no dice" in {
            val controller = new Controller
            val state = new WaitingForRoll
            state.rollDice(controller) should be > 1
        }
        "return 4 with both dice" in {
            val controller = new Controller
            val state = new WaitingForRoll
            state.rollDice(controller, 1, 3) should be(4)
        }
        "return 5 with only d1" in {
            val controller = new Controller
            val state = new WaitingForRoll
            state.rollDice(controller, 5) should be(5)
        }
        "return 6 with only d2" in {
            val controller = new Controller
            val state = new WaitingForRoll
            state.rollDice(controller, -1, 6) should be(6)
        }
    }
    "move" should {
        "return -1" in {
            val controller = new Controller
            val state = new WaitingForRoll
            state.move(controller, 1) should be(-1)
        }
    }
    "buy" should {
        "return -1" in {
            val controller = new Controller
            val state = new WaitingForRoll
            state.buy(controller) should be(-1)
        }
    }
    "buildHouse" should {
        "return -1" in {
            val controller = new Controller
            val state = new WaitingForRoll
            state.buildHouse(controller, 1) should be(-1)
        }
    }
    "buildHotel" should {
        "return -1" in {
            val controller = new Controller
            val state = new WaitingForRoll
            state.buildHotel(controller, 1) should be(-1)
        }
    }
    "endTurn" should {
        "return -1" in {
            val controller = new Controller
            val state = new WaitingForRoll
            state.endTurn(controller) should be(-1)
        }
    }
}