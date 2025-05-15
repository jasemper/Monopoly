package de.htwg.monopoly

import de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class InJailSpec extends AnyWordSpec {
    "rolldice" should {
        "get free with pasch" in {
            val player1 = Player("Blue", inJail = true)
            val controller = new Controller(players = Vector(player1))
            val state = new InJail
            state.rollDice(controller, 1, 1) should be(2)
            controller.players(0).inJail should be(false)
        }
        "stay in jail without pasch" in {
            val player1 = Player("Blue", inJail = true)
            val controller = new Controller(players = Vector(player1))
            val state = new InJail
            state.rollDice(controller, 1, 2) should be(3)
            controller.players(0).inJail should be(true)
        }
    }
    "move" should {
        "return -1" in {
            val controller = new Controller
            val state = new InJail
            state.move(controller, 1) should be(-1)
        }
    }
    "buy" should {
        "return -1" in {
            val controller = new Controller
            val state = new InJail
            state.buy(controller) should be(-1)
        }
    }
    "buildHouse" should {
        "return -1" in {
            val controller = new Controller
            val state = new InJail
            state.buildHouse(controller, 1) should be(-1)
        }
    }
    "buildHotel" should {
        "return -1" in {
            val controller = new Controller
            val state = new InJail
            state.buildHotel(controller, 1) should be(-1)
        }
    }
    "endTurn" should {
        "return 0" in {
            val controller = new Controller
            val state = new InJail
            state.endTurn(controller) should be(0)
        }
        "set state to InJail" in {
            val player1 = Player("Blue")
            val player2 = Player("Red", inJail = true)
            val controller = new Controller(players = Vector(player1, player2))
            val state = new InJail
            state.endTurn(controller)
            controller.state should be(an[InJail])
        }
        "set state to WaitingForRoll" in {
            val player1 = Player("Blue")
            val player2 = Player("Red")
            val controller = new Controller(players = Vector(player1, player2))
            val state = new InJail
            state.endTurn(controller)
            controller.state should be(an[WaitingForRoll])
        }
    }
}