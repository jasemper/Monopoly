package de.htwg.monopoly

import de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class agressiveSpec extends AnyWordSpec {
    "agressiveAI" should {
        "agressive move the AI" in {
            val player = Player("Blue", position = 0, strategy = Some(new AggressiveStrategy))
            val controller = new Controller(players = Vector(player))
            controller.performAITurn()
            controller.players(0).position should be > 0
        }
        "agressive buy a property" in {
            val player = Player("Blue", position = 1, strategy = Some(new AggressiveStrategy))
            val controller = new Controller(players = Vector(player))
            controller.performAITurn(0, 0)
            controller.getCurrentOwner should be("Blue")
        }
        "agressive not buy, if already owned" in {
            val player = Player ("Red", position = 1)
            val player2 = Player("Blue", position = 1, strategy = Some(new AggressiveStrategy))
            val controller = new Controller(players = Vector(player, player2))
            controller.buyCurrentProperty()
            controller.getCurrentOwner should be("Red")
            controller.nextTurn()
            controller.performAITurn(0, 0)
            controller.getCurrentOwner should be("Red")
        }
        "agressive build a house" in {
            val player = Player("Blue", position = 1, strategy = Some(new AggressiveStrategy))
            val controller = new Controller(players = Vector(player))
            controller.buyCurrentProperty()
            controller.getCurrentOwner should be("Blue")
            controller.performAITurn(0, 0)
            controller.streets(0).buildings should be(1)
        }
        "agressive not build a house on a property not owned" in {
            val player = Player("Blue", position = 1, strategy = Some(new AggressiveStrategy))
            val controller = new Controller(players = Vector(player))
            controller.performAITurn()
            controller.streets(0).buildings should be(0)
        }
        "agressive build a hotel" in {
            val player = Player("Blue", position = 1, strategy = Some(new AggressiveStrategy))
            val controller = new Controller(players = Vector(player))
            controller.buyCurrentProperty()
            controller.getCurrentOwner should be("Blue")
            controller.buildHouse(1)
            controller.buildHouse(1)
            controller.buildHouse(1)
            controller.buildHouse(1)
            controller.performAITurn(0, 0)
            controller.streets(0).hotels should be(1)
        }
        "agressive not build a hotel on a property not owned" in {
            val player = Player("Blue", position = 1, strategy = Some(new AggressiveStrategy))
            val controller = new Controller(players = Vector(player))
            controller.performAITurn(0, 0)
            controller.streets(0).hotels should be(0)
        }
        "agressive out of jail" in {
            val player = Player("Blue", position = 10, inJail = true, strategy = Some(new AggressiveStrategy))
            val controller = new Controller(players = Vector(player))
            controller.performAITurn(2, 3)
            controller.players(0).inJail should be(false)
        }
    }
}
