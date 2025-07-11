package de.htwg.monopoly.model.strategy
import de.htwg.monopoly.controller.IController
import de.htwg.monopoly.model.{Player}
import de.htwg.monopoly.model.data.Board

import scala.util.Random

class RandomStrategy extends PlayerStrategy {
  override def decideBuy(player: Player, controller: IController): Boolean = {
    if (getRandom(controller)) {
      return true
    }
    false
  }

  override def decideBuildHouse(player: Player, controller: IController): Boolean = {
    val streets = controller.getGameState._2
    val ownedStreets = streets.zipWithIndex.filter {
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
  override def decideBuildHotel(player: Player, controller: IController): Boolean = {
    val streets = controller.getGameState._2
    val ownedStreets = streets.zipWithIndex.filter {
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

  override def decideJail(player: Player, controller: IController): Boolean = {
    if (player.inJail && getRandom(controller)) {
      return true
    }
    false
  }

  def getRandom(controller: IController): Boolean = {
  controller.getTilt match {
    case IController.Tilt.Yes    => true
    case IController.Tilt.No     => false
    case IController.Tilt.Random => scala.util.Random.nextBoolean()
  }
}

}