package de.htwg.monopoly

class WaitingForRoll extends GameState {
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
