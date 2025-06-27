package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

import de.htwg.monopoly.controller.impl.Controller
import de.htwg.monopoly.model.data._
import de.htwg.monopoly.model.{Player, Street, Train, Utility}

import java.nio.file.Files

class FileIOJsonImplSpec extends AnyWordSpec {

  "Controller save/load" should {

    "correctly save and load game state" in {
      val controller = new Controller()
      val updatedPlayers = controller.players.updated(0, controller.players(0).copy(money = 1200, position = 5))
      val updatedStreets = controller.streets.updated(0, controller.streets(0).copy(owner = Some("red")))
      controller.players = updatedPlayers
      controller.streets = updatedStreets
      controller.currentPlayerIndex = 0

      val tempFile = Files.createTempFile("monopoly_test", ".json").toFile
      val filename = tempFile.getAbsolutePath
      //println(s"Saved JSON file at: $filename")

      try {
        controller.saveGame(filename)

        val loadedController = new Controller()
        loadedController.loadGame(filename)

        loadedController.players shouldBe controller.players
        loadedController.streets shouldBe controller.streets
        loadedController.trains shouldBe controller.trains
        loadedController.utilities shouldBe controller.utilities
        loadedController.currentPlayerIndex shouldBe controller.currentPlayerIndex
        loadedController.state.getClass shouldBe controller.state.getClass
      } finally {
        tempFile.delete()
      }
    }
  }
}
