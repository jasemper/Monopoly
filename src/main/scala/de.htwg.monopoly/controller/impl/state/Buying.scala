package de.htwg.monopoly.controller.impl.state
import de.htwg.monopoly.controller.api._
import de.htwg.monopoly.model.GameStateEnum

class Buying extends GameState {

  override def getState: GameStateEnum = GameStateEnum.Buying
  
  override def rollDice(controller: IController, dice1: Option[Int], dice2: Option[Int]): GameResult = 
    Error("You already rolled this turn.")

  override def move(controller: IController, spaces: Int): GameResult =
    Error("You already moved this turn.")

  override def buy(controller: IController): GameResult = {
    controller.buyCurrentProperty()
    controller.setState(new Building)
    Success()
  }

  override def buildHouse(controller: IController, fieldNr: Int): GameResult = {
    controller.buildHouse(fieldNr)
    controller.setState(new Building)
    Success()
  }

  override def buildHotel(controller: IController, fieldNr: Int): GameResult = {
    controller.buildHotel(fieldNr)
    controller.setState(new Building)
    Success()
  }

  override def endTurn(controller: IController): GameResult = {
    controller.nextTurn()
    if (controller.currentPlayer.inJail)
      controller.setState(new InJail)
    else
      controller.setState(new WaitingForRoll)
    Success()
  }
}
