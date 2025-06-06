package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class GameSnapshotSpec extends AnyWordSpec {

  "A GameSnapshot" should {

    "correctly store and return its fields" in {
        var players: Vector[Player] = InitPlayers
        var streets: Vector[Street] = InitStreets
        var trains: Vector[Train] = InitTrains
        var utilities: Vector[Utility] = InitUtilities
        val currentPlayerIndex = 1
        val gameState: GameState = new WaitingForRoll

        val snapshot = GameSnapshot(players, streets, trains, utilities, currentPlayerIndex, gameState)

        snapshot.players shouldBe players
        snapshot.streets shouldBe streets
        snapshot.trains shouldBe trains
        snapshot.utilities shouldBe utilities
        snapshot.currentPlayerIndex shouldBe currentPlayerIndex
        snapshot.gameState shouldBe gameState
    }

  }

}
