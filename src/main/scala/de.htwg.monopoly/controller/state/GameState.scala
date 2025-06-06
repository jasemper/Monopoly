package de.htwg.monopoly.controller.state
import de.htwg.monopoly.controller.Controller
import de.htwg.monopoly.model.GameStateEnum

trait GameState {
  var state: GameStateEnum = GameStateEnum.WaitingForRoll
  def getState: GameStateEnum = state
  def setState(newState: GameStateEnum): Unit = {
    state = newState
  }

  def rollDice(controller: Controller, dice1: Option[Int] = None, dice2: Option[Int] = None): GameResult
  def move(controller: Controller, spaces: Int): GameResult
  def buy(controller: Controller): GameResult
  def buildHouse(controller: Controller, fieldNr: Int): GameResult
  def buildHotel(controller: Controller, fieldNr: Int): GameResult
  def endTurn(controller: Controller): GameResult
}



trait GameResult

case class Success(spacesMoved: Option[Int] = None) extends GameResult
case class Error(message: String) extends GameResult