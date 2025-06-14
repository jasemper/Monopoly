package de.htwg.monopoly.controller.state
import de.htwg.monopoly.controller.Controller
import de.htwg.monopoly.model.GameStateEnum

class Moving extends GameState {

  override def getState: GameStateEnum = GameStateEnum.Moving
  
  override def rollDice(controller: Controller, dice1: Option[Int], dice2: Option[Int]): GameResult = 
    Error("You already rolled this turn.")

  override def move(controller: Controller, spaces: Int): GameResult =
    controller.moveCurrentPlayer(spaces)
    controller.setState(new Buying)
    Success()

  override def buy(controller: Controller): GameResult = {
    Error("You first need to move this turn.")
  }

  override def buildHouse(controller: Controller, fieldNr: Int): GameResult =
    Error("You must finish your move before building.")

  override def buildHotel(controller: Controller, fieldNr: Int): GameResult =
    Error("You must finish your move before building.")

  override def endTurn(controller: Controller): GameResult = {
    controller.nextTurn()
    if (controller.currentPlayer.inJail)
      controller.setState(new InJail)
    else
      controller.setState(new WaitingForRoll)
    Success()
  }
}
