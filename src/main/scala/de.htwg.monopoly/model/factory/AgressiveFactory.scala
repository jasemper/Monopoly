package de.htwg.monopoly

class AggressiveFactory extends PlayerFactory {
  def createPlayer(color: String): Player = Player(color, 10000, 0, strategy = Some(new AggressiveStrategy))
}
