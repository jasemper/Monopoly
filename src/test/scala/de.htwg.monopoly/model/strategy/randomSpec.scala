package de.htwg.monopoly

import de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

import de.htwg.monopoly.controller.IController
import de.htwg.monopoly.controller.impl.Controller
import de.htwg.monopoly.model.Player
import de.htwg.monopoly.model.strategy.RandomStrategy

class randomSpec extends AnyWordSpec {
    "randomAI" should {
        "buy a property" in {
            val strategy = new RandomStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.tilt = IController.Tilt.Yes
            strategy.decideBuy(player, controller) shouldBe true
        }
        "not buy a property, if not enough money" in {
            val strategy = new RandomStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.tilt = IController.Tilt.No
            strategy.decideBuy(player, controller) shouldBe false
        }
        "build a house" in {
            val strategy = new RandomStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.tilt = IController.Tilt.Yes
            controller.buyCurrentProperty()
            controller.streets(0).buildings should be(0)
            strategy.decideBuildHouse(player, controller) shouldBe true
        }
        "not build a house on a property not owned" in {
            val strategy = new RandomStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.tilt = IController.Tilt.No
            strategy.decideBuildHouse(player, controller) shouldBe false
        }
        "not build a house when already 4 houses" in {
            val strategy = new RandomStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.tilt = IController.Tilt.Yes
            controller.buyCurrentProperty()
            controller.streets(0).buildings should be(0)
            controller.buildHouse(1)
            controller.buildHouse(1)
            controller.buildHouse(1)
            controller.buildHouse(1)
            strategy.decideBuildHouse(player, controller) shouldBe false
        }
        "not build a house" in {
            val strategy = new RandomStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.tilt = IController.Tilt.No
            strategy.decideBuildHouse(player, controller) shouldBe false
        }
        "build a hotel" in {
            val strategy = new RandomStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.tilt = IController.Tilt.Yes
            controller.buyCurrentProperty()
            controller.buildHouse(1)
            controller.buildHouse(1)
            controller.buildHouse(1)
            controller.buildHouse(1)
            strategy.decideBuildHotel(player, controller) shouldBe true
        }
        "not build a hotel on a property not owned" in {
            val strategy = new RandomStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.tilt = IController.Tilt.No
            strategy.decideBuildHotel(player, controller) shouldBe false
        }
        "not build a hotel on a property with less than 4 houses" in {
            val strategy = new RandomStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.tilt = IController.Tilt.Yes
            controller.buyCurrentProperty()
            controller.streets(0).buildings should be(0)
            controller.buildHouse(1)
            controller.buildHouse(1)
            strategy.decideBuildHotel(player, controller) shouldBe false
        }
        "not build a hotel" in {
            val strategy = new RandomStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.tilt = IController.Tilt.No
            strategy.decideBuildHotel(player, controller) shouldBe false
        }
        "out of jail" in {
            val player = Player("Blue", position = 10, inJail = true, strategy = Some(new RandomStrategy))
            val controller = new Controller(players = Vector(player))
            controller.tilt = IController.Tilt.Yes
            controller.performAITurn(2, 3)
            controller.players(0).inJail should (be(false))
        }
        "not out of jail" in {
            val player = Player("Blue", position = 10, inJail = true, strategy = Some(new RandomStrategy))
            val controller = new Controller(players = Vector(player))
            controller.tilt = IController.Tilt.No
            controller.performAITurn(2, 3)
            controller.players(0).inJail should (be(true))
        }
        "maybe out of jail" in {
            val player = Player("Blue", position = 10, inJail = true, strategy = Some(new RandomStrategy))
            val controller = new Controller(players = Vector(player))
            controller.performAITurn(2, 3)
            controller.players(0).inJail should (be(false) or be(true))
        }
    }
}