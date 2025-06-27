package de.htwg.monopoly.model.fileio

import de.htwg.monopoly.model.GameSnapshot

trait FileIOInterface {
  def save(game: GameSnapshot, filename: String): Unit
  def load(filename: String): GameSnapshot
}