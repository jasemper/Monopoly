package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

import de.htwg.monopoly.controller.impl.Controller
import de.htwg.monopoly.model.GameSnapshot
import de.htwg.monopoly.util.GameSnapshotCommand

class GameSnapshotCommandSpec extends AnyWordSpec {
    "redoStep" should {
        "restore the game state to the snapshot" in {
            val controller = new Controller()
            val initialSnapshot = controller.createSnapshot()
            val command = new GameSnapshotCommand(controller, initialSnapshot)


            controller.state.rollDice(controller)
            controller.state.move(controller, 3)


            command.undoStep()


            controller.createSnapshot() shouldEqual initialSnapshot
        }
    }
    "undoStep" should {
        "restore the game state to the previous state" in {
            val controller = new Controller()
            val initialSnapshot = controller.createSnapshot()
            val command = new GameSnapshotCommand(controller, initialSnapshot)


            controller.state.rollDice(controller)
            controller.state.move(controller, 3)


            command.redoStep()


            controller.createSnapshot() should not equal initialSnapshot
        }
    }
    "doStep" should {
        "create a new snapshot of the game state" in {
            val controller = new Controller()
            val initialSnapshot = controller.createSnapshot()
            val command = new GameSnapshotCommand(controller, initialSnapshot)


            controller.state.rollDice(controller)
            controller.state.move(controller, 3)


            command.doStep()


            controller.getGameState should not equal initialSnapshot
        }
    }
}