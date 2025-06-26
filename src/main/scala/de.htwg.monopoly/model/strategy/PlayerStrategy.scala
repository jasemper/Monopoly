package de.htwg.monopoly.model.strategy
import de.htwg.monopoly.controller.api.IController
import de.htwg.monopoly.model.Player

trait PlayerStrategy {
  def decideBuy(player: Player, controller: IController): Boolean
  def decideBuildHouse(player: Player, controller: IController): Boolean
  def decideBuildHotel(player: Player, controller: IController): Boolean
  def decideJail(player: Player, controller: IController): Boolean
}
