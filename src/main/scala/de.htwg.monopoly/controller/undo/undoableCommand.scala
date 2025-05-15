package de.htwg.monopoly

trait UndoableCommand {
  def doStep(): Unit
  def undoStep(): Unit
  def redoStep(): Unit
}
