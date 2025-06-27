package de.htwg.monopoly

import de.htwg.monopoly.controller.api._
import de.htwg.monopoly.controller.impl.Controller  // explicitly import Controller impl as I create an object
import de.htwg.monopoly.model.factory._
import de.htwg.monopoly.view.tui.Tui
import de.htwg.monopoly.view.gui.Gui

import javax.swing.SwingUtilities

object MonopolyGame {
  @main def startGame(): Unit = {
    val players = Vector(
      Human("Red"),
      AggressiveAI("Blue"),
      DefensiveAI("Green"),
      RandomAI("Yellow")
    )


    val controller = new Controller(players)
    val tui = new Tui(controller)
    controller.add(tui)
    tui.devPlay()
    
/*
    val controller = new Controller(players)
    val tui = new Tui(controller)
    val gui = new Gui(controller)
    controller.add(tui)
    controller.add(gui)

    SwingUtilities.invokeLater(() => {
      gui.createAndShowGUI()
    })

    val tuiThread = new Thread(() => {
      tui.devPlay()
    })
    tuiThread.setDaemon(true)
    tuiThread.start()
    tuiThread.join()*/
  }
}
