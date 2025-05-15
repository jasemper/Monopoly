package de.htwg.monopoly

class InJail extends GameState {
  override def rollDice(controller: Controller, dice1: Int, dice2: Int): Int = {
    val spaces = controller.rollDice()
    if (controller.currentPlayer.pasch > 0) {
      controller.players = controller.players.updated(
        controller.currentPlayerIndex,
        controller.currentPlayer.copy(inJail = false)
      )
      controller.moveCurrentPlayer(spaces)
      controller.setState(new Buying)
    } else {
      println("You didn’t roll a double. You're still in jail.")
      controller.setState(new TurnEnded)
    }
    spaces
  }

  override def move(controller: Controller, spaces: Int): Unit =
    println("You're in jail. Roll a double to get out.")

  override def buy(controller: Controller): Unit =
    println("You're in jail.")

  override def buildHouse(controller: Controller, fieldNr: Int): Unit =
    println("You're in jail. Can't build now.")

  override def buildHotel(controller: Controller, fieldNr: Int): Unit =
    println("You're in jail. Can't build now.")

  override def endTurn(controller: Controller): Unit = {
    controller.nextTurn()
    if (controller.currentPlayer.inJail)
      controller.setState(new InJail)
    else
      controller.setState(new WaitingForRoll)
  }
}
