package de.htwg.monopoly

trait PlayerStrategy {
  def decideBuy(player: Player, controller: Controller): Boolean
  def decideBuildHouse(player: Player, controller: Controller): Boolean
  def decideBuildHotel(player: Player, controller: Controller): Boolean
  def decideJail(player: Player, controller: Controller): Boolean
}
