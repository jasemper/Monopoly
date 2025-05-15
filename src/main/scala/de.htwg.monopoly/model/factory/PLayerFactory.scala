package de.htwg.monopoly

trait PlayerFactory {
  def createPlayer(color: String): Player
}
