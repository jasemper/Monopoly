package de.htwg.monopoly

import de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class BuyingSpec extends AnyWordSpec {
    "rolldice" should {
        "return -1" in {
            val controller = new Controller
            val state = new Buying
            state.rollDice(controller, 1, 2) should be(-1)
        }
    }
    "move" should {
        "return -1" in {
            val controller = new Controller
            val state = new Buying
            state.move(controller, 1) should be(-1)
        }
    }
    "buy" should {
        "return 0" in {
            val controller = new Controller
            val state = new Buying
            state.buy(controller) should be(0)
        }
    }
    "buildHouse" should {
        "return -1" in {
            val controller = new Controller
            val state = new Buying
            state.buildHouse(controller, 1) should be(-1)
        }
    }
    "buildHotel" should {
        "return -1" in {
            val controller = new Controller
            val state = new Buying
            state.buildHotel(controller, 1) should be(-1)
        }
    }
    "endTurn" should {
        "return 0" in {
            val controller = new Controller
            val state = new Buying
            state.endTurn(controller) should be(0)
        }
        "set state to InJail" in {
            val player1 = Player("Blue")
            val player2 = Player("Red", inJail = true)
            val controller = new Controller(players = Vector(player1, player2))
            val state = new Buying
            state.endTurn(controller)
            controller.state should be(an[InJail])
        }
        "set state to WaitingForRoll" in {
            val player1 = Player("Blue")
            val player2 = Player("Red")
            val controller = new Controller(players = Vector(player1, player2))
            val state = new Buying
            state.endTurn(controller)
            controller.state should be(an[WaitingForRoll])
        }
    }
}