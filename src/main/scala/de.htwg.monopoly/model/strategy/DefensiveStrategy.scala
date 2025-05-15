package de.htwg.monopoly

class DefensiveStrategy extends PlayerStrategy {
  override def decideBuy(player: Player, controller: Controller): Unit = {
    if (player.money > 8000) {
      controller.buyCurrentProperty()
    }
  }

  override def decideBuild(player: Player, controller: Controller): Unit = {
  if (player.money > 5000) {
    for (i <- controller.streets.indices) {
      val street = controller.streets(i)
      if (street.owner.contains(player.color)) {
        val fieldNumber = streetIndexToFieldNumber(i, controller)
        if (street.buildings < 4) {
          controller.buildHouse(fieldNumber)
        } else {
          controller.buildHotel(fieldNumber)
        }
      }
    }
  }
}

  override def decideJail(player: Player, controller: Controller): Unit = {
    if (player.inJail && player.money > 500) {
      controller.payJailFee()
    }
  }

  def streetIndexToFieldNumber(index: Int, controller: Controller): Int = {
    Board.indexWhere(_._2 == controller.streets(index).name)
  }

}
