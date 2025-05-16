package de.htwg.monopoly

trait PlayerFactory {
  def apply(color: String): Player
}

class Human extends PlayerFactory {
  def apply(color: String): Player = Player(color, strategy = None)
}

class AggressiveAI extends PlayerFactory {
  def apply(color: String): Player = Player(color, strategy = Some(new AggressiveStrategy))
}

class DefensiveAI extends PlayerFactory {
  def apply(color: String): Player = Player(color, strategy = Some(new DefensiveStrategy))
}

class RandomAI extends PlayerFactory {
  def apply(color: String): Player = Player(color, strategy = Some(new RandomStrategy))
}
