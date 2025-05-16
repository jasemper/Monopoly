package de.htwg.monopoly

class TurnEnded extends GameState {
  override def rollDice(controller: Controller, dice1: Option[Int], dice2: Option[Int]): GameResult = 
    Error("Wait for your next turn.")

  override def move(controller: Controller, spaces: Int): GameResult =
    Error("Wait for your next turn.")

  override def buy(controller: Controller): GameResult =
    Error("Wait for your next turn.")

  override def buildHouse(controller: Controller, fieldNr: Int): GameResult =
    Error("Wait for your next turn.")

  override def buildHotel(controller: Controller, fieldNr: Int): GameResult =
    Error("Wait for your next turn.")

  override def endTurn(controller: Controller): GameResult = {
    controller.nextTurn()
    if (controller.currentPlayer.inJail)
      controller.setState(new InJail)
    else
      controller.setState(new WaitingForRoll)
    Success()
  }
}
