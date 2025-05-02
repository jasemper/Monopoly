package de.htwg.monopoly

object Main {
  @main def startGame(): Unit = {
    val controller = new Controller()
    val tui = new Tui(controller)
    tui.devPlay()
  }

  //TODO: Get all Case Classes own files
  //TODO: link buying and entering field to addmoney and remove money
  //TODO: Split tests into multiple files
  //TODO: Add tests for all classes
}