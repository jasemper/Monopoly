package de.htwg.monopoly

import scala.io.StdIn.readLine

class Tui(controller: Controller) extends Observer {
  controller.add(this)
  def devPlay(): Unit = {
    val controller = new Controller()
    statusReport()
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
      statusReport()
    }
  }
  def statusReport(): Unit = {
  val (players, streets, trains, utilities) = controller.getGameState

  println("Current Player Status:\n| Name    | Money | Pos | Jail |")
  for (player <- players) {
    printf("| %-8s|%6d |  %2d | %5s|\n", player.color, player.money, player.position, player.inJail)
  }

  println("\nCurrent Game Board Status:\n| Nr | Field                 | Owner   | House | Hotel | Players on field")
  for ((fieldNr, fieldName) <- Board) {
    val owner = controller.getCurrentOwner
    val houses = streets.find(_.name == fieldName).map(_.buildings).getOrElse(0)
    val hotels = streets.find(_.name == fieldName).map(_.hotels).getOrElse(0)
    val playersOnField = players.filter(_.position == fieldNr).map(_.color)
    val playersString = if (playersOnField.isEmpty) "" else playersOnField.mkString(", ")
    printf("| %2d | %-22s| %-8s|   %1d   |   %1d   | %-7s\n", fieldNr, fieldName, owner, houses, hotels, playersString)
  }
}
  override def update: Unit = println(statusReport())
}