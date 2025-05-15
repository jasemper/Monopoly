package de.htwg.monopoly

trait PlayerStrategy {
  def decideBuy(player: Player, controller: Controller): Unit
  def decideBuild(player: Player, controller: Controller): Unit
  def decideJail(player: Player, controller: Controller): Unit
}
