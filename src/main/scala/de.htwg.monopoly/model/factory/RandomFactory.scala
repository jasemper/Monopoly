package de.htwg.monopoly

class RandomFactory extends PlayerFactory {
  def createPlayer(color: String): Player = Player(color, 10000, 0, strategy = Some(new RandomStrategy))
}