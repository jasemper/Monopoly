package de.htwg.monopoly

object TUIGame {
  @main def startGame(): Unit = {
    val players = Vector(
      Human("Red"),
      AggressiveAI("Blue"),
      DefensiveAI("Green"),
      RandomAI("Yellow")
    )
    val controller = new Controller(players = players)
    val tui = new Tui(controller)
    tui.devPlay()
  }
}

