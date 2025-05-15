package de.htwg.monopoly

class DefensiveStrategy extends PlayerStrategy {
  override def decideMove(player: Player, controller: Controller): Unit = {
    // Not needed
  }

  override def decideBuy(player: Player, controller: Controller): Unit = {
    if (player.money > 1000) {
      controller.buyCurrentProperty()
    }
  }

  override def decideBuild(player: Player, controller: Controller): Unit = {
    if (player.money > 1000) {
      controller.streets.zipWithIndex.foreach {
        case (street, index) =>
          if (street.owner.contains(player.color)) {
            if (street.buildings < 4) {
              controller.buildHouse(index)
            } else if (street.buildings == 4 && street.hotels < 1) {
              controller.buildHotel(index)
            }
          }
      }
    }
  }
}
