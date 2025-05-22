package de.htwg.monopoly

trait Command {
  def doStep(): Unit
  def undoStep(): Unit
}

class UndoManager {
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil

  def doStep(command: Command): Unit = {
    undoStack = command :: undoStack
    redoStack = Nil // Clear redo on new action
    command.doStep()
  }

  def undoStep(): Unit = {
    undoStack match {
      case Nil => // Nothing to undo
      case head :: stack =>
        head.undoStep()
        undoStack = stack
        redoStack = head :: redoStack
    }
  }

  def redoStep(): Unit = {
    redoStack match {
      case Nil => // Nothing to redo
      case head :: stack =>
        head.doStep()
        redoStack = stack
        undoStack = head :: undoStack
    }
  }

  def clear(): Unit = {
    undoStack = Nil
    redoStack = Nil
  }
}
