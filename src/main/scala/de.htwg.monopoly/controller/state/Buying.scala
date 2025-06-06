package de.htwg.monopoly.controller.state
import de.htwg.monopoly.controller.Controller

class Buying extends GameState {
  override def rollDice(controller: Controller, dice1: Option[Int], dice2: Option[Int]): GameResult = 
    Error("You already rolled this turn.")

  override def move(controller: Controller, spaces: Int): GameResult =
    Error("You already moved this turn.")

  override def buy(controller: Controller): GameResult = {
    controller.buyCurrentProperty()
    controller.setState(new Building)
    Success()
  }

  override def buildHouse(controller: Controller, fieldNr: Int): GameResult = {
    controller.buildHouse(fieldNr)
    controller.setState(new Building)
    Success()
  }

  override def buildHotel(controller: Controller, fieldNr: Int): GameResult = {
    controller.buildHotel(fieldNr)
    controller.setState(new Building)
    Success()
  }

  override def endTurn(controller: Controller): GameResult = {
    controller.nextTurn()
    if (controller.currentPlayer.inJail)
      controller.setState(new InJail)
    else
      controller.setState(new WaitingForRoll)
    Success()
  }
}
