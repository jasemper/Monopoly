package de.htwg.monopoly.model.fileio

import de.htwg.monopoly.model._
import scala.xml.{Elem, XML}
import java.io._

class FileIOXmlImpl extends FileIOInterface {

  override def save(game: GameSnapshot, filename: String): Unit = {
    val xml: Elem =
      <game>
        <players>
          {game.players.map(p =>
            <player>
              <color>{p.color}</color>
              <money>{p.money}</money>
              <position>{p.position}</position>
              <pasch>{p.pasch}</pasch>
              <roll>{p.roll}</roll>
              <inJail>{p.inJail}</inJail>
              <!-- Properties and strategy skipped for simplicity -->
            </player>
          )}
        </players>
        <streets>
          {game.streets.map(s =>
            <street>
              <name>{s.name}</name>
              <owner>{s.owner.getOrElse("")}</owner>
              <buildings>{s.buildings}</buildings>
              <hotels>{s.hotels}</hotels>
              <colorGroup>{s.colorGroup}</colorGroup>
              <rent>{s.rent}</rent>
            </street>
          )}
        </streets>
        <trains>
          {game.trains.map(t =>
            <train>
              <name>{t.name}</name>
              <owner>{t.owner.getOrElse("")}</owner>
              <rent>{t.rent}</rent>
            </train>
          )}
        </trains>
        <utilities>
          {game.utilities.map(u =>
            <utility>
              <name>{u.name}</name>
              <owner>{u.owner.getOrElse("")}</owner>
              <rent>{u.rent}</rent>
            </utility>
          )}
        </utilities>
        <currentPlayerIndex>{game.currentPlayerIndex}</currentPlayerIndex>
        <gameState>{game.gameState.toString}</gameState>
      </game>

    XML.save(filename, xml, "UTF-8", xmlDecl = true)
  }

  override def load(filename: String): GameSnapshot = {
    val xml = XML.loadFile(filename)

    val players = (xml \ "players" \ "player").map(p =>
      Player(
        color = (p \ "color").text,
        money = (p \ "money").text.toInt,
        position = (p \ "position").text.toInt,
        pasch = (p \ "pasch").text.toInt,
        roll = (p \ "roll").text.toInt,
        inJail = (p \ "inJail").text.toBoolean
      )
    ).toVector

    val streets = (xml \ "streets" \ "street").map(s =>
      Street(
        name = (s \ "name").text,
        owner = Some((s \ "owner").text).filter(_.nonEmpty),
        buildings = (s \ "buildings").text.toInt,
        hotels = (s \ "hotels").text.toInt,
        colorGroup = (s \ "colorGroup").text,
        rent = (s \ "rent").text.toInt
      )
    ).toVector

    val trains = (xml \ "trains" \ "train").map(t =>
      Train(
        name = (t \ "name").text,
        owner = Some((t \ "owner").text).filter(_.nonEmpty),
        rent = (t \ "rent").text.toInt
      )
    ).toVector

    val utilities = (xml \ "utilities" \ "utility").map(u =>
      Utility(
        name = (u \ "name").text,
        owner = Some((u \ "owner").text).filter(_.nonEmpty),
        rent = (u \ "rent").text.toInt
      )
    ).toVector

    val currentPlayerIndex = (xml \ "currentPlayerIndex").text.toInt
    val gameState = GameStateEnum.valueOf((xml \ "gameState").text)

    GameSnapshot(players, streets, trains, utilities, currentPlayerIndex, gameState)
  }
}
