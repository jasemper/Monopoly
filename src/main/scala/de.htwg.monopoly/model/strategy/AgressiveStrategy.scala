package de.htwg.monopoly.model.strategy
import de.htwg.monopoly.controller.api._
import de.htwg.monopoly.model.{Board, Player}

class AggressiveStrategy extends PlayerStrategy {
  override def decideBuy(player: Player, controller: IController): Boolean = {
    true
  }

  override def decideBuildHouse(player: Player, controller: IController): Boolean = {
    var canBuild = false
    val streets = controller.getGameState._2
    for ((nr, fieldName) <- Board) {
      streets.find(s => s.name == fieldName && s.owner.contains(player.color)) match {
        case Some(street) if street.buildings < 4 =>
          canBuild = true
        case _ =>
      }
    }
    canBuild
  }
  override def decideBuildHotel(player: Player, controller: IController): Boolean = {
    var canBuild = false
    val streets = controller.getGameState._2
    for ((nr, fieldName) <- Board) {
      streets.find(s => s.name == fieldName && s.owner.contains(player.color)) match {
        case Some(street) if street.buildings >= 4 =>
          canBuild = true
        case _ =>
      }
    }
    canBuild
  }
  override def decideJail(player: Player, controller: IController): Boolean = {
    true
  }
}

