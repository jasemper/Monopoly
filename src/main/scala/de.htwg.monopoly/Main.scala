package de.htwg.monopoly

object TUIGame {
  @main def startGame(): Unit = {
  val controller = new Controller(
    players = Vector(
      Player("Red", 10000, 0, strategy = None),
      Player("Blue", 10000, 0, strategy = Some(new AggressiveStrategy)),
      Player("Green", 10000, 0, strategy = Some(new DefensiveStrategy)),
      Player("Yellow", 10000, 0, strategy = Some(new RandomStrategy))
    )
  )
  val tui = new Tui(controller)
  tui.devPlay()
}
}

