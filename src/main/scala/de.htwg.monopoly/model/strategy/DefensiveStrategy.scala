package de.htwg.monopoly.model.strategy
import de.htwg.monopoly.controller.api._
import de.htwg.monopoly.model.{Board, Player}

import scala.compiletime.ops.boolean

class DefensiveStrategy extends PlayerStrategy {
  override def decideBuy(player: Player, controller: IController): Boolean = {
    if (player.money > 8000) {
      return true
    }
    false
  }

  override def decideBuildHouse(player: Player, controller: IController): Boolean = {
    var canBuild = false
    if (player.money > 5000) {
      val streets = controller.getGameState._2
      for (i <- streets.indices) {
        val street = streets(i)
        if (street.owner.contains(player.color)) {
          val fieldNumber = streetIndexToFieldNumber(i, controller)
          if (street.buildings < 4) {
            canBuild = true
          }
        }
      }
    }
    canBuild
  }
  override def decideBuildHotel(player: Player, controller: IController): Boolean = {
    var canBuild = false
    if (player.money > 5000) {
      val streets = controller.getGameState._2
      for (i <- streets.indices) {
        val street = streets(i)
        if (street.owner.contains(player.color)) {
          val fieldNumber = streetIndexToFieldNumber(i, controller)
          if (street.buildings == 4) {
            canBuild = true
          }
        }
      }
    }
    canBuild
  }

  override def decideJail(player: Player, controller: IController): Boolean = {
    player.inJail && player.money > 500
  }

  def streetIndexToFieldNumber(index: Int, controller: IController): Int = {
    val streets = controller.getGameState._2
    Board.indexWhere(_._2 == streets(index).name)
  }

}
