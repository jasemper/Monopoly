package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

import de.htwg.monopoly.model.GameSnapshot
import de.htwg.monopoly.model.data._
import de.htwg.monopoly.model.fileio.FileIOXmlImpl

import java.nio.file.Files

class FileIOXmlImplSpec extends AnyWordSpec {

  "FileIOXmlImpl" should {

    "correctly save and load a game snapshot" in {
      val fileIO = new FileIOXmlImpl()

      // Prepare a sample GameSnapshot
      val snapshot = GameSnapshot(
        players = InitPlayers,
        streets = InitStreets,
        trains = InitTrains,
        utilities = InitUtilities,
        currentPlayerIndex = 2,
        gameState = de.htwg.monopoly.model.GameStateEnum.WaitingForRoll
      )

      // Create a temporary file
      val tempFile = Files.createTempFile("monopoly_xml_test", ".xml").toFile
      val filename = tempFile.getAbsolutePath

      try {
        // Save the snapshot to XML
        fileIO.save(snapshot, filename)

        // Load the snapshot back
        val loadedSnapshot = fileIO.load(filename)

        // Assertions to verify that saved and loaded data are the same
        loadedSnapshot.players shouldBe snapshot.players
        loadedSnapshot.streets shouldBe snapshot.streets
        loadedSnapshot.trains shouldBe snapshot.trains
        loadedSnapshot.utilities shouldBe snapshot.utilities
        loadedSnapshot.currentPlayerIndex shouldBe snapshot.currentPlayerIndex
        loadedSnapshot.gameState shouldBe snapshot.gameState

      } finally {
        tempFile.delete()
      }
    }
  }
}
