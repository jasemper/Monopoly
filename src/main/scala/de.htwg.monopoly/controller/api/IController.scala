package de.htwg.monopoly.controller.api

import de.htwg.monopoly.model._
import de.htwg.monopoly.util.Observer

trait IController {
  def currentPlayer: Player
  def players: Vector[Player]
  def updatePlayers(newPlayers: Vector[Player]): Unit
  def moveCurrentPlayer(spaces: Int): Unit
  def currentPlayerIndex: Int

  
  def buyCurrentProperty(): Unit
  def buildHouse(fieldNr: Int): Unit
  def buildHotel(fieldNr: Int): Unit
  def nextTurn(): Unit
  def setState(state: GameState): Unit
  
  def getGameState: (Vector[Player], Vector[Street], Vector[Train], Vector[Utility])
  def getWinnerIfAny: Option[String]

  def performAITurn(dice1: Int = scala.util.Random.nextInt(6) + 1, dice2: Int = scala.util.Random.nextInt(6) + 1): Unit

  def undo(): Unit
  def redo(): Unit
  def createSnapshot(): GameSnapshot
  def restoreSnapshot(snapshot: GameSnapshot): Unit

  def add(observer: Observer): Unit
  def remove(observer: Observer): Unit

  def undoStepsAvailable: Int
  def redoStepsAvailable: Int
  def getTilt: Tilt

  //states
  def state: GameState
  def rollDice(dice1: Int, dice2: Int): Int
}
