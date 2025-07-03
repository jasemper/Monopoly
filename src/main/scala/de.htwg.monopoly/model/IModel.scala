package de.htwg.monopoly.model

import de.htwg.monopoly.model.fileio.FileIOInterface
import de.htwg.monopoly.model.strategy.PlayerStrategy
import de.htwg.monopoly.model.factory.PlayerFactory
import de.htwg.monopoly.model._

trait IModel {
  // ===== File I/O =====
  def save(game: GameSnapshot, filename: String): Unit
  def load(filename: String): GameSnapshot

  // ===== AI Decision Delegates =====
  def decideBuy(player: Player): Boolean
  def decideBuildHouse(player: Player): Boolean
  def decideBuildHotel(player: Player): Boolean
  def decideJail(player: Player): Boolean

  // ===== Player Factories =====
  def humanFactory: PlayerFactory
  def aggressiveAIFactory: PlayerFactory
  def defensiveAIFactory: PlayerFactory
  def randomAIFactory: PlayerFactory

  // ===== Current Game State =====
  def gameSnapshot: GameSnapshot
  def gameState: GameStateEnum
  def currentPlayer: Player

  // ===== Initial Game Data =====
  def InitPlayers: Vector[Player]
  def InitStreets: Vector[Street]
  def InitTrains: Vector[Train]
  def InitUtilities: Vector[Utility]
  def Events: Vector[Event]
  def Board: Vector[(Int, String)]
}
