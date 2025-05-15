package de.htwg.monopoly

class TurnCommand(controller: Controller) extends UndoableCommand {
  private val snapshotBefore: GameSnapshot = controller.snapshot()

  override def doStep(): Unit = {
    controller.performAITurn() // or controller.playTurn(), if human
    controller.nextTurn()
  }

  override def undoStep(): Unit = controller.restore(snapshotBefore)

  override def redoStep(): Unit = doStep()
}
