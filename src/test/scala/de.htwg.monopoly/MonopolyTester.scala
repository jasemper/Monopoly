package de.htwg.monopoly

import de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import java.io.{ByteArrayOutputStream, PrintStream}
import java.io.ByteArrayInputStream

class MonopolyTester extends AnyWordSpec {

  "TUIGame (main)" should {
    "start the game and call devPlay without exceptions" in {
      noException should be thrownBy {
        val simulatedInput = new ByteArrayInputStream("exit\n".getBytes)
        Console.withIn(simulatedInput) {
          TUIGame.startGame()
        }
      }
    }
  }

  /*
  "Monopoly" should {
    "play itself without exceptions" in {
      val controller = new Controller()
      while (controller.getWinnerIfAny == None) {
        val rolled = controller.rollDice()
        if (controller.currentPlayer.inJail) {
          if (controller.currentPlayer.pasch > 0) {
            controller.moveCurrentPlayer(rolled)
            controller.players = controller.players.updated(controller.currentPlayerIndex, controller.currentPlayer.copy(inJail = false))
          }
        } else {
          controller.moveCurrentPlayer(rolled)
          val fieldNr = controller.currentPlayer.position
          if (controller.getCurrentOwner == "") {
            controller.buyCurrentProperty()
          }
        }
      }
    }
  }*/
}