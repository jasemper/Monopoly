package de.htwg.monopoly

class AggressiveStrategy extends PlayerStrategy {
  override def decideBuy(player: Player, controller: Controller): Unit = {
    controller.buyCurrentProperty()
  }

  override def decideBuild(player: Player, controller: Controller): Unit = {
    for ((nr, fieldName) <- Board) {
      controller.streets.find(s => s.name == fieldName && s.owner.contains(player.color)) match {
        case Some(street) if street.buildings < 4 =>
          controller.buildHouse(nr)
        case Some(street) if street.buildings == 4 && street.hotels < 1 =>
          controller.buildHotel(nr)
        case _ =>
      }
    }
  }
  override def decideJail(player: Player, controller: Controller): Unit = {
    controller.payJailFee()
  }
}

