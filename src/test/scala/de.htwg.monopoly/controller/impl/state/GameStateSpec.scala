package de.htwg.monopoly.controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

import de.htwg.monopoly.model._
import de.htwg.monopoly.util.Observer
/*
// Minimal dummy controller that satisfies the IController interface
class DummyController extends IController {
  def add(observer: Observer): Unit = {}
  def remove(observer: Observer): Unit = {}
  def buildHotel(fieldNr: Int): Unit = {}
  def buildHouse(fieldNr: Int): Unit = {}
  def buyCurrentProperty(): Unit = {}
  def createSnapshot(): GameSnapshot = 
  GameSnapshot(Vector(), Vector(), Vector(), Vector(), 0, GameStateEnum.InJail)
  def currentPlayer: Player = Player("Dummy")
  def currentPlayerIndex: Int = 0
  def getGameState: (Vector[Player], Vector[Street], Vector[Train], Vector[Utility]) =
    (Vector(), Vector(), Vector(), Vector())
  def getTilt: IController.Tilt = IController.Tilt.Random
  def getWinnerIfAny: Option[String] = None
  def moveCurrentPlayer(spaces: Int): Unit = {}
  def nextTurn(): Unit = {}
  def performAITurn(dice1: Int, dice2: Int): Unit = {}
  def players: Vector[Player] = Vector()
  def redo(): Unit = {}
  def redoStepsAvailable: Int = 0
  def restoreSnapshot(snapshot: GameSnapshot): Unit = {}
  def rollDice(dice1: Int, dice2: Int): Int = dice1 + dice2
  def setState(state: GameState): Unit = {}
  def state: GameState = new DummyState
  def undo(): Unit = {}
  def undoStepsAvailable: Int = 0
  def updatePlayers(newPlayers: Vector[Player]): Unit = {}
}

class DummyState extends GameState {
  override def getState: GameStateEnum = GameStateEnum.InJail
  override def rollDice(controller: IController, dice1: Option[Int], dice2: Option[Int]): GameResult =
    Success(Some(dice1.getOrElse(0) + dice2.getOrElse(0)))
  override def move(controller: IController, spaces: Int): GameResult = Success(Some(spaces))
  override def buy(controller: IController): GameResult = Success()
  override def buildHouse(controller: IController, fieldNr: Int): GameResult = Success()
  override def buildHotel(controller: IController, fieldNr: Int): GameResult = Success()
  override def endTurn(controller: IController): GameResult = Success()
}

class GameStateSpec extends AnyWordSpec {

  val state = new DummyState
  val controller = new DummyController

  "A GameState" should {
    "return the correct state from getState" in {
      state.getState shouldBe GameStateEnum.InJail
    }

    "return Success with the sum of dice on rollDice" in {
      state.rollDice(controller, Some(4), Some(3)) shouldBe Success(Some(7))
    }

    "handle rollDice with no dice values provided" in {
      state.rollDice(controller, None, None) shouldBe Success(Some(0))
    }

    "return Success with spaces moved on move" in {
      state.move(controller, 5) shouldBe Success(Some(5))
    }

    "return Success on buy" in {
      state.buy(controller) shouldBe a[Success]
    }

    "return Success on buildHouse" in {
      state.buildHouse(controller, 1) shouldBe a[Success]
    }

    "return Success on buildHotel" in {
      state.buildHotel(controller, 1) shouldBe a[Success]
    }

    "return Success on endTurn" in {
      state.endTurn(controller) shouldBe a[Success]
    }
  }
}
*/