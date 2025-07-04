package de.htwg.monopoly.inject

import de.htwg.monopoly.controller.IController
import de.htwg.monopoly.controller.impl.Controller
import de.htwg.monopoly.model.factory._
import de.htwg.monopoly.view.gui.Gui
import de.htwg.monopoly.view.tui.Tui

class GameModule {

  private val players = Vector(
    Human("Red"),
    AggressiveAI("Blue"),
    DefensiveAI("Green"),
    RandomAI("Yellow")
  )

  val controller: IController = new Controller(players)
  val tui: Tui = new Tui(controller)
  val gui: Gui = new Gui(controller)

  controller.add(tui)
  controller.add(gui)
}
