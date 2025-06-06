package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

import java.io.ByteArrayInputStream

class MonopolyTester extends AnyWordSpec {

  "MonopolyGame (main)" should {
    "start the game and exit gracefully on 'exit' input without exceptions" in {
      noException should be thrownBy {
        val simulatedInput = new ByteArrayInputStream("exit\n".getBytes)
        Console.withIn(simulatedInput) {
          MonopolyGame.startGame()
        }
      }
    }
  }
}
