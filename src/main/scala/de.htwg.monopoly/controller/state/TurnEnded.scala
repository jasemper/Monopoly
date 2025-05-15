package de.htwg.monopoly

class TurnEnded extends GameState {
  override def rollDice(controller: Controller, dice1: Int, dice2: Int): Int =
    println("Wait for your next turn.")
    -1

  override def move(controller: Controller, spaces: Int): Int =
    println("Wait for your next turn.")
    -1

  override def buy(controller: Controller): Int =
    println("Wait for your next turn.")
    -1

  override def buildHouse(controller: Controller, fieldNr: Int): Int =
    println("Wait for your next turn.")
    -1

  override def buildHotel(controller: Controller, fieldNr: Int): Int =
    println("Wait for your next turn.")
    -1

  override def endTurn(controller: Controller): Int = {
    controller.nextTurn()
    if (controller.currentPlayer.inJail)
      controller.setState(new InJail)
    else
      controller.setState(new WaitingForRoll)
    0
  }
}
