package de.htwg.monopoly

import de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

import de.htwg.monopoly.controller.impl.Controller
import de.htwg.monopoly.model.Player
import de.htwg.monopoly.model.strategy.DefensiveStrategy

class defensiveSpec extends AnyWordSpec {
    "defensiveAI" should {
        "buy a property" in {
            val strategy = new DefensiveStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            strategy.decideBuy(player, controller) shouldBe true
        }
        "not buy a property, if not enough money" in {
            val strategy = new DefensiveStrategy
            val player = Player("Red", position = 1, money = 2000, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            strategy.decideBuy(player, controller) shouldBe false
        }
        "build a house" in {
            val strategy = new DefensiveStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.buyCurrentProperty()
            controller.streets(0).buildings should be(0)
            strategy.decideBuildHouse(player, controller) shouldBe true
        }
        "not build a house on a property not owned" in {
            val strategy = new DefensiveStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.streets(0).buildings should be(0)
            strategy.decideBuildHouse(player, controller) shouldBe false
        }
        "not build a house with less than 5000 money" in {
            val strategy = new DefensiveStrategy
            val player = Player("Red", position = 1, money = 2000, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.buyCurrentProperty()
            controller.streets(0).buildings should be(0)
            strategy.decideBuildHouse(player, controller) shouldBe false
        }
        "not build a house with 4 houses already" in {
            val strategy = new DefensiveStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.buyCurrentProperty()
            controller.streets(0).buildings should be(0)
            controller.buildHouse(1)
            controller.buildHouse(1)
            controller.buildHouse(1)
            controller.buildHouse(1)
            strategy.decideBuildHouse(player, controller) shouldBe false
        }
        "build a hotel" in {
            val strategy = new DefensiveStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.buyCurrentProperty()
            controller.streets(0).buildings should be(0)
            controller.buildHouse(1)
            controller.buildHouse(1)
            controller.buildHouse(1)
            controller.buildHouse(1)
            strategy.decideBuildHotel(player, controller) shouldBe true
        }
        "not build a hotel on a property not owned" in {
            val strategy = new DefensiveStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.streets(0).buildings should be(0)
            strategy.decideBuildHotel(player, controller) shouldBe false
        }
        "not build a hotel on a property with less than 4 houses" in {
            val strategy = new DefensiveStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.buyCurrentProperty()
            controller.streets(0).buildings should be(0)
            controller.buildHouse(1)
            controller.buildHouse(1)
            controller.buildHouse(1)
            strategy.decideBuildHotel(player, controller) shouldBe false
        }
        "not build a hotel with less than 5000 money" in {
            val strategy = new DefensiveStrategy
            val player = Player("Red", position = 1, money = 2000, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.buyCurrentProperty()
            controller.streets(0).buildings should be(0)
            controller.buildHouse(1)
            controller.buildHouse(1)
            controller.buildHouse(1)
            controller.buildHouse(1)
            strategy.decideBuildHotel(player, controller) shouldBe false
        }
        "out of jail" in {
            val strategy = new DefensiveStrategy
            val player = Player("Red", position = 10, inJail = true, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            strategy.decideJail(player, controller) shouldBe true
        }
        "not out of jail, if not enough money" in {
            val strategy = new DefensiveStrategy
            val player = Player("Red", position = 10, inJail = true, money = 200, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            strategy.decideJail(player, controller) shouldBe false
        }
    }
}