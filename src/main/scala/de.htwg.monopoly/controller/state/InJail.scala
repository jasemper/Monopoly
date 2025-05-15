package de.htwg.monopoly

class InJail extends GameState {
  override def rollDice(controller: Controller, dice1: Int, dice2: Int): Int = {
    val spaces = controller.rollDice(dice1, dice2)
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

  override def move(controller: Controller, spaces: Int): Int =
    println("You're in jail. Roll a double to get out.")
    -1

  override def buy(controller: Controller): Int =
    println("You're in jail.")
    -1

  override def buildHouse(controller: Controller, fieldNr: Int): Int =
    println("You're in jail. Can't build now.")
    -1

  override def buildHotel(controller: Controller, fieldNr: Int): Int =
    println("You're in jail. Can't build now.")
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
