package de.htwg.monopoly.controller.impl.state
import de.htwg.monopoly.controller.api._
import de.htwg.monopoly.model.GameStateEnum

class Moving extends GameState {

  override def getState: GameStateEnum = GameStateEnum.Moving
  
  override def rollDice(controller: IController, dice1: Option[Int], dice2: Option[Int]): GameResult = 
    Error("You already rolled this turn.")

  override def move(controller: IController, spaces: Int): GameResult =
    controller.moveCurrentPlayer(spaces)
    controller.setState(new Buying)
    Success()

  override def buy(controller: IController): GameResult = {
    Error("You first need to move this turn.")
  }

  override def buildHouse(controller: IController, fieldNr: Int): GameResult =
    Error("You must finish your move before building.")

  override def buildHotel(controller: IController, fieldNr: Int): GameResult =
    Error("You must finish your move before building.")

  override def endTurn(controller: IController): GameResult = {
    controller.nextTurn()
    if (controller.currentPlayer.inJail)
      controller.setState(new InJail)
    else
      controller.setState(new WaitingForRoll)
    Success()
  }
}
