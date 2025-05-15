package de.htwg.monopoly

class Moving extends GameState {
  override def rollDice(controller: Controller, dice1: Int, dice2: Int): Int =
    println("You already rolled this turn.")
    -1

  override def move(controller: Controller, spaces: Int): Unit =
    controller.moveCurrentPlayer(spaces)
    controller.setState(new Buying)

  override def buy(controller: Controller): Unit = {
    println("You first need to move this turn.")
  }

  override def buildHouse(controller: Controller, fieldNr: Int): Unit =
    println("You must buy or end turn before building.")

  override def buildHotel(controller: Controller, fieldNr: Int): Unit =
    println("You must buy or end turn before building.")

  override def endTurn(controller: Controller): Unit = {
    controller.nextTurn()
    if (controller.currentPlayer.inJail)
      controller.setState(new InJail)
    else
      controller.setState(new WaitingForRoll)
  }
}
