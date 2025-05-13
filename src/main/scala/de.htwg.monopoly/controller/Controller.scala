package de.htwg.monopoly

class Controller(
    var players: Vector[Player] = InitPlayers,
    var streets: Vector[Street] = InitStreets,
    var trains: Vector[Railroad] = InitTrains,
    var utilities: Vector[Utility] = InitUtilities,
    var currentPlayerIndex: Int = 0) extends Observable{

  def currentPlayer: Player = players(currentPlayerIndex)

  def rollDice(dice1: Int = scala.util.Random.nextInt(6) + 1, dice2: Int = scala.util.Random.nextInt(6) + 1): Int = {
    val total = dice1 + dice2
    if (dice1 == dice2) {
      players = players.updated(currentPlayerIndex, currentPlayer.copy(pasch = currentPlayer.pasch + 1))
      players = players.updated(currentPlayerIndex, currentPlayer.copy(roll = currentPlayer.roll + 1))
      currentPlayerPasch()
    } else {
      players = players.updated(currentPlayerIndex, currentPlayer.copy(roll = currentPlayer.roll + 1))
      players = players.updated(currentPlayerIndex, currentPlayer.copy(pasch = 0))
    }
    total
  }

  def currentPlayerPasch(): Unit = {
    if (currentPlayer.pasch >= 3) {
      toJail()
    }
  }

  def moveCurrentPlayer(spaces: Int): Unit = {
    if (currentPlayer.roll <= currentPlayer.pasch+1 && currentPlayer.roll <= 3 && currentPlayer.inJail == false) {
      players = movePlayer(players, currentPlayerIndex, spaces)
      val owner  = getCurrentOwner
      if (owner != "") {
        val ownerIndex = getPlayerIndex(players, owner) 
        players = addMoney(players, currentPlayerIndex, -getRent(currentPlayer.position, streets, trains, utilities))
        players = addMoney(players, ownerIndex, getRent(currentPlayer.position, streets, trains, utilities))
      }
    }
    if (currentPlayer.inJail == true && currentPlayer.pasch > 0 && currentPlayer.roll <=3) {
      players = players.updated(currentPlayerIndex, currentPlayer.copy(inJail = false))
      moveCurrentPlayer(spaces)
    }
    notifyObservers
  }

  def buyCurrentProperty(): Unit = {
    if (getCurrentOwner != "") {
      notifyObservers
      return
    }
    val fieldNr = currentPlayer.position
    val (newStreets, newTrains, newUtilities) = giveOwner(currentPlayer, fieldNr, streets, trains, utilities)
    players = addMoney(players, currentPlayerIndex, -getRent(currentPlayer.position, streets, trains, utilities)*10)
    streets = newStreets
    trains = newTrains
    utilities = newUtilities
    notifyObservers
  }

  def toJail(): Unit = {
    val fieldNr = currentPlayer.position
    moveCurrentPlayer((10-fieldNr)%40)
    players = players.updated(currentPlayerIndex, currentPlayer.copy(inJail = true))
    players = players.updated(currentPlayerIndex, currentPlayer.copy(pasch = 0))
    notifyObservers
  }

  def nextTurn(): Unit = {
    players = players.updated(currentPlayerIndex, currentPlayer.copy(pasch = 0))
    players = players.updated(currentPlayerIndex, currentPlayer.copy(roll = 0))
    currentPlayerIndex = (currentPlayerIndex + 1) % players.length
    notifyObservers
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
    notifyObservers
  }

  def buildHotel(streetFieldNr: Int): Unit = {
    val fieldName = Board(streetFieldNr)._2
    val index = streets.indexWhere(_.name == fieldName)
    if (index >= 0 && streets(index).owner.contains(currentPlayer.color)) {
      val updatedStreet = streets(index).copy(hotels = streets(index).hotels + 1)
      streets = streets.updated(index, updatedStreet)
    }
    notifyObservers
  }

  def getGameState: (Vector[Player], Vector[Street], Vector[Railroad], Vector[Utility]) = {
    (players, streets, trains, utilities)
  }
}
