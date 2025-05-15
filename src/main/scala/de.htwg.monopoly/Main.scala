package de.htwg.monopoly

object TUIGame {
  @main def startGame(): Unit = {
    val players = Vector(
      new HumanFactory().createPlayer("Red"),
      new AggressiveFactory().createPlayer("Blue"),
      new DefensiveFactory().createPlayer("Green"),
      new RandomFactory().createPlayer("Yellow")
    )
    val playersHuman = Vector(
      new HumanFactory().createPlayer("Red"),
      new HumanFactory().createPlayer("Blue"),
      new HumanFactory().createPlayer("Green"),
      new HumanFactory().createPlayer("Yellow")
    )
    val controller = new Controller(players = playersHuman)
    val tui = new Tui(controller)
    tui.devPlay()
  }
}

