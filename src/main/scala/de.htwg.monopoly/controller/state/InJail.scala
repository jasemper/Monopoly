package de.htwg.monopoly

class InJail extends GameState {
  override def rollDice(controller: Controller, dice1: Option[Int], dice2: Option[Int]): GameResult = {
    val (d1, d2) = (dice1, dice2) match {
      case (Some(d1), Some(d2)) => (d1, d2)
      case (Some(d1), None)     => (d1, 0)
      case (None, Some(d2))     => (0, d2)
      case (None, None)         => (
        scala.util.Random.nextInt(6) + 1,
        scala.util.Random.nextInt(6) + 1
      )
    }
    val spaces = controller.rollDice(d1, d2)
    if (controller.currentPlayer.pasch > 0) {
      controller.players = controller.players.updated(
        controller.currentPlayerIndex,
        controller.currentPlayer.copy(inJail = false)
      )
      controller.moveCurrentPlayer(spaces)
      controller.setState(new Buying)
      Success(Some(spaces))
    } else {
      println("You didn’t roll a double. You're still in jail.")
      controller.setState(new TurnEnded)
      Success(None)
    }
    
  }

  override def move(controller: Controller, spaces: Int): GameResult =
    Error("You're in jail. Roll a double to get out.")

  override def buy(controller: Controller): GameResult =
    Error("You're in jail.")

  override def buildHouse(controller: Controller, fieldNr: Int): GameResult =
    Error("You're in jail. Can't build now.")

  override def buildHotel(controller: Controller, fieldNr: Int): GameResult =
    Error("You're in jail. Can't build now.")

  override def endTurn(controller: Controller): GameResult = {
    controller.nextTurn()
    if (controller.currentPlayer.inJail)
      controller.setState(new InJail)
    else
      controller.setState(new WaitingForRoll)
    Success()
  }
}
