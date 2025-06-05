package de.htwg.monopoly

import javax.swing._
import java.awt._
import java.awt.event._
import javax.swing.SwingUtilities

class Gui(controller: Controller) extends Observer {

  // GUI components
  private val frame = new JFrame("Monopoly GUI")
  private val statusTextArea = new JTextArea(20, 50)
  private val inputPanel = new JPanel()
  private val moveButton = new JButton("Move")
  private val buyButton = new JButton("Buy")
  private val buildHouseButton = new JButton("Build House")
  private val buildHotelButton = new JButton("Build Hotel")
  private val endTurnButton = new JButton("End Turn")
  private val undoButton = new JButton("Undo")
  private val redoButton = new JButton("Redo")
  private val inputField = new JTextField(5) // for positions or dice input

  // Setup GUI layout and behavior
  def createAndShowGUI(): Unit = {
    frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)

    frame.setLayout(new BorderLayout())

    // Text area for status
    statusTextArea.setEditable(false)
    frame.add(new JScrollPane(statusTextArea), BorderLayout.CENTER)

    // Input panel with buttons and input field
    inputPanel.setLayout(new FlowLayout())

    inputPanel.add(new JLabel("Input:"))
    inputPanel.add(inputField)
    inputPanel.add(moveButton)
    inputPanel.add(buyButton)
    inputPanel.add(buildHouseButton)
    inputPanel.add(buildHotelButton)
    inputPanel.add(endTurnButton)
    inputPanel.add(undoButton)
    inputPanel.add(redoButton)

    frame.add(inputPanel, BorderLayout.SOUTH)

    // Button listeners
    moveButton.addActionListener(_ => doMove())
    buyButton.addActionListener(_ => doBuy())
    buildHouseButton.addActionListener(_ => doBuildHouse())
    buildHotelButton.addActionListener(_ => doBuildHotel())
    endTurnButton.addActionListener(_ => doEndTurn())
    undoButton.addActionListener(_ => controller.undo())
    redoButton.addActionListener(_ => controller.redo())

    frame.pack()
    frame.setLocationRelativeTo(null) // center on screen
    frame.setVisible(true)
  }

  // Methods to handle button clicks and call controller
  private def doMove(): Unit = {
    val input = inputField.getText.trim
    val result = if (input.isEmpty) {
      // Roll dice automatically
      controller.state.rollDice(controller)
    } else {
      // Parse dice input (can be 1 or 2 dice separated by space or comma)
      val parts = input.split("[ ,]+")
      parts.length match {
        case 1 =>
          val d1 = parts(0).toIntOption
          d1 match {
            case Some(d1Val) => controller.state.rollDice(controller, Some(d1Val))
            case None => Error("Invalid input for dice")
          }
        case 2 =>
          val d1 = parts(0).toIntOption
          val d2 = parts(1).toIntOption
          (d1, d2) match {
            case (Some(v1), Some(v2)) => controller.state.rollDice(controller, Some(v1), Some(v2))
            case _ => Error("Invalid dice inputs")
          }
        case _ =>
          Error("Invalid dice input format")
      }
    }

    result match {
      case Success(Some(spaces)) =>
        controller.state.move(controller, spaces)
      case Success(None) =>
        showError("No moves rolled.")
      case Error(msg) =>
        showError(s"Roll failed: $msg")
    }
  }

  private def doBuy(): Unit = {
    controller.state.buy(controller)
  }

  private def doBuildHouse(): Unit = {
    inputField.getText.trim.toIntOption match {
      case Some(pos) => controller.state.buildHouse(controller, pos)
      case None => showError("Enter a valid field number to build a house.")
    }
  }

  private def doBuildHotel(): Unit = {
    inputField.getText.trim.toIntOption match {
      case Some(pos) => controller.state.buildHotel(controller, pos)
      case None => showError("Enter a valid field number to build a hotel.")
    }
  }

  private def doEndTurn(): Unit = {
    controller.state.endTurn(controller)
  }

  private def showError(msg: String): Unit = {
    JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE)
  }

  // Helper method to enable/disable buttons during AI turn
  private def setButtonsEnabled(enabled: Boolean): Unit = {
    moveButton.setEnabled(enabled)
    buyButton.setEnabled(enabled)
    buildHouseButton.setEnabled(enabled)
    buildHotelButton.setEnabled(enabled)
    endTurnButton.setEnabled(enabled)
    undoButton.setEnabled(enabled)
    redoButton.setEnabled(enabled)
    inputField.setEnabled(enabled)
  }

  override def update: Unit = {
  SwingUtilities.invokeLater(() => {
    val (players, streets, trains, utilities) = controller.getGameState

    // Update game status text
    val sb = new StringBuilder
    sb.append("Current Player Status:\n| Name    | Money | Pos | Jail |\n")
    for (player <- players) {
      sb.append(f"| ${player.color}%-8s|${player.money}%6d |  ${player.position}%2d | ${player.inJail}%5s|\n")
    }

    sb.append("\nCurrent Game Board Status:\n| Nr | Field                 | Owner   | House | Hotel | Players on field\n")
    for ((fieldNr, fieldName) <- Board) {
      val owner = streets.find(_.name == fieldName).flatMap(_.owner).getOrElse(
        trains.find(_.name == fieldName).flatMap(_.owner).getOrElse(
          utilities.find(_.name == fieldName).flatMap(_.owner).getOrElse("")
        )
      )
      val houses = streets.find(_.name == fieldName).map(_.buildings).getOrElse(0)
      val hotels = streets.find(_.name == fieldName).map(_.hotels).getOrElse(0)
      val playersOnField = players.filter(_.position == fieldNr).map(_.color)
      val playersString = if (playersOnField.isEmpty) "" else playersOnField.mkString(", ")
      sb.append(f"| ${fieldNr}%2d | ${fieldName}%-22s| ${owner}%-8s|   ${houses}%1d   |   ${hotels}%1d   | ${playersString}%-7s\n")
    }

    sb.append("\nUndo steps: " + controller.undoManager.undoStack.size + "\n")
    sb.append("Redo steps: " + controller.undoManager.redoStack.size + "\n")
    sb.append("\n" + controller.state.toString())

    statusTextArea.setText(sb.toString())

    val currentPlayer = controller.currentPlayer
    if (currentPlayer.strategy.isDefined) {
      // Disable buttons to prevent human input during AI turn
      setButtonsEnabled(false)

      println(s"\n${currentPlayer.color}'s turn (AI):")
      println("Calculating move...")

      controller.performAITurn()
      controller.state.endTurn(controller)

      // After AI turn is done, enable buttons and update UI again
      setButtonsEnabled(true)
      update
    } else {
      // Human player - enable buttons
      setButtonsEnabled(true)
    }
  })
}

}
