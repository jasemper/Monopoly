package de.htwg.monopoly.model.strategy
import de.htwg.monopoly.controller.Controller
import de.htwg.monopoly.model.{Board, Player}
//import de.htwg.monopoly.model

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
  controller.tilt match {
    case controller.Tilt.Yes    => true
    case controller.Tilt.No     => false
    case controller.Tilt.Random => scala.util.Random.nextBoolean()
  }
}

}