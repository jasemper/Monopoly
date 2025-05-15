package de.htwg.monopoly

import scala.util.Random

class RandomStrategy extends PlayerStrategy {
  override def decideBuy(player: Player, controller: Controller): Unit = {
    if (getRandom(controller)) {
      controller.buyCurrentProperty()
    }
  }

  override def decideBuild(player: Player, controller: Controller): Unit = {
    val ownedStreets = controller.streets.zipWithIndex.filter {
      case (street, _) => street.owner.contains(player.color)
    }

    if (ownedStreets.nonEmpty && getRandom(controller)) {
      val (street, index) = Random.shuffle(ownedStreets).head
      if (street.buildings < 4) {
        controller.buildHouse(index)
      } else {
        controller.buildHotel(index)
      }
    }
  }

  override def decideJail(player: Player, controller: Controller): Unit = {
    if (player.inJail && getRandom(controller)) {
      controller.payJailFee()
    }
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