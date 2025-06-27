package de.htwg.monopoly.controller.impl.state
import de.htwg.monopoly.controller._
import de.htwg.monopoly.model.GameStateEnum

class Building extends GameState {

  override def getState: GameStateEnum = GameStateEnum.Building
  
  override def rollDice(controller: IController, dice1: Option[Int], dice2: Option[Int]): GameResult = 
    Error("You already moved and bought.")

  override def move(controller: IController, spaces: Int): GameResult =
    Error("You already moved.")

  override def buy(controller: IController): GameResult =
    Error("You already bought or skipped buying.")

  override def buildHouse(controller: IController, fieldNr: Int): GameResult = {
    controller.buildHouse(fieldNr)
    Success()
  }

  override def buildHotel(controller: IController, fieldNr: Int): GameResult = {
    controller.buildHotel(fieldNr)
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
