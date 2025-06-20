package de.htwg.monopoly.view.gui

import de.htwg.monopoly.controller.api._
import de.htwg.monopoly.model.Board
import de.htwg.monopoly.model._

import java.awt._
import javax.swing.JPanel
import scala.collection.mutable

class BoardPanel(controller: IController) extends JPanel {

  private val gridSize = 11
  private val fieldSize = 60
  private val tokenSize = 10

  private val fieldPositions: Map[Int, (Int, Int)] = {

    val bottomRow = (0 to 10).map(i => (i, 0)).toList              

    val leftCol = (1 to 9).map(i => (0, 10 - i)).toList            

    val topRow = (0 to 10).map(i => (10 - i, 10)).toList           

    val rightCol = (1 to 9).map(i => (10, i)).toList              

    val positions = bottomRow ++ rightCol ++ topRow ++ leftCol

    (0 until 40).zip(positions).toMap
  }

  private val ownerColors: Map[String, Color] = Map(
    "Red" -> Color.RED,
    "Blue" -> Color.BLUE,
    "Green" -> Color.GREEN,
    "Yellow" -> Color.YELLOW,
    "" -> Color.LIGHT_GRAY 
  )

  private val playerColors: Map[String, Color] = Map(
    "Red" -> Color.RED,
    "Blue" -> Color.BLUE,
    "Green" -> Color.GREEN,
    "Yellow" -> Color.YELLOW
  )

  override def getPreferredSize: Dimension =
    new Dimension(fieldSize * gridSize, fieldSize * gridSize)

  override def paintComponent(g: Graphics): Unit = {
    super.paintComponent(g)
    val g2 = g.asInstanceOf[Graphics2D]

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)


    for (row <- 0 until gridSize; col <- 0 until gridSize) {
      if (row == 0 || row == gridSize - 1 || col == 0 || col == gridSize - 1) {
        g2.setColor(Color.WHITE)
        g2.fillRect(col * fieldSize, row * fieldSize, fieldSize, fieldSize)
        g2.setColor(Color.BLACK)
        g2.drawRect(col * fieldSize, row * fieldSize, fieldSize, fieldSize)
      }
    }

    for ((fieldNum, (col, row)) <- fieldPositions) {
      val x = col * fieldSize
      val y = row * fieldSize

      val ownerColor = getOwnerColor(fieldNum)
      g2.setColor(ownerColor)
      g2.fillRect(x + 1, y + 1, fieldSize - 2, fieldSize - 2)

      g2.setColor(Color.BLACK)
      val fieldName = Board(fieldNum)._2
      val font = g2.getFont.deriveFont(Font.PLAIN, 10f)
      g2.setFont(font)
      drawStringMultiLine(g2, fieldName, x + 5, y + 15, fieldSize - 10)

      drawBuildings(g2, fieldNum, x, y)

      g2.setFont(g2.getFont.deriveFont(Font.BOLD, 12f))
      g2.drawString(fieldNum.toString, x + fieldSize - 15, y + fieldSize - 5)
    }

    val players = controller.getGameState._1
    val playerPositions = mutable.Map[Int, mutable.ListBuffer[String]]()

    for (player <- players) {
      playerPositions.getOrElseUpdate(player.position, mutable.ListBuffer()) += player.color
    }

    for ((fieldNum, colors) <- playerPositions) {
      val (col, row) = fieldPositions(fieldNum)
      val baseX = col * fieldSize
      val baseY = row * fieldSize

      for ((color, idx) <- colors.zipWithIndex) {
        val tokenX = baseX + 5 + idx * (tokenSize + 2)
        val tokenY = baseY + fieldSize - tokenSize - 5
        drawPlayerToken(g2, tokenX, tokenY, color)
      }
    }
  }

  private def getOwnerColor(fieldNum: Int): Color = {
    val (streets, trains, utilities) = controller.getGameState._2 match {
      case streets => (controller.getGameState._2, controller.getGameState._3, controller.getGameState._4)
    }
    val ownerNameOpt = streets.find(_.name == Board(fieldNum)._2).flatMap(_.owner)
      .orElse(controller.getGameState._3.find(_.name == Board(fieldNum)._2).flatMap(_.owner))
      .orElse(controller.getGameState._4.find(_.name == Board(fieldNum)._2).flatMap(_.owner))
      .getOrElse("")

    ownerColors.getOrElse(ownerNameOpt, Color.LIGHT_GRAY)
  }

  private def drawBuildings(g2: Graphics2D, fieldNum: Int, x: Int, y: Int): Unit = {
    val (_, streets, _, _) = controller.getGameState
    streets.find(_.name == Board(fieldNum)._2) match {
      case Some(street) => {
        val houses = street.buildings
        val hotels = street.hotels
        g2.setColor(Color.ORANGE)
        val baseX = x + 5
        val baseY = y + 2
        for (i <- 0 until houses) {
          g2.fillRect(baseX + i * 12, baseY, 10, 10)
        }
        if (hotels > 0) {
          g2.setColor(new Color(128, 0, 128))
          g2.fillRect(baseX + houses * 12, baseY, 10, 10)
        }
      }
      case None => // do nothing if no street found
    }
  }


  private def drawPlayerToken(g2: Graphics2D, x: Int, y: Int, colorName: String): Unit = {
    val color = playerColors.getOrElse(colorName, Color.BLACK)
    g2.setColor(color)
    g2.fillOval(x, y, tokenSize, tokenSize)
    g2.setColor(Color.BLACK)
    g2.drawOval(x, y, tokenSize, tokenSize)
  }

  private def drawStringMultiLine(g2: Graphics2D, text: String, x: Int, y: Int, maxWidth: Int): Unit = {
    val words = text.split(" ")
    var line = ""
    val lineHeight = g2.getFontMetrics.getHeight
    var curY = y

    for (word <- words) {
      val testLine = if (line.isEmpty) word else line + " " + word
      val width = g2.getFontMetrics.stringWidth(testLine)
      if (width > maxWidth) {
        g2.drawString(line, x, curY)
        line = word
        curY += lineHeight
      } else {
        line = testLine
      }
    }
    g2.drawString(line, x, curY)
  }
}
