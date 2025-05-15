package de.htwg.monopoly

class TurnEnded extends GameState {
  override def rollDice(controller: Controller, dice1: Int, dice2: Int): Int =
    println("Wait for your next turn.")
    -1

  override def move(controller: Controller, spaces: Int): Unit =
    println("Wait for your next turn.")

  override def buy(controller: Controller): Unit =
    println("Wait for your next turn.")

  override def buildHouse(controller: Controller, fieldNr: Int): Unit =
    println("Wait for your next turn.")

  override def buildHotel(controller: Controller, fieldNr: Int): Unit =
    println("Wait for your next turn.")

  override def endTurn(controller: Controller): Unit = {
    controller.nextTurn()
    if (controller.currentPlayer.inJail)
      controller.setState(new InJail)
    else
      controller.setState(new WaitingForRoll)
  }
}
