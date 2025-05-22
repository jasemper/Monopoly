package de.htwg.monopoly

import scala.annotation.transparentTrait

case class GameSnapshot(
  players: Vector[Player],
  streets: Vector[Street],
  trains: Vector[Train],
  utilities: Vector[Utility],
  currentPlayerIndex: Int,
  gameState: GameState
)
