package de.htwg.monopoly

import de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class BuildingSpec extends AnyWordSpec {
    "rolldice" should {
        "return -1" in {
            val controller = new Controller
            val state = new Building
            state.rollDice(controller, 1, 2) should be(-1)
        }
    }
    "move" should {
        "return -1" in {
            val controller = new Controller
            val state = new Building
            state.move(controller, 1) should be(-1)
        }
    }
    "buy" should {
        "return -1" in {
            val controller = new Controller
            val state = new Building
            state.buy(controller) should be(-1)
        }
    }
    "buildHouse" should {
        "return 0" in {
            val controller = new Controller
            val state = new Building
            state.buildHouse(controller, 1) should be(0)
        }
    }
    "buildHotel" should {
        "return 0" in {
            val controller = new Controller
            val state = new Building
            state.buildHotel(controller, 1) should be(0)
        }
    }
    "endTurn" should {
        "return 0" in {
            val controller = new Controller
            val state = new Building
            state.endTurn(controller) should be(0)
        }
        "set state to InJail" in {
            val player1 = Player("Blue")
            val player2 = Player("Red", inJail = true)
            val controller = new Controller(players = Vector(player1, player2))
            val state = new Building
            state.endTurn(controller)
            controller.state should be(an[InJail])
        }
        "set state to WaitingForRoll" in {
            val player1 = Player("Blue")
            val player2 = Player("Red")
            val controller = new Controller(players = Vector(player1, player2))
            val state = new Building
            state.endTurn(controller)
            controller.state should be(an[WaitingForRoll])
        }
    }
}