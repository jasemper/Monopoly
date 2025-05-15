package de.htwg.monopoly

import de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class defensiveSpec extends AnyWordSpec {
    "defensiveAI" should {
        "defensive move the AI" in {
            val player = Player("Blue", position = 0, strategy = Some(new DefensiveStrategy))
            val controller = new Controller(players = Vector(player))
            controller.performAITurn()
            controller.players(0).position should be > 0
        }
        "defensive buy a property" in {
            val player = Player("Blue", position = 1, strategy = Some(new DefensiveStrategy))
            val controller = new Controller(players = Vector(player))
            controller.performAITurn(0, 0)
            controller.getCurrentOwner should be("Blue")
            }
        "defensive not buy, if already owned" in {
            val player = Player ("Red", position = 1)
            val player2 = Player("Blue", position = 1, strategy = Some(new DefensiveStrategy))
            val controller = new Controller(players = Vector(player, player2))
            controller.buyCurrentProperty()
            controller.getCurrentOwner should be("Red")
            controller.nextTurn()
            controller.performAITurn(0, 0)
            controller.getCurrentOwner should be("Red")
        }
        "defensive not buy, if not enough money" in {
            val player = Player("Blue", position = 1, money = 2000, strategy = Some(new DefensiveStrategy))
            val controller = new Controller(players = Vector(player))
            controller.performAITurn(0, 0)
            controller.getCurrentOwner should be("")
        }
        "defensive build a house" in {
            val player = Player("Blue", position = 1, strategy = Some(new DefensiveStrategy))
            val controller = new Controller(players = Vector(player))
            controller.buyCurrentProperty()
            controller.getCurrentOwner should be("Blue")
            controller.performAITurn(0, 0)
            controller.streets(0).buildings should be(1)
        }
        "defensive not build a house on a property not owned" in {
            val player = Player("Blue", position = 1, strategy = Some(new DefensiveStrategy))
            val controller = new Controller(players = Vector(player))
            controller.getCurrentOwner should be("")
            controller.performAITurn(0, 0)
            controller.streets(0).buildings should be(0)
        }
        "defensive not build, if not enough money" in {
            val player = Player("Blue", position = 1, money = 2000, strategy = Some(new DefensiveStrategy))
            val controller = new Controller(players = Vector(player))
            controller.buyCurrentProperty()
            controller.getCurrentOwner should be("Blue")
            controller.performAITurn(0, 0)
            controller.streets(0).buildings should be(0)
            }
        "defensive build a hotel" in {
            val player = Player("Blue", money = 100000, position = 1, strategy = Some(new DefensiveStrategy))
            val controller = new Controller(players = Vector(player))
            controller.buyCurrentProperty()
            controller.getCurrentOwner should be("Blue")
            controller.buildHouse(1)
            controller.buildHouse(1)
            controller.buildHouse(1)
            controller.buildHouse(1)
            controller.streets(0).buildings should be(4)
            controller.performAITurn(0, 0)
            controller.streets(0).hotels should be(1)
        }
        "defensive not build a hotel on a property not owned" in {
            val player = Player("Blue", position = 1, strategy = Some(new DefensiveStrategy))
            val controller = new Controller(players = Vector(player))
            controller.performAITurn(0, 0)
            controller.streets(0).hotels should be(0)
        }
        "defensive out of jail" in {
            val player = Player("Blue", position = 10, inJail = true, strategy = Some(new DefensiveStrategy))
            val controller = new Controller(players = Vector(player))
            controller.performAITurn(2, 3)
            controller.players(0).inJail should be(false)
        }
        "defensive not out of jail, if not enough money" in {
            val player = Player("Blue", position = 10, inJail = true, money = 200, strategy = Some(new DefensiveStrategy))
            val controller = new Controller(players = Vector(player))
            controller.performAITurn(2, 3)
            controller.players(0).inJail should be(true)
        }
    }
}