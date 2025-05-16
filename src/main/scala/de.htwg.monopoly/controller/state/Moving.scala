package de.htwg.monopoly

class Moving extends GameState {
  override def rollDice(controller: Controller, dice1: Int, dice2: Int): GameResult =
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
