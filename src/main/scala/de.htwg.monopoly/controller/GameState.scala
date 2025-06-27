package de.htwg.monopoly.controller

import de.htwg.monopoly.model.GameStateEnum

trait GameState {
  def getState: GameStateEnum
  def rollDice(controller: IController, dice1: Option[Int] = None, dice2: Option[Int] = None): GameResult
  def move(controller: IController, spaces: Int): GameResult
  def buy(controller: IController): GameResult
  def buildHouse(controller: IController, fieldNr: Int): GameResult
  def buildHotel(controller: IController, fieldNr: Int): GameResult
  def endTurn(controller: IController): GameResult
}
