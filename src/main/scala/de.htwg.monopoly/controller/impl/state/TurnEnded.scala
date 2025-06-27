package de.htwg.monopoly.controller.impl.state
import de.htwg.monopoly.controller._
import de.htwg.monopoly.model.GameStateEnum

class TurnEnded extends GameState {

  override def getState: GameStateEnum = GameStateEnum.TurnEnded
  override def rollDice(controller: IController, dice1: Option[Int], dice2: Option[Int]): GameResult = 
    Error("Wait for your next turn.")

  override def move(controller: IController, spaces: Int): GameResult =
    Error("Wait for your next turn.")

  override def buy(controller: IController): GameResult =
    Error("Wait for your next turn.")

  override def buildHouse(controller: IController, fieldNr: Int): GameResult =
    Error("Wait for your next turn.")

  override def buildHotel(controller: IController, fieldNr: Int): GameResult =
    Error("Wait for your next turn.")

  override def endTurn(controller: IController): GameResult = {
    controller.nextTurn()
    if (controller.currentPlayer.inJail)
      controller.setState(new InJail)
    else
      controller.setState(new WaitingForRoll)
    Success()
  }
}
