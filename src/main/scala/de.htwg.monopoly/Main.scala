package de.htwg.monopoly

object TUIGame {
  @main def startGame(): Unit = {
    val controller = new Controller()
    val tui = new Tui(controller)
    tui.devPlay()
  }
}