package de.htwg.monopoly

import scala.io.StdIn.readLine


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

  def statusReport(Players: Vector[Player], Streets: Vector[Street], Trains: Vector[Railroad], Utilities: Vector[Utility]):  Unit = {
    println("Current Player Status:\n| Name    | Money | Pos | Jail |")
    for (player <- Players) {
      printf("| %-8s|%6d |  %2d | %5s|\n", player.color, player.money, player.position, player.inJail)
    }
    println("\nCurrent Game Board Status:\n| Nr | Field                 | Owner   | House | Hotel | Players on field")
    for ((fieldNr, fieldName) <- Board) {

      val maybeStreet = Streets.find(_.name == fieldName)
      val maybeTrain = Trains.find(_.name == fieldName)
      val maybeUtility = Utilities.find(_.name == fieldName)

      val owner = getOwner(fieldNr, Streets, Trains, Utilities) 
      val houses = getHouses(fieldNr, Streets, Trains, Utilities)
      val hotels = getHotels(fieldNr, Streets, Trains, Utilities)

      val playersOnField = Players.filter(_.position == fieldNr).map(_.color)
      val playersString = if (playersOnField.isEmpty) "" else playersOnField.mkString(", ")
      printf("| %2d | %-22s| %-8s|   %1d   |   %1d   | %-7s\n", fieldNr, fieldName, owner, houses, hotels, playersString)
    }
  }