package de.htwg.monopoly.controller.impl

import scala.util.Random
import de.htwg.monopoly.model._
import de.htwg.monopoly.model.data._
import de.htwg.monopoly.util.UndoManager
import de.htwg.monopoly.util.GameSnapshotCommand
import de.htwg.monopoly.util.Observable
import de.htwg.monopoly.controller.impl.helper._
import de.htwg.monopoly.controller.impl.state._
import de.htwg.monopoly.controller.IController
import de.htwg.monopoly.controller.GameState
import de.htwg.monopoly.model.fileio._

class Controller(
    var players: Vector[Player] = InitPlayers,
    var streets: Vector[Street] = InitStreets,
    var trains: Vector[Train] = InitTrains,
    var utilities: Vector[Utility] = InitUtilities,
    var currentPlayerIndex: Int = 0,
    var state: GameState = new WaitingForRoll) extends Observable with IController{

  val undoManager = new UndoManager()
  var undoAllowed: Boolean = false
  val fileIO: FileIOInterface = new FileIOJsonImpl()

  def updatePlayers(newPlayers: Vector[Player]): Unit = {
    players = newPlayers
  }

  def currentPlayer: Player = players(currentPlayerIndex)
  var tilt: IController.Tilt = IController.Tilt.Random
  override def getTilt: IController.Tilt = (tilt)

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
      state = Buying()
    }
    if (currentPlayer.inJail == true && currentPlayer.pasch > 0 && currentPlayer.roll <=3) {
      players = players.updated(currentPlayerIndex, currentPlayer.copy(inJail = false))
      moveCurrentPlayer(spaces)
    }
    if (currentPlayer.position == 30){
      toJail()
    }
    if (currentPlayer.position == 4) {
      players = players.updated(currentPlayerIndex, currentPlayer.copy(money = currentPlayer.money - 200))
    }
    if (currentPlayer.position == 38) {
      players = players.updated(currentPlayerIndex, currentPlayer.copy(money = currentPlayer.money - 500))
    }
    if (currentPlayer.position == 2 || currentPlayer.position == 7 || currentPlayer.position == 17 || currentPlayer.position == 22 || currentPlayer.position == 33 || currentPlayer.position == 36) {
      eventCard()
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
    markUndoPoint()
    notifyObservers
  }

  def toJail(): Unit = {
    val fieldNr = currentPlayer.position
    moveCurrentPlayer((10-fieldNr)%40)
    players = players.updated(currentPlayerIndex, currentPlayer.copy(inJail = true))
    players = players.updated(currentPlayerIndex, currentPlayer.copy(pasch = 0))
    notifyObservers
  }

  def payJailFee(): Unit = {
    players = players.updated(currentPlayerIndex, currentPlayer.copy(money = currentPlayer.money - 500))
    players = players.updated(currentPlayerIndex, currentPlayer.copy(inJail = false))
    notifyObservers
  }

  def eventCard(): Unit = {
  Random.nextInt(4) match {
    case 0 =>
      // Event 1: Lose $200
      players = players.updated(currentPlayerIndex, currentPlayer.copy(money = currentPlayer.money - 200))
    case 1 =>
      // Event 2: Go to jail
      toJail()
    case 2 =>
      // Event 3: Gain $100
      players = players.updated(currentPlayerIndex, currentPlayer.copy(money = currentPlayer.money + 100))
    case 3 =>
      // Event 4: Swap position with another random player
      val otherIndex = Random.shuffle((0 until players.length).filter(_ != currentPlayerIndex)).head
      val tempPos = players(otherIndex).position
      players = players.updated(otherIndex, players(otherIndex).copy(position = currentPlayer.position))
      players = players.updated(currentPlayerIndex, currentPlayer.copy(position = tempPos))
  }
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
    markUndoPoint()
    notifyObservers
  }

  def buildHotel(streetFieldNr: Int): Unit = {
    val fieldName = Board(streetFieldNr)._2
    val index = streets.indexWhere(_.name == fieldName)
    if (index >= 0 && streets(index).owner.contains(currentPlayer.color)) {
      val updatedStreet = streets(index).copy(hotels = streets(index).hotels + 1)
      streets = streets.updated(index, updatedStreet)
    }
    markUndoPoint()
    notifyObservers
  }

  def performAITurn(dice1: Int = scala.util.Random.nextInt(6) + 1, dice2: Int = scala.util.Random.nextInt(6) + 1): Unit = {
    currentPlayer.strategy.foreach { strategy =>
      val spaces = rollDice(dice1, dice2)
      moveCurrentPlayer(spaces)

      if (getCurrentOwner == "") {
        if strategy.decideBuy(currentPlayer, this) then buyCurrentProperty()

      }
      if (currentPlayer.color == getCurrentOwner) {
        if (strategy.decideBuildHouse(currentPlayer, this)){
          buildHouse(currentPlayer.position)
        }
        if (strategy.decideBuildHotel(currentPlayer, this)) {
          buildHotel(currentPlayer.position)
        }

      }
      if (currentPlayer.inJail) {
        if strategy.decideJail(currentPlayer, this) then payJailFee()

      }
    }
    undoManager.clear()
  }

  def setState(newState: GameState): Unit = {
    state = newState
    newState match {
      case _: Buying =>
        onEnterBuyingState()
      case _: TurnEnded =>
        onExitTurn()
      case _ =>
    }
  }

  def getGameState: (Vector[Player], Vector[Street], Vector[Train], Vector[Utility]) = {
    (players, streets, trains, utilities)
  }

  def createSnapshot(): GameSnapshot = GameSnapshot(players, streets, trains, utilities, currentPlayerIndex, state.getState)

  def markUndoPoint(): Unit = {
    if (undoAllowed) {
      val snapshot = createSnapshot()
      undoManager.doStep(new GameSnapshotCommand(this: IController, snapshot))
    }
  }

  def undo(): Unit = {
    if (undoAllowed) undoManager.undoStep()
    notifyObservers
  }

  def redo(): Unit = {
    if (undoAllowed) undoManager.redoStep()
    notifyObservers
  }

  def restoreSnapshot(snapshot: GameSnapshot): Unit = {
    this.players = snapshot.players
    this.streets = snapshot.streets
    this.trains = snapshot.trains
    this.utilities = snapshot.utilities
    this.currentPlayerIndex = snapshot.currentPlayerIndex
    val stateenum = snapshot.gameState
    this.state = stateenum match {
      case GameStateEnum.WaitingForRoll => new WaitingForRoll
      case GameStateEnum.Moving => new Moving
      case GameStateEnum.Buying => new Buying
      case GameStateEnum.Building => new Building
      case GameStateEnum.TurnEnded => new TurnEnded
      case GameStateEnum.InJail => new InJail
    }
  }

  def onEnterBuyingState(): Unit = {
    undoAllowed = true
    markUndoPoint()
  }

  def onExitTurn(): Unit = {
    undoAllowed = false
    undoManager.clear()
  }

  override def undoStepsAvailable: Int = undoManager.undoStack.size
  override def redoStepsAvailable: Int = undoManager.redoStack.size

  def saveGame(filename: String): Unit = {
    val snapshot = createSnapshot()
    fileIO.save(snapshot, filename)
  }
  def loadGame(filename: String): Unit = {
    val snapshot = fileIO.load(filename)
    restoreSnapshot(snapshot)
  }
}
