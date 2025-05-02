package de.htwg.monopoly

import de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import java.io.{ByteArrayOutputStream, PrintStream}
import java.io.ByteArrayInputStream

class MonopolyTester extends AnyWordSpec {

  "TUIGame" should {
    "start the game and call devPlay without exceptions" in {
      noException should be thrownBy {
        val simulatedInput = new ByteArrayInputStream("exit\n".getBytes)
        Console.withIn(simulatedInput) {
          TUIGame.startGame()
        }
      }
    }
  }

  "data" should {
    "hold the data for players" in {
      InitPlayers contains Player("Green") shouldBe true
    }
    "hold the data for streets" in {
      InitStreets contains Street("Tennessee Avenue", None, 0, 0, "Orange") shouldBe true
    }
    "hold the data for trains" in {
      InitTrains contains Railroad("B&O Railroad", None) shouldBe true
    }
    "hold the data for utilities" in {
      InitUtilities contains Utility("Water Works", None) shouldBe true
    }
    "hold the data for events" in {
      Events contains Event("Go back 3 spaces", MoveSpaces(-3)) shouldBe true
    }
    "hold the data for boards" in {
      Board contains 0 -> "Go" shouldBe true
    }
  }
}