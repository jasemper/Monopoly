package de.htwg.monopoly

import scala.io.StdIn.readLine

def inputPoC(): Unit = {
  val controller = new Controller()
  controller.statusReport()
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
    controller.statusReport()
  }
}