package de.htwg.monopoly.controller.impl.state
import de.htwg.monopoly.controller._
import de.htwg.monopoly.model.GameStateEnum

class WaitingForRoll extends GameState {

  override def getState: GameStateEnum = GameStateEnum.WaitingForRoll
  override def rollDice(controller: IController, dice1: Option[Int], dice2: Option[Int]): GameResult = {
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


  override def move(controller: IController, spaces: Int): GameResult =
    Error("You must roll the dice before moving.")

  override def buy(controller: IController): GameResult =
    Error("You can't buy anything before moving.")

  override def buildHouse(controller: IController, fieldNr: Int): GameResult =
    Error("You must finish your move before building.")

  override def buildHotel(controller: IController, fieldNr: Int): GameResult =
    Error("You must finish your move before building.")

  override def endTurn(controller: IController): GameResult =
    Error("You must roll and move first.")
}
