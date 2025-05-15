package de.htwg.monopoly

class UndoManager {
  private var undoStack: List[UndoableCommand] = Nil
  private var redoStack: List[UndoableCommand] = Nil

  def doStep(command: UndoableCommand): Unit = {
    command.doStep()
    undoStack = command :: undoStack
    redoStack = Nil
  }

  def undoStep(): Option[Unit] = undoStack match {
    case command :: rest =>
      command.undoStep()
      undoStack = rest
      redoStack = command :: redoStack
      Some(())
    case Nil => None
  }

  def redoStep(): Option[Unit] = redoStack match {
    case command :: rest =>
      command.redoStep()
      redoStack = rest
      undoStack = command :: undoStack
      Some(())
    case Nil => None
  }
}
