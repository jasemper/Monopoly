package de.htwg.monopoly.model.strategy
import de.htwg.monopoly.controller.Controller
import de.htwg.monopoly.model.Player

trait PlayerStrategy {
  def decideBuy(player: Player, controller: Controller): Boolean
  def decideBuildHouse(player: Player, controller: Controller): Boolean
  def decideBuildHotel(player: Player, controller: Controller): Boolean
  def decideJail(player: Player, controller: Controller): Boolean
}
