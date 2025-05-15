package de.htwg.monopoly

class AggressiveStrategy extends PlayerStrategy {
  override def decideMove(player: Player, controller: Controller): Unit = {
    // Not used here
  }

  override def decideBuy(player: Player, controller: Controller): Unit = {
    controller.buyCurrentProperty()
  }

  override def decideBuild(player: Player, controller: Controller): Unit = {
    // Try to build a house on every street the player owns
    for ((nr, fieldName) <- Board) {
      controller.streets.find(s => s.name == fieldName && s.owner.contains(player.color)) match {
        case Some(street) if street.buildings < 4 =>
          controller.buildHouse(nr)
        case Some(street) if street.buildings == 4 && street.hotels < 1 =>
          controller.buildHotel(nr)
        case _ => // do nothing
      }
    }
  }
}

