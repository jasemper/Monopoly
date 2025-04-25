package de.htwg.monopoly

import scala.io.StdIn.readLine

import de.htwg.monopoly.Main.InitPlayers
import de.htwg.monopoly.Main.InitStreets
import de.htwg.monopoly.Main.InitTrains
import de.htwg.monopoly.Main.InitUtilities
import de.htwg.monopoly.Main.statusReport
import de.htwg.monopoly.Main.giveOwner
import de.htwg.monopoly.Player

def inputPoC(): Unit = {
    var streets = InitStreets
    var trains = InitTrains
    var utilities = InitUtilities
    val player = Player("BetaTester")
    statusReport(InitPlayers, streets, trains, utilities)
    var input = ""
    while ({
      print("Enter property number to buy (or 'exit' to quit): ")
      input = scala.io.StdIn.readLine()
      input != "exit"
    }) {
      try {
        val num = input.toInt
        val (newStreets, newTrains, newUtilities) = giveOwner(player, num, streets, trains, utilities)
        streets = newStreets
        trains = newTrains
        utilities = newUtilities

        statusReport(InitPlayers, streets, trains, utilities)
      } catch {
        case _: NumberFormatException =>
          println("Please enter a valid number.")
      }
    }
  }