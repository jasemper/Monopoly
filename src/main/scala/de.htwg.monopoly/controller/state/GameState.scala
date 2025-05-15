package de.htwg.monopoly

trait GameState {
  def rollDice(controller: Controller, dice1: Int = scala.util.Random.nextInt(6) + 1, dice2: Int = scala.util.Random.nextInt(6) + 1): Int
  def move(controller: Controller, spaces: Int): Unit
  def buy(controller: Controller): Unit
  def buildHouse(controller: Controller, fieldNr: Int): Unit
  def buildHotel(controller: Controller, fieldNr: Int): Unit
  def endTurn(controller: Controller): Unit
}
