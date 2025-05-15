package de.htwg.monopoly

class Building extends GameState {
  override def rollDice(controller: Controller, dice1: Int, dice2: Int): Int =
    println("You already moved and bought.")
    -1

  override def move(controller: Controller, spaces: Int): Unit =
    println("You already moved.")

  override def buy(controller: Controller): Unit =
    println("You already bought or skipped buying.")

  override def buildHouse(controller: Controller, fieldNr: Int): Unit = {
    controller.buildHouse(fieldNr)
  }

  override def buildHotel(controller: Controller, fieldNr: Int): Unit = {
    controller.buildHotel(fieldNr)
  }

  override def endTurn(controller: Controller): Unit = {
    controller.nextTurn()
    if (controller.currentPlayer.inJail)
      controller.setState(new InJail)
    else
      controller.setState(new WaitingForRoll)
  }
}
