package de.htwg.monopoly

import scala.compiletime.ops.boolean

class DefensiveStrategy extends PlayerStrategy {
  override def decideBuy(player: Player, controller: Controller): Boolean = {
    if (player.money > 8000) {
      return true
    }
    false
  }

  override def decideBuildHouse(player: Player, controller: Controller): Boolean = {
    var canBuild = false
    if (player.money > 5000) {
      for (i <- controller.streets.indices) {
        val street = controller.streets(i)
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
  override def decideBuildHotel(player: Player, controller: Controller): Boolean = {
    var canBuild = false
    if (player.money > 5000) {
      for (i <- controller.streets.indices) {
        val street = controller.streets(i)
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

  override def decideJail(player: Player, controller: Controller): Boolean = {
    player.inJail && player.money > 500
  }

  def streetIndexToFieldNumber(index: Int, controller: Controller): Int = Board.indexWhere(_._2 == controller.streets(index).name)

}
