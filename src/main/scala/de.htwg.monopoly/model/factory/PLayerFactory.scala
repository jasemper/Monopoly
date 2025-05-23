package de.htwg.monopoly

trait PlayerFactory {
  def apply(color: String): Player
}

object Human extends PlayerFactory {
  def apply(color: String): Player = Player(color, strategy = None)
}

object AggressiveAI extends PlayerFactory {
  def apply(color: String): Player = Player(color, strategy = Some(new AggressiveStrategy))
}

object DefensiveAI extends PlayerFactory {
  def apply(color: String): Player = Player(color, strategy = Some(new DefensiveStrategy))
}

object RandomAI extends PlayerFactory {
  def apply(color: String): Player = Player(color, strategy = Some(new RandomStrategy))
}
