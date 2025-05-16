package de.htwg.monopoly

trait GameState {
  def rollDice(controller: Controller, dice1: Int, dice2: Int): GameResult
  def move(controller: Controller, spaces: Int): GameResult
  def buy(controller: Controller): GameResult
  def buildHouse(controller: Controller, fieldNr: Int): GameResult
  def buildHotel(controller: Controller, fieldNr: Int): GameResult
  def endTurn(controller: Controller): GameResult
}


sealed trait GameResult

case class Success(spacesMoved: Option[Int] = None) extends GameResult
case class Error(message: String) extends GameResult