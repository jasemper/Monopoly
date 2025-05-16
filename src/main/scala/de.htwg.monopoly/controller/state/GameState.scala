package de.htwg.monopoly

trait GameState {
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