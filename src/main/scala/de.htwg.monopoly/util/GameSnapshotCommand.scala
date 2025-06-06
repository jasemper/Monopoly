package de.htwg.monopoly.util

import de.htwg.monopoly.controller.Controller
import de.htwg.monopoly.model.GameSnapshot

class GameSnapshotCommand(controller: Controller, val before: GameSnapshot) extends Command {
  private var after: Option[GameSnapshot] = None

  override def doStep(): Unit = {
    after = Some(controller.createSnapshot())
  }

  override def undoStep(): Unit = {
    controller.restoreSnapshot(before)
  }

  override def redoStep(): Unit = {
    after match {
      case Some(snapshot) => controller.restoreSnapshot(snapshot)
      case None => ()
    }
  }
}
