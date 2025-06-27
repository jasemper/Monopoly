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

      val snapshot = GameSnapshot(
        players = InitPlayers,
        streets = InitStreets,
        trains = InitTrains,
        utilities = InitUtilities,
        currentPlayerIndex = 2,
        gameState = de.htwg.monopoly.model.GameStateEnum.WaitingForRoll
      )

      val tempFile = Files.createTempFile("monopoly_xml_test", ".xml").toFile
      val filename = tempFile.getAbsolutePath

      try {
        fileIO.save(snapshot, filename)

        val loadedSnapshot = fileIO.load(filename)

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
