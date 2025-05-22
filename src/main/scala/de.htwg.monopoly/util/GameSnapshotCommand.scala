package de.htwg.monopoly

import de.htwg.monopoly.{Controller}
import de.htwg.monopoly.GameSnapshot

class GameSnapshotCommand(controller: Controller, val before: GameSnapshot) extends Command {
  private var after: Option[GameSnapshot] = None

  override def doStep(): Unit = {
    after = Some(controller.createSnapshot())
  }

  override def undoStep(): Unit = {
    controller.restoreSnapshot(before)
  }

  def redoStep(): Unit = {
    after match {
      case Some(snapshot) => controller.restoreSnapshot(snapshot)
      case None => ()
    }
  }
}
