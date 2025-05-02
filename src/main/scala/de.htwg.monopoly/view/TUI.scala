package de.htwg.monopoly

import scala.io.StdIn.readLine

class Tui(controller: Controller) extends Observer {
  controller.add(this)
  def devPlay(): Unit = {
    print(statusReport())
    var input = ""
    while ({
      println(s"Current player: ${controller.currentPlayer.color}")
      println("Enter command: move X | buy | buildhouse X | buildhotel X | end | exit")
      input = readLine()
      input != "exit"
    }) {
      val parts = input.split(" ")
      parts(0).toLowerCase match {
        case "move" if parts.length == 2 =>
          println("moved (theory)")
          controller.moveCurrentPlayer(parts(1).toInt)
        case "buy" =>
          controller.buyCurrentProperty()
        case "buildhouse" if parts.length == 2 =>
          controller.buildHouse(parts(1).toInt)
        case "buildhotel" if parts.length == 2 =>
          controller.buildHotel(parts(1).toInt)
        case "end" =>
          controller.nextTurn()
        case _ =>
          println("Invalid command.")
      }
    }
  }
  def statusReport(): String = {
    val (players, streets, trains, utilities) = controller.getGameState

    var strOut = "Current Player Status:\n| Name    | Money | Pos | Jail |\n"
    for (player <- players) {
      strOut += f"| ${player.color}%-8s|${player.money}%6d | ${player.position}%2d | ${player.inJail}%5s|\n"
    }

    strOut += "\nCurrent Game Board Status:\n| Nr | Field                 | Owner   | House | Hotel | Players on field\n"
    for ((fieldNr, fieldName) <- Board) {
      val ownerx = controller.getCurrentOwner
      val owner = streets.find(_.name == fieldName).flatMap(_.owner).getOrElse("")
      val houses = streets.find(_.name == fieldName).map(_.buildings).getOrElse(0)
      val hotels = streets.find(_.name == fieldName).map(_.hotels).getOrElse(0)
      val playersOnField = players.filter(_.position == fieldNr).map(_.color)
      val playersString = if (playersOnField.isEmpty) "" else playersOnField.mkString(", ")
      strOut += f"| ${fieldNr}%2d | ${fieldName}%-22s| ${owner}%-8s|   ${houses}%1d   |   ${hotels}%1d   | ${playersString}%-7s\n"
    }
    return strOut
  }
  override def update: Unit = println(statusReport())
}