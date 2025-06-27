package de.htwg.monopoly

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import java.io.{ByteArrayOutputStream, PrintStream}
import java.io.ByteArrayInputStream

import de.htwg.monopoly.controller.IController
import de.htwg.monopoly.controller.impl.Controller
import de.htwg.monopoly.view.tui._
import de.htwg.monopoly.model.Player
import de.htwg.monopoly.model.{Street, Train, Utility}
import de.htwg.monopoly.model.strategy.AggressiveStrategy

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
            output.length should be > 0
        }
        "show AI players" in {
            val player = Player("Blue", strategy = Some(new AggressiveStrategy))
            val player2 = Player("Green")
            val controller = new Controller(players = Vector(player, player2))
            val tui = new Tui(controller)
            val simulatedInput = new ByteArrayInputStream("exit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            Console.withIn(simulatedInput) {
                Console.withOut(new PrintStream(outContent)) {
                    tui.devPlay()
                }
            }
            val output = outContent.toString
            output should include("AI")
        }
        "show updated player position" in {
            val player = Player("Blue")
            val player2 = Player("Green")
            val controller = new Controller(players = Vector(player, player2))
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
        "show an error on invalid number format" in {
            val player = Player("Blue")
            val player2 = Player("Green")
            val controller = new Controller(players = Vector(player, player2))
            val tui = new Tui(controller)
            val simulatedInput = new ByteArrayInputStream("move 1.5\nexit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            Console.withIn(simulatedInput) {
                Console.withOut(new PrintStream(outContent)) {
                    tui.devPlay()
                }
            }
            val output = outContent.toString
            output should include("Invalid number format")
        }
        "show updated random player position" in {
            val player = Player("Blue")
            val player2 = Player("Green")
            val controller = new Controller(players = Vector(player, player2))
            val tui = new Tui(controller)
            val simulatedInput = new ByteArrayInputStream("move\nexit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            Console.withIn(simulatedInput) {
                Console.withOut(new PrintStream(outContent)) {
                    tui.devPlay()
                }
            }
            val output = outContent.toString
            output should include("| Green")
        }
        "show updated specific playerroll position" in {
            val player = Player("Blue")
            val player2 = Player("Green")
            val controller = new Controller(players = Vector(player, player2))
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
        "show error on invalid number format" in {
            val player = Player("Blue")
            val player2 = Player("Green")
            val controller = new Controller(players = Vector(player, player2))
            val tui = new Tui(controller)
            val simulatedInput = new ByteArrayInputStream("move 1 2.5\nexit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            Console.withIn(simulatedInput) {
                Console.withOut(new PrintStream(outContent)) {
                    tui.devPlay()
                }
            }
            val output = outContent.toString
            output should include("Invalid number format")
        }
        "show bought properties" in {
            val player = Player("Blue")
            val player2 = Player("Green")
            val controller = new Controller(players = Vector(player, player2))
            val tui = new Tui(controller)
            val simulatedInput = new ByteArrayInputStream("move 1\nbuy\nexit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            Console.withIn(simulatedInput) {
                Console.withOut(new PrintStream(outContent)) {
                    tui.devPlay()
                }
            }
            val fieldName = controller.streets(0).name
            val owner = "Blue"
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
            val player = Player("Blue")
            val player2 = Player("Green")
            val controller = new Controller(players = Vector(player,player2))
            val tui = new Tui(controller)
            val simulatedInput = new ByteArrayInputStream("move\nend\nexit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            Console.withIn(simulatedInput) {
                Console.withOut(new PrintStream(outContent)) {
                    tui.devPlay()
                }
            }
            val output = outContent.toString
            output should include("Green's turn")
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
        "show an error on invalid move random" in {
            val player = Player("Blue")
            val player2 = Player("Green")
            val controller = new Controller(players = Vector(player,player2))
            val tui = new Tui(controller)
            val simulatedInput = new ByteArrayInputStream("move 1\nmove\nexit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            Console.withIn(simulatedInput) {
                Console.withOut(new PrintStream(outContent)) {
                    tui.devPlay()
                }
            }
            val output = outContent.toString
            output should include("You already rolled this turn.")
        }
        "show an error on invalid move spaces" in {
            val player = Player("Blue")
            val player2 = Player("Green")
            val controller = new Controller(players = Vector(player,player2))
            val tui = new Tui(controller)
            val simulatedInput = new ByteArrayInputStream("move 1\nmove 3\nexit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            Console.withIn(simulatedInput) {
                Console.withOut(new PrintStream(outContent)) {
                    tui.devPlay()
                }
            }
            val output = outContent.toString
            output should include("You already rolled this turn.")
        }
        "show an error on invalid move specific" in {
            val player = Player("Blue")
            val player2 = Player("Green")
            val controller = new Controller(players = Vector(player,player2))
            val tui = new Tui(controller)
            val simulatedInput = new ByteArrayInputStream("move 1\nmove 1 2\nexit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            Console.withIn(simulatedInput) {
                Console.withOut(new PrintStream(outContent)) {
                    tui.devPlay()
                }
            }
            val output = outContent.toString
            output should include("You already rolled this turn.")
        }
        "undo a buy" in {
            val player = Player("Blue")
            val player2 = Player("Green")
            val controller = new Controller(players = Vector(player,player2))
            val tui = new Tui(controller)
            val simulatedInput = new ByteArrayInputStream("move 1\nbuy\nundo\nexit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            Console.withIn(simulatedInput) {
                Console.withOut(new PrintStream(outContent)) {
                    tui.devPlay()
                }
            }
            val output = outContent.toString
            output should include("undo")
        }
        "redo a buy" in {
            val player = Player("Blue")
            val player2 = Player("Green")
            val controller = new Controller(players = Vector(player,player2))
            val tui = new Tui(controller)
            val simulatedInput = new ByteArrayInputStream("move 1\nbuy\nundo\nredo\nexit\n".getBytes)
            val outContent = new ByteArrayOutputStream()
            Console.withIn(simulatedInput) {
                Console.withOut(new PrintStream(outContent)) {
                    tui.devPlay()
                }
            }
            val output = outContent.toString
            output should include("redo")
        }
    }
}