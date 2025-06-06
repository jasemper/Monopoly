package de.htwg.monopoly.model.factory
import de.htwg.monopoly.model.Player
import de.htwg.monopoly.model.strategy.{AggressiveStrategy, DefensiveStrategy, PlayerStrategy, RandomStrategy}

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
