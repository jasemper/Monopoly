package de.htwg.monopoly

class WaitingForRoll extends GameState {
  override def rollDice(controller: Controller, dice1: Int = -1, dice2: Int = -1): GameResult  = {
    val spaces = (dice1, dice2) match {
      case (-1, -1) => controller.rollDice(scala.util.Random.nextInt(6) + 1, scala.util.Random.nextInt(6) + 1)
      case (-1, d2) => controller.rollDice(0, d2)
      case (d1, -1) => controller.rollDice(d1, 0)
      case (d1, d2) => controller.rollDice(d1, d2)
    }
    controller.setState(new Moving)
    Success(Some(spaces))
  }

  override def move(controller: Controller, spaces: Int): GameResult =
    Error("You must roll the dice before moving.")

  override def buy(controller: Controller): GameResult =
    Error("You can't buy anything before moving.")

  override def buildHouse(controller: Controller, fieldNr: Int): GameResult =
    Error("You must finish your move before building.")

  override def buildHotel(controller: Controller, fieldNr: Int): GameResult =
    Error("You must finish your move before building.")

  override def endTurn(controller: Controller): GameResult =
    Error("You must roll and move first.")
}
