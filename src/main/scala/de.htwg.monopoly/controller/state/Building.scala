package de.htwg.monopoly

class Building extends GameState {
  override def rollDice(controller: Controller, dice1: Int, dice2: Int): Int =
    println("You already moved and bought.")
    -1

  override def move(controller: Controller, spaces: Int): Int =
    println("You already moved.")
    -1

  override def buy(controller: Controller): Int =
    println("You already bought or skipped buying.")
    -1

  override def buildHouse(controller: Controller, fieldNr: Int): Int = {
    controller.buildHouse(fieldNr)
    0
  }

  override def buildHotel(controller: Controller, fieldNr: Int): Int = {
    controller.buildHotel(fieldNr)
    0
  }

  override def endTurn(controller: Controller): Int = {
    controller.nextTurn()
    if (controller.currentPlayer.inJail)
      controller.setState(new InJail)
    else
      controller.setState(new WaitingForRoll)
    0
  }
}
