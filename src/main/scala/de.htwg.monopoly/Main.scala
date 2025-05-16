package de.htwg.monopoly

object TUIGame {
  @main def startGame(): Unit = {
    val players = Vector(
      new Human()("Red"),
      new AggressiveAI()("Blue"),
      new DefensiveAI()("Green"),
      new RandomAI()("Yellow")
    )
    val controller = new Controller(players = players)
    val tui = new Tui(controller)
    tui.devPlay()
  }
}

