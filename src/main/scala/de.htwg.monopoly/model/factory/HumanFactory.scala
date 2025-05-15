package de.htwg.monopoly

class HumanFactory extends PlayerFactory {
  def createPlayer(color: String): Player = Player(color, 10000, 0, strategy = None)
}
