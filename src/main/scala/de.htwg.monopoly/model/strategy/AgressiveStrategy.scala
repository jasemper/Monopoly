package de.htwg.monopoly

class AggressiveStrategy extends PlayerStrategy {
  override def decideBuy(player: Player, controller: Controller): Boolean = {
    true
  }

  override def decideBuildHouse(player: Player, controller: Controller): Boolean = {
    var canBuild = false
    for ((nr, fieldName) <- Board) {
      controller.streets.find(s => s.name == fieldName && s.owner.contains(player.color)) match {
        case Some(street) if street.buildings < 4 =>
          canBuild = true
        case _ =>
      }
    }
    canBuild
  }
  override def decideBuildHotel(player: Player, controller: Controller): Boolean = {
    var canBuild = false
    for ((nr, fieldName) <- Board) {
      controller.streets.find(s => s.name == fieldName && s.owner.contains(player.color)) match {
        case Some(street) if street.buildings >= 4 =>
          canBuild = true
        case _ =>
      }
    }
    canBuild
  }
  override def decideJail(player: Player, controller: Controller): Boolean = {
    true
  }
}

