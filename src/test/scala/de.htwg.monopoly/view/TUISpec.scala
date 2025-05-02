package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import java.io.{ByteArrayOutputStream, PrintStream}
import java.io.ByteArrayInputStream

class TUISpec extends AnyWordSpec {
    "TUI" should {
        "start a game in console" in {
            val originalIn = System.in
            val originalOut = System.out

            val controller = new Controller()
            val tui = new Tui(controller)

            val simulatedInput = new ByteArrayInputStream("exit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            try {
                Console.withIn(simulatedInput) {
                    Console.withOut(new PrintStream(outContent)) {
                        tui.devPlay()
                    }
                }

                val output = outContent.toString
                output should include("Current player: ")
                output should include("Current Player Status:")
            } finally {
                System.setIn(originalIn)
                System.setOut(originalOut)
            }
        }
        "print the game state" in {
            val controller = new Controller()   
            val tui = new Tui(controller)
            val text = tui.statusReport()
            text should include("Current Game Board Status:")
        }
    }
}