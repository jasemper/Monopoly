package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class GameSnapshotCommandSpec extends AnyWordSpec {
    "redoStep" should {
        "restore the game state to the snapshot" in {
            val controller = new Controller()
            val initialSnapshot = controller.createSnapshot()
            val command = new GameSnapshotCommand(controller, initialSnapshot)

            // Simulate some changes in the game state
            controller.state.rollDice(controller)
            controller.state.move(controller, 3)

            // Undo the changes
            command.undoStep()

            // Verify that the game state is restored to the snapshot
            controller.createSnapshot() shouldEqual initialSnapshot
        }
    }
    "undoStep" should {
        "restore the game state to the previous state" in {
            val controller = new Controller()
            val initialSnapshot = controller.createSnapshot()
            val command = new GameSnapshotCommand(controller, initialSnapshot)

            // Simulate some changes in the game state
            controller.state.rollDice(controller)
            controller.state.move(controller, 3)

            // Undo the changes
            command.redoStep()

            // Verify that the game state is restored to the snapshot
            controller.createSnapshot() should not equal initialSnapshot
        }
    }
    "doStep" should {
        "create a new snapshot of the game state" in {
            val controller = new Controller()
            val initialSnapshot = controller.createSnapshot()
            val command = new GameSnapshotCommand(controller, initialSnapshot)

            // Simulate some changes in the game state
            controller.state.rollDice(controller)
            controller.state.move(controller, 3)

            // Create a new snapshot
            command.doStep()

            // Verify that the new snapshot is different from the initial snapshot
            controller.getGameState should not equal initialSnapshot
        }
    }
}