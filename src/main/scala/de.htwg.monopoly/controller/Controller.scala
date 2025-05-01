package de.htwg.monopoly

class Controller(
    var players: Vector[Player] = InitPlayers,
    var streets: Vector[Street] = InitStreets,
    var trains: Vector[Railroad] = InitTrains,
    var utilities: Vector[Utility] = InitUtilities,
    var currentPlayerIndex: Int = 0) {

  def currentPlayer: Player = players(currentPlayerIndex)

  def moveCurrentPlayer(spaces: Int): Unit = {
    players = movePlayer(players, currentPlayerIndex, spaces)
  }

  def buyCurrentProperty(): Unit = {
    val fieldNr = currentPlayer.position
    val (newStreets, newTrains, newUtilities) = giveOwner(currentPlayer, fieldNr, streets, trains, utilities)
    streets = newStreets
    trains = newTrains
    utilities = newUtilities
  }

  def nextTurn(): Unit = {
    currentPlayerIndex = (currentPlayerIndex + 1) % players.length
  }

  def getCurrentFieldName: String = {
    val position = players(currentPlayerIndex).position
    Board(position)._2
  }

  def getCurrentOwner: String = {
    val position = players(currentPlayerIndex).position
    getOwner(position, streets, trains, utilities)
  }

  def getWinnerIfAny: Option[String] = {
    val winner = getWinner(players)
    if (winner.isEmpty) None else Some(winner)
  }

  def buildHouse(streetFieldNr: Int): Unit = {
    val fieldName = Board(streetFieldNr)._2
    val index = streets.indexWhere(_.name == fieldName)
    if (index >= 0 && streets(index).owner.contains(currentPlayer.color)) {
      val updatedStreet = streets(index).copy(buildings = streets(index).buildings + 1)
      streets = streets.updated(index, updatedStreet)
    }
  }

  def buildHotel(streetFieldNr: Int): Unit = {
    val fieldName = Board(streetFieldNr)._2
    val index = streets.indexWhere(_.name == fieldName)
    if (index >= 0 && streets(index).owner.contains(currentPlayer.color)) {
      val updatedStreet = streets(index).copy(hotels = streets(index).hotels + 1)
      streets = streets.updated(index, updatedStreet)
    }
  }

  def statusReport(): Unit = {
    println("Current Player Status:\n| Name    | Money | Pos | Jail |")
    for (player <- players) {
      printf("| %-8s|%6d |  %2d | %5s|\n", player.color, player.money, player.position, player.inJail)
    }
    println("\nCurrent Game Board Status:\n| Nr | Field                 | Owner   | House | Hotel | Players on field")
    for ((fieldNr, fieldName) <- Board) {
      val owner = getOwner(fieldNr, streets, trains, utilities)
      val houses = getHouses(fieldNr, streets, trains, utilities)
      val hotels = getHotels(fieldNr, streets, trains, utilities)
      val playersOnField = players.filter(_.position == fieldNr).map(_.color)
      val playersString = if (playersOnField.isEmpty) "" else playersOnField.mkString(", ")
      printf("| %2d | %-22s| %-8s|   %1d   |   %1d   | %-7s\n", fieldNr, fieldName, owner, houses, hotels, playersString)
    }
  }
}
