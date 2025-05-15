package de.htwg.monopoly

import scala.util.Random

class RandomStrategy extends PlayerStrategy {
  override def decideBuy(player: Player, controller: Controller): Unit = {
    if (Random.nextBoolean()) {
      controller.buyCurrentProperty()
    }
  }

  override def decideBuild(player: Player, controller: Controller): Unit = {
    val ownedStreets = controller.streets.zipWithIndex.filter {
      case (street, _) => street.owner.contains(player.color)
    }

    if (ownedStreets.nonEmpty && Random.nextBoolean()) {
      val (street, index) = Random.shuffle(ownedStreets).head
      if (street.buildings < 4) {
        controller.buildHouse(index)
      } else if (street.buildings == 4 && street.hotels < 1) {
        controller.buildHotel(index)
      }
    }
  }
}
