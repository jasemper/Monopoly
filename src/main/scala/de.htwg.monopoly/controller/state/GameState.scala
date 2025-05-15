package de.htwg.monopoly

trait GameState {
  def rollDice(controller: Controller, dice1: Int, dice2: Int): Int
  def move(controller: Controller, spaces: Int): Int
  def buy(controller: Controller): Int
  def buildHouse(controller: Controller, fieldNr: Int): Int
  def buildHotel(controller: Controller, fieldNr: Int): Int
  def endTurn(controller: Controller): Int
}
