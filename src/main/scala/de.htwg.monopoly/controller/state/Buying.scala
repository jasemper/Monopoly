package de.htwg.monopoly

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

  override def buildHouse(controller: Controller, fieldNr: Int): GameResult =
    Error("You must buy or end turn before building.")

  override def buildHotel(controller: Controller, fieldNr: Int): GameResult =
    Error("You must buy or end turn before building.")

  override def endTurn(controller: Controller): GameResult = {
    controller.nextTurn()
    if (controller.currentPlayer.inJail)
      controller.setState(new InJail)
    else
      controller.setState(new WaitingForRoll)
    Success()
  }
}
