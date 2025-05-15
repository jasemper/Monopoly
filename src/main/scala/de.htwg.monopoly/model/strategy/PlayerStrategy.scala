package de.htwg.monopoly

trait PlayerStrategy {
  def decideMove(player: Player, controller: Controller): Unit
  def decideBuy(player: Player, controller: Controller): Unit
  def decideBuild(player: Player, controller: Controller): Unit
}
