package de.htwg.monopoly

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

    // Create UIs
    val tui = new Tui(controller)
    val gui = new Gui(controller)

    // Register both as observers
    controller.add(tui)
    controller.add(gui)

    // Start GUI in Swing event thread
    SwingUtilities.invokeLater(() => {
      gui.createAndShowGUI()
    })

    // Start TUI input loop in a separate thread
    val tuiThread = new Thread(() => {
      tui.devPlay()  // this reads user input in console and sends commands to controller
    })
    tuiThread.setDaemon(true)
    tuiThread.start()

    // Optionally block main thread until TUI thread finishes (or just keep running)
    tuiThread.join()
  }
}
