package de.htwg.monopoly

case class GameSnapshot(
  players: Vector[Player],
  streets: Vector[Street],
  trains: Vector[Railroad],
  utilities: Vector[Utility],
  currentPlayerIndex: Int
)
