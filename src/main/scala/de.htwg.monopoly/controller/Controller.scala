package de.htwg.monopoly

class Controller(
    var players: Vector[Player] = InitPlayers,
    var streets: Vector[Street] = InitStreets,
    var trains: Vector[Railroad] = InitTrains,
    var utilities: Vector[Utility] = InitUtilities,
    var currentPlayerIndex: Int = 0) extends Observable{

  def currentPlayer: Player = players(currentPlayerIndex)

  def moveCurrentPlayer(spaces: Int): Unit = {
    players = movePlayer(players, currentPlayerIndex, spaces)
    notifyObservers
  }

  def buyCurrentProperty(): Unit = {
    val fieldNr = currentPlayer.position
    val (newStreets, newTrains, newUtilities) = giveOwner(currentPlayer, fieldNr, streets, trains, utilities)
    streets = newStreets
    trains = newTrains
    utilities = newUtilities
    notifyObservers
  }

  def nextTurn(): Unit = {
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

  def addMoney(Players: Vector[Player], index: Int, muula: Int): Vector[Player] = {
    val player = Players(index)
    val updatedPlayers = player.copy(money = player.money + muula)
    Players.updated(index, updatedPlayers)
  }

  def movePlayer(Players: Vector[Player], index: Int, spaces: Int): Vector[Player] = {
    val player = Players(index)
    val newPosition = (player.position + spaces) % 40
    val passedGo = (player.position + spaces) > 40
    val onGO = (player.position + spaces) == 40

    val updatedPlayers = if (onGO) {
      addMoney(Players, index, 400)
    } else if (passedGo) {
      addMoney(Players, index, 200)
    } else {
      Players
    }
    updatedPlayers.updated(index, updatedPlayers(index).copy(position = newPosition))
  }

  def giveOwner(player: Player, fieldNr: Int, Streets: Vector[Street], Trains: Vector[Railroad], Utilities: Vector[Utility]): (Vector[Street], Vector[Railroad], Vector[Utility]) = {
      val streetnrs = Array(1, 3, 6, 8, 9, 11, 13, 14, 16, 18, 19, 21, 23, 24, 26, 27, 29, 31, 32, 34, 37, 39)
      val trainnrs = Array(5, 15, 25, 35)

      val updatedStreets = 
      if (streetnrs contains fieldNr) {
        val streetIndex = streetnrs.indexOf(fieldNr)
        Streets.updated(streetIndex, Streets(streetIndex).copy(owner = Some(player.color)))
      } else Streets

      val updatedTrains = 
      if (trainnrs contains fieldNr) {
        val trainIndex = (fieldNr - 5) / 10
        Trains.updated(trainIndex, Trains(trainIndex).copy(owner = Some(player.color)))
      } else Trains

      val updatedUtilities = 
      if (fieldNr == 12 || fieldNr == 28) {
        val utilityIndex = fieldNr / 10 - 1
        Utilities.updated(utilityIndex, Utilities(utilityIndex).copy(owner = Some(player.color)))
      } else Utilities

      (updatedStreets, updatedTrains, updatedUtilities)
    }

  def getWinner(Players: Vector[Player]): String = {
    val remaining = Players.filter(_.money > 0)
    remaining match {
      case Vector(single) => single.color
      case _ => ""
    }
  }

    def getOwner(fieldNr: Int, Streets: Vector[Street], Trains: Vector[Railroad], Utilities: Vector[Utility]): String = {
    if (fieldNr < 0 || fieldNr >= Board.length) return ""

    val fieldName = Board(fieldNr)._2

    Streets.find(_.name == fieldName).flatMap(_.owner)
      .orElse(Trains.find(_.name == fieldName).flatMap(_.owner))
      .orElse(Utilities.find(_.name == fieldName).flatMap(_.owner))
      .getOrElse("")
  }
  def getHouses(fieldNr: Int, Streets: Vector[Street], Trains: Vector[Railroad], Utilities: Vector[Utility]): Int = {
    if (fieldNr < 0 || fieldNr >= Board.length) return 0
    val fieldName = Board(fieldNr)._2
    Streets.find(_.name == fieldName).map(_.buildings)
      .getOrElse(0)
  }
  def getHotels(fieldNr: Int, Streets: Vector[Street], Trains: Vector[Railroad], Utilities: Vector[Utility]): Int = {
    if (fieldNr < 0 || fieldNr >= Board.length) return 0
    val fieldName = Board(fieldNr)._2
    Streets.find(_.name == fieldName).map(_.hotels)
      .getOrElse(0)
  }
}
