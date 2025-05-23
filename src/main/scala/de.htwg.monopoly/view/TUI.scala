package de.htwg.monopoly

import scala.io.StdIn.readLine
import scala.util.{Try, Failure}

class Tui(controller: Controller) extends Observer {
  controller.add(this)

  def devPlay(): Unit = {
    var run = true
    while (controller.getWinnerIfAny.isEmpty && run) {
      val player = controller.currentPlayer

      if (player.strategy.isDefined) {
        println(s"\n${player.color}'s turn (AI):")
        println("Calculating move...")
        controller.performAITurn()

        controller.state.endTurn(controller)
      } else {
        var continue = true
        while (continue) {

          println(s"\n${player.color}'s turn (Human):")
          println("Enter command: move [X] [Y] | buy | buildhouse [X] | buildhotel [X] | undo | redo | end | exit")
          val input = readLine()
          val parts = input.trim.split(" ")

          parts(0).toLowerCase match {
            case "move" if parts.length == 1 =>
              controller.state.rollDice(controller) match {
                case Success(Some(spaces)) =>
                  controller.state.move(controller, spaces)
                case Error(msg) =>
                  println(s"Roll failed: $msg")
              }

            case "move" if parts.length == 2 =>
              Try(parts(1).toInt) match {
                case scala.util.Success(die1) =>
                  controller.state.rollDice(controller, Some(die1)) match {
                    case Success(Some(spaces)) => //Own Success from Gamestate -- Rename
                      controller.state.move(controller, spaces)
                    case Error(msg) =>
                      println(s"Roll failed: $msg")
                  }
                case Failure(exception) =>
                  println(s"Invalid number format: ${exception.getMessage}")
              }

            case "move" if parts.length == 3 =>
              val diceTry = for {
                d1 <- Try(parts(1).toInt)
                d2 <- Try(parts(2).toInt)
              } yield (d1, d2)

              diceTry match {
                case scala.util.Success((die1, die2)) =>
                  controller.state.rollDice(controller, Some(die1), Some(die2)) match {
                    case Success(Some(spaces)) => //Own Success from Gamestate -- Rename
                      controller.state.move(controller, spaces)
                    case Error(msg) =>
                      println(s"Roll failed: $msg")
                  }
                case Failure(exception) =>
                  println(s"Invalid number format: ${exception.getMessage} in \"move ${parts(1)} ${parts(2)}\"")
              }

            case "buy" =>
              controller.state.buy(controller)
            case "buildhouse" if parts.length == 2 && parts(1).forall(_.isDigit) =>
              controller.state.buildHouse(controller, parts(1).toInt)
            case "buildhotel" if parts.length == 2 && parts(1).forall(_.isDigit) =>
              controller.state.buildHotel(controller, parts(1).toInt)
            case "end" =>
              controller.state.endTurn(controller)
              continue = false
            case "exit" =>
              continue = false
              run = false
            case "undo" =>
              controller.undo()
            case "redo" =>
              controller.redo()
            case _ =>
              println("Invalid command.")
          }
        }
      }
    }
  }



  def statusReport(): String = {
    val (players, streets, trains, utilities) = controller.getGameState

    var strOut = "Current Player Status:\n| Name    | Money | Pos | Jail |\n"
    for (player <- players) {
      strOut += f"| ${player.color}%-8s|${player.money}%6d |  ${player.position}%2d | ${player.inJail}%5s|\n"
    }

    strOut += "\nCurrent Game Board Status:\n| Nr | Field                 | Owner   | House | Hotel | Players on field\n"
    for ((fieldNr, fieldName) <- Board) {
      val ownerx = controller.getCurrentOwner
      val owner = streets.find(_.name == fieldName).flatMap(_.owner).getOrElse(
        trains.find(_.name == fieldName).flatMap(_.owner).getOrElse(
          utilities.find(_.name == fieldName).flatMap(_.owner).getOrElse("")
        )
      )
      val houses = streets.find(_.name == fieldName).map(_.buildings).getOrElse(0)
      val hotels = streets.find(_.name == fieldName).map(_.hotels).getOrElse(0)
      val playersOnField = players.filter(_.position == fieldNr).map(_.color)
      val playersString = if (playersOnField.isEmpty) "" else playersOnField.mkString(", ")
      strOut += f"| ${fieldNr}%2d | ${fieldName}%-22s| ${owner}%-8s|   ${houses}%1d   |   ${hotels}%1d   | ${playersString}%-7s\n"
    }
    return strOut
  }
  override def update: Unit = println(
    statusReport() +
    controller.undoManager.undoStack.size + " undo steps available\n" +
    controller.undoManager.redoStack.size + " redo steps available\n" +
    controller.state.toString()
    )
}