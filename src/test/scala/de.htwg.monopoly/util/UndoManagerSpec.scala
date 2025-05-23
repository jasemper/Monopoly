package de.htwg.monopoly


import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class UndoManagerSpec extends AnyWordSpec {
    "undoStep" should {
      "do nothing on empty" in {
        val undoManager = new UndoManager
        undoManager.undoStep()
        undoManager.undoStack shouldBe empty
      }
    }
    "redoStep" should {
      "do nothing on empty" in {
        val undoManager = new UndoManager
        undoManager.redoStep()
        undoManager.redoStack shouldBe empty
      }
    }
}