package de.htwg.monopoly

class Buying extends GameState {
  override def rollDice(controller: Controller, dice1: Int, dice2: Int): Int =
    println("You already rolled this turn.")
    -1

  override def move(controller: Controller, spaces: Int): Unit =
    println("You already moved this turn.")

  override def buy(controller: Controller): Unit = {
    controller.buyCurrentProperty()
    controller.setState(new Building)
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
