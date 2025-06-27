package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

import de.htwg.monopoly.model.data._
import de.htwg.monopoly.model.{Player, Street, Train, Utility}
import de.htwg.monopoly.model.GameSnapshot
import de.htwg.monopoly.model.GameStateEnum
import de.htwg.monopoly.controller.impl.state._
import de.htwg.monopoly.controller.GameState

class GameSnapshotSpec extends AnyWordSpec {

  "A GameSnapshot" should {

    "correctly store and return its fields" in {
        var players: Vector[Player] = InitPlayers
        var streets: Vector[Street] = InitStreets
        var trains: Vector[Train] = InitTrains
        var utilities: Vector[Utility] = InitUtilities
        val currentPlayerIndex = 1
        val gameState: GameStateEnum = GameStateEnum.WaitingForRoll

        val snapshot = GameSnapshot(players, streets, trains, utilities, currentPlayerIndex, gameState)

        snapshot.players shouldBe players
        snapshot.streets shouldBe streets
        snapshot.trains shouldBe trains
        snapshot.utilities shouldBe utilities
        snapshot.currentPlayerIndex should be(currentPlayerIndex)
        snapshot.gameState shouldBe gameState
    }

  }

}
