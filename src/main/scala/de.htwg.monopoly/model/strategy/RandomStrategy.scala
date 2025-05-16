package de.htwg.monopoly

import scala.util.Random

class RandomStrategy extends PlayerStrategy {
  override def decideBuy(player: Player, controller: Controller): Boolean = {
    if (getRandom(controller)) {
      return true
    }
    false
  }

  override def decideBuildHouse(player: Player, controller: Controller): Boolean = {
    val ownedStreets = controller.streets.zipWithIndex.filter {
      case (street, _) => street.owner.contains(player.color)
    }

    if (ownedStreets.nonEmpty && getRandom(controller)) {
      val (street, index) = Random.shuffle(ownedStreets).head
      if (street.buildings < 4) {
        return true
      }
    }
    false
  }
  override def decideBuildHotel(player: Player, controller: Controller): Boolean = {
    val ownedStreets = controller.streets.zipWithIndex.filter {
      case (street, _) => street.owner.contains(player.color)
    }

    if (ownedStreets.nonEmpty && getRandom(controller)) {
      val (street, index) = Random.shuffle(ownedStreets).head
      if (street.buildings >= 4) {
        return true
      }
    }
    false
  }

  override def decideJail(player: Player, controller: Controller): Boolean = {
    if (player.inJail && getRandom(controller)) {
      return true
    }
    false
  }

  def getRandom(controller: Controller): Boolean = {
    var random = Random.nextBoolean()
    if (controller.tilt == 1) {
      random = true
    } else if (controller.tilt == 0) {
      random = false
    }
    random
  }
}