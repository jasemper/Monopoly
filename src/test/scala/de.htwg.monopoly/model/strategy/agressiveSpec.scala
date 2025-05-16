package de.htwg.monopoly

import de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class agressiveSpec extends AnyWordSpec {
    "agressiveAI" should {
        "buy a property" in {
            val strategy = new AggressiveStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            strategy.decideBuy(player, controller) shouldBe true
        }
        "build a house" in {
            val strategy = new AggressiveStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.buyCurrentProperty()
            controller.streets(0).buildings should be(0)
            strategy.decideBuildHouse(player, controller) shouldBe true
        }
        "not build a house on a property not owned" in {
            val strategy = new AggressiveStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.streets(0).buildings should be(0)
            strategy.decideBuildHouse(player, controller) shouldBe false
        }
        "build a hotel" in {
            val strategy = new AggressiveStrategy
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
            val strategy = new AggressiveStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.streets(0).buildings should be(0)
            strategy.decideBuildHotel(player, controller) shouldBe false
        }
        "not build a hotel on a property with less than 4 houses" in {
            val strategy = new AggressiveStrategy
            val player = Player("Red", position = 1, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            controller.buyCurrentProperty()
            controller.streets(0).buildings should be(0)
            controller.buildHouse(1)
            controller.buildHouse(1)
            controller.buildHouse(1)
            strategy.decideBuildHotel(player, controller) shouldBe false
        }
        "out of jail" in {
            val strategy = new AggressiveStrategy
            val player = Player("Red", position = 10, inJail = true, strategy = Some(strategy))
            val controller = new Controller(players = Vector(player))
            strategy.decideJail(player, controller) shouldBe true
        }
    }
}
