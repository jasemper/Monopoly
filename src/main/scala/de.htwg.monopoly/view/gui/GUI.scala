package de.htwg.monopoly.view.gui

import de.htwg.monopoly.util.Observer
import de.htwg.monopoly.controller.Controller
import de.htwg.monopoly.controller.state.{GameState, GameResult, Success, Error}

import javax.swing._
import java.awt._
import javax.swing.SwingUtilities

class Gui(controller: Controller) extends Observer {

  private val frame = new JFrame("Monopoly GUI")

  private val statusPanel = new JPanel()

  private val inputPanel = new JPanel()
  private val moveButton = new JButton("Move")
  private val buyButton = new JButton("Buy")
  private val buildHouseButton = new JButton("Build House")
  private val buildHotelButton = new JButton("Build Hotel")
  private val endTurnButton = new JButton("End Turn")
  private val undoButton = new JButton("Undo")
  private val redoButton = new JButton("Redo")
  private val inputField = new JTextField(5)
  private val boardPanel = new BoardPanel(controller)

  def createAndShowGUI(): Unit = {
    frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)
    frame.setLayout(new BorderLayout())

    frame.add(boardPanel, BorderLayout.CENTER)

    statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS))
    statusPanel.setPreferredSize(new Dimension(700, 150))
    val scrollPane = new JScrollPane(statusPanel)
    frame.add(scrollPane, BorderLayout.SOUTH)

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
    frame.add(inputPanel, BorderLayout.NORTH)

    moveButton.addActionListener(_ => doMove())
    buyButton.addActionListener(_ => doBuy())
    buildHouseButton.addActionListener(_ => doBuildHouse())
    buildHotelButton.addActionListener(_ => doBuildHotel())
    endTurnButton.addActionListener(_ => doEndTurn())
    undoButton.addActionListener(_ => controller.undo())
    redoButton.addActionListener(_ => controller.redo())

    frame.pack()
    frame.setLocationRelativeTo(null)
    frame.setVisible(true)
  }

  private def doMove(): Unit = {
    val input = inputField.getText.trim
    val result = if (input.isEmpty) {
      controller.state.rollDice(controller)
    } else {
      val parts = input.split("[ ,]+")
      parts.length match {
        case 1 =>
          parts(0).toIntOption match {
            case Some(d1Val) => controller.state.rollDice(controller, Some(d1Val))
            case None => Error("Invalid input for dice")
          }
        case 2 =>
          (parts(0).toIntOption, parts(1).toIntOption) match {
            case (Some(v1), Some(v2)) => controller.state.rollDice(controller, Some(v1), Some(v2))
            case _ => Error("Invalid dice inputs")
          }
        case _ =>
          Error("Invalid dice input format")
      }
    }

    result match {
      case Success(Some(spaces)) => controller.state.move(controller, spaces)
      case Success(None)         => showError("No moves rolled.")
      /*case Error(msg)            => showError(s"Roll failed: $msg")*/
      case e: Error => showError(s"Roll failed: ${e.message}")

    }
  }

  private def doBuy(): Unit = controller.state.buy(controller)

  private def doBuildHouse(): Unit = {
    inputField.getText.trim.toIntOption match {
      case Some(pos) => controller.state.buildHouse(controller, pos)
      case None      => showError("Enter a valid field number to build a house.")
    }
  }

  private def doBuildHotel(): Unit = {
    inputField.getText.trim.toIntOption match {
      case Some(pos) => controller.state.buildHotel(controller, pos)
      case None      => showError("Enter a valid field number to build a hotel.")
    }
  }

  private def doEndTurn(): Unit = controller.state.endTurn(controller)

  private def showError(msg: String): Unit =
    JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE)

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

  private def getColorForName(name: String): Color = name.toLowerCase match {
    case "red"    => Color.RED
    case "blue"   => Color.BLUE
    case "green"  => new Color(0, 128, 0)
    case "yellow" => Color.YELLOW.darker()
    case _        => Color.BLACK
  }

  def updategui: Unit = SwingUtilities.invokeLater(() => {
    val (players, streets, trains, utilities) = controller.getGameState

    statusPanel.removeAll()

    val title = new JLabel("Current Player Status:")
    title.setFont(new Font("Arial", Font.BOLD, 14))
    statusPanel.add(title)

    val header = new JPanel(new GridLayout(1, 4))
    header.add(new JLabel("Name"))
    header.add(new JLabel("Money"))
    header.add(new JLabel("Position"))
    header.add(new JLabel("In Jail"))
    statusPanel.add(header)

    for (player <- players) {
      val row = new JPanel(new GridLayout(1, 4))
      val nameLabel = new JLabel(player.color)
      nameLabel.setForeground(getColorForName(player.color))
      row.add(nameLabel)
      row.add(new JLabel(f"${player.money}%,d"))
      row.add(new JLabel(player.position.toString))
      row.add(new JLabel(player.inJail.toString))
      statusPanel.add(row)
    }

    statusPanel.revalidate()
    statusPanel.repaint()

    boardPanel.repaint()

    val currentPlayer = controller.currentPlayer
    if (currentPlayer.strategy.isDefined) {
      setButtonsEnabled(false)
      println(s"\n${currentPlayer.color}'s turn (AI):")
      println("Calculating move...")

      controller.performAITurn()
      controller.state.endTurn(controller)

      setButtonsEnabled(true)
      update
    } else {
      setButtonsEnabled(true)
    }
  })

  override def update: Unit = {
    updategui
  }
}
