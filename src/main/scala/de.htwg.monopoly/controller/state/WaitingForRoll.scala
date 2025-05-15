package de.htwg.monopoly

class WaitingForRoll extends GameState {
  override def rollDice(controller: Controller, dice1: Int = scala.util.Random.nextInt(6) + 1, dice2: Int = scala.util.Random.nextInt(6) + 1): Int = {
    val spaces = controller.rollDice(dice1, dice2)
    controller.setState(new Moving)
    spaces
  }

  override def move(controller: Controller, spaces: Int): Unit =
    println("You must roll the dice before moving.")

  override def buy(controller: Controller): Unit =
    println("You can't buy anything before moving.")

  override def buildHouse(controller: Controller, fieldNr: Int): Unit =
    println("You must finish your move before building.")

  override def buildHotel(controller: Controller, fieldNr: Int): Unit =
    println("You must finish your move before building.")

  override def endTurn(controller: Controller): Unit =
    println("You must roll and move first.")
}
