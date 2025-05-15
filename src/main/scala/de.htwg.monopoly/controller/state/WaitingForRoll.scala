package de.htwg.monopoly

class WaitingForRoll extends GameState {
  override def rollDice(controller: Controller, dice1: Int = -1, dice2: Int = -1): Int = {
    val spaces = (dice1, dice2) match {
      case (-1, -1) => controller.rollDice(scala.util.Random.nextInt(6) + 1, scala.util.Random.nextInt(6) + 1)
      case (-1, d2) => controller.rollDice(0, d2)
      case (d1, -1) => controller.rollDice(d1, 0)
      case (d1, d2) => controller.rollDice(d1, d2)
    }
    controller.setState(new Moving)
    spaces
  }

  override def move(controller: Controller, spaces: Int): Int =
    println("You must roll the dice before moving.")
    -1

  override def buy(controller: Controller): Int =
    println("You can't buy anything before moving.")
    -1

  override def buildHouse(controller: Controller, fieldNr: Int): Int =
    println("You must finish your move before building.")
    -1

  override def buildHotel(controller: Controller, fieldNr: Int): Int =
    println("You must finish your move before building.")
    -1

  override def endTurn(controller: Controller): Int =
    println("You must roll and move first.")
    -1
}
