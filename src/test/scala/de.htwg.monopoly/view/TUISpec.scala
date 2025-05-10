package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import java.io.{ByteArrayOutputStream, PrintStream}
import java.io.ByteArrayInputStream

class TUISpec extends AnyWordSpec {
    "TUI" should {
        "start a game in console" in {
            val controller = new Controller()
            val tui = new Tui(controller)

            val simulatedInput = new ByteArrayInputStream("exit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            Console.withIn(simulatedInput) {
                Console.withOut(new PrintStream(outContent)) {
                    tui.devPlay()
                }
            }
            val output = outContent.toString
            output should include("Current player: ")
            output should include("Current Player Status:")
        }
        "show updated player position" in {
            val controller = new Controller()
            val tui = new Tui(controller)
            val simulatedInput = new ByteArrayInputStream("move 2\nexit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            Console.withIn(simulatedInput) {
                Console.withOut(new PrintStream(outContent)) {
                    tui.devPlay()
                }
            }
            val output = outContent.toString
            output should include("|  2 | Event                 |         |   0   |   0   | Blue")
        }
        "show updated random player position" in {
            val controller = new Controller()
            val tui = new Tui(controller)
            val simulatedInput = new ByteArrayInputStream("move\nexit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            Console.withIn(simulatedInput) {
                Console.withOut(new PrintStream(outContent)) {
                    tui.devPlay()
                }
            }
            val output = outContent.toString
            output should include("| Green") // because usually blue is first. green can only be first if blue has moved away from start
        }
        "show updated specific playerroll position" in {
            val controller = new Controller()
            val tui = new Tui(controller)
            val simulatedInput = new ByteArrayInputStream("move 1 2\nexit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            Console.withIn(simulatedInput) {
                Console.withOut(new PrintStream(outContent)) {
                    tui.devPlay()
                }
            }
            val output = outContent.toString
            output should include("|  3 | Baltic Avenue         |         |   0   |   0   | Blue")
        }
        "show bought properties" in {
            val controller = new Controller()
            val tui = new Tui(controller)
            val simulatedInput = new ByteArrayInputStream("move 1\nbuy\nexit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            Console.withIn(simulatedInput) {
                Console.withOut(new PrintStream(outContent)) {
                    tui.devPlay()
                }
            }
            val fieldName = controller.streets(0).name
            val owner = controller.streets(0).owner.getOrElse("")
            val output = outContent.toString
            output should include(f"${fieldName}%-22s| ${owner}%-8s")
        }
        "show houses and hotels" in {
            val controller = new Controller()
            val tui = new Tui(controller)
            val simulatedInput = new ByteArrayInputStream("move 1\nbuy\nbuildhouse 1\nbuildhotel 1\nexit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            Console.withIn(simulatedInput) {
                Console.withOut(new PrintStream(outContent)) {
                    tui.devPlay()
                }
            }
            val output = outContent.toString
            val fieldName = controller.streets(0).name
            val owner = controller.streets(0).owner.getOrElse("")
            val fieldNr = 1
            val houses = 1
            val hotels = 1
            output should include(f"| ${fieldNr}%2d | ${fieldName}%-22s| ${owner}%-8s|   ${houses}%1d   |   ${hotels}%1d   | ${owner}%-7s\n")
        }
        "show next player" in {
            val controller = new Controller()
            val tui = new Tui(controller)
            val simulatedInput = new ByteArrayInputStream("end\nexit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            Console.withIn(simulatedInput) {
                Console.withOut(new PrintStream(outContent)) {
                    tui.devPlay()
                }
            }
            val output = outContent.toString
            output should include("Current player: Green")
        }
        "show an error on invalid command" in {
            val controller = new Controller()
            val tui = new Tui(controller)
            val simulatedInput = new ByteArrayInputStream("invalidcommand\nexit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            Console.withIn(simulatedInput) {
                Console.withOut(new PrintStream(outContent)) {
                    tui.devPlay()
                }
            }
            val output = outContent.toString
            output should include("Invalid command.")
        }
        "print the game state" in {
            val controller = new Controller()   
            val tui = new Tui(controller)
            val text = tui.statusReport()
            text should include("Current Game Board Status:")
        }
    }
}