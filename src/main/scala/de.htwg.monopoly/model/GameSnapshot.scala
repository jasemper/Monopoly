package de.htwg.monopoly.model
import de.htwg.monopoly.model.GameStateEnum
import de.htwg.monopoly.model.{Player, Street, Train, Utility}

import scala.annotation.transparentTrait

case class GameSnapshot(
  players: Vector[Player],
  streets: Vector[Street],
  trains: Vector[Train],
  utilities: Vector[Utility],
  currentPlayerIndex: Int,
  gameState: GameStateEnum
)
