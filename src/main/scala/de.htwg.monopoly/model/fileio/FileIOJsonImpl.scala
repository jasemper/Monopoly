package de.htwg.monopoly.model.fileio

import play.api.libs.json._
import play.api.libs.functional.syntax._
import de.htwg.monopoly.model._
import de.htwg.monopoly.model.strategy._

// --- JSON Format for your enum GameStateEnum ---
implicit val gameStateEnumFormat: Format[GameStateEnum] = new Format[GameStateEnum] {
  def writes(state: GameStateEnum): JsValue = state match {
    case GameStateEnum.WaitingForRoll => JsString("WaitingForRoll")
    case GameStateEnum.Moving          => JsString("Moving")
    case GameStateEnum.Buying          => JsString("Buying")
    case GameStateEnum.Building        => JsString("Building")
    case GameStateEnum.TurnEnded       => JsString("TurnEnded")
    case GameStateEnum.InJail          => JsString("InJail")
  }

  def reads(json: JsValue): JsResult[GameStateEnum] = json match {
    case JsString("WaitingForRoll") => JsSuccess(GameStateEnum.WaitingForRoll)
    case JsString("Moving")          => JsSuccess(GameStateEnum.Moving)
    case JsString("Buying")          => JsSuccess(GameStateEnum.Buying)
    case JsString("Building")        => JsSuccess(GameStateEnum.Building)
    case JsString("TurnEnded")       => JsSuccess(GameStateEnum.TurnEnded)
    case JsString("InJail")          => JsSuccess(GameStateEnum.InJail)
    case _                          => JsError("Unknown GameStateEnum value")
  }
}

// --- PlayerStrategy formats, you can't auto-derive because they're traits ---
implicit val aggressiveStrategyFormat: Format[AggressiveStrategy] = Format(
  Reads(_ => JsSuccess(new AggressiveStrategy)),
  Writes(_ => JsString("AggressiveStrategy"))
)

implicit val defensiveStrategyFormat: Format[DefensiveStrategy] = Format(
  Reads(_ => JsSuccess(new DefensiveStrategy)),
  Writes(_ => JsString("DefensiveStrategy"))
)

implicit val randomStrategyFormat: Format[RandomStrategy] = Format(
  Reads(_ => JsSuccess(new RandomStrategy)),
  Writes(_ => JsString("RandomStrategy"))
)

// Option[PlayerStrategy] format discriminating on type
implicit val playerStrategyFormat: Format[Option[PlayerStrategy]] = new Format[Option[PlayerStrategy]] {
  def writes(opt: Option[PlayerStrategy]): JsValue = opt match {
    case Some(_: AggressiveStrategy) => JsString("AggressiveStrategy")
    case Some(_: DefensiveStrategy)  => JsString("DefensiveStrategy")
    case Some(_: RandomStrategy)     => JsString("RandomStrategy")
    case None                        => JsNull
    case Some(other)                 => JsString(other.getClass.getSimpleName)
  }
  def reads(json: JsValue): JsResult[Option[PlayerStrategy]] = json match {
    case JsString("AggressiveStrategy") => JsSuccess(Some(new AggressiveStrategy))
    case JsString("DefensiveStrategy")  => JsSuccess(Some(new DefensiveStrategy))
    case JsString("RandomStrategy")     => JsSuccess(Some(new RandomStrategy))
    case JsNull                        => JsSuccess(None)
    case _                            => JsError("Unknown PlayerStrategy")
  }
}

// --- Property and subclasses formats ---
implicit val propertyFormat: OFormat[Property] = Json.format[Property]
implicit val streetFormat: OFormat[Street] = Json.format[Street]
implicit val trainFormat: OFormat[Train] = Json.format[Train]
implicit val utilityFormat: OFormat[Utility] = Json.format[Utility]

// --- Vector formats explicitly to avoid ambiguity ---
implicit def vectorFormat[A](implicit fmt: Format[A]): Format[Vector[A]] = Format(
  Reads.seq[A](fmt).map(_.toVector),
  Writes[Vector[A]](v => Writes.seq(fmt).writes(v.toSeq))
)

implicit val propertyVectorFormat: Format[Vector[Property]] = vectorFormat(propertyFormat)
implicit val streetVectorFormat: Format[Vector[Street]] = vectorFormat(streetFormat)
implicit val trainVectorFormat: Format[Vector[Train]] = vectorFormat(trainFormat)
implicit val utilityVectorFormat: Format[Vector[Utility]] = vectorFormat(utilityFormat)

// --- Player and GameSnapshot formats ---
implicit val playerFormat: OFormat[Player] = (
  (__ \ "color").format[String] and
  (__ \ "money").format[Int] and
  (__ \ "position").format[Int] and
  (__ \ "pasch").format[Int] and
  (__ \ "roll").format[Int] and
  (__ \ "properties").format[Vector[Property]] and
  (__ \ "inJail").format[Boolean] and
  (__ \ "strategy").format[Option[PlayerStrategy]]
)(
  Player.apply,
  player => (
    player.color,
    player.money,
    player.position,
    player.pasch,
    player.roll,
    player.properties,
    player.inJail,
    player.strategy
  )
)
implicit val gameSnapshotFormat: OFormat[GameSnapshot] = Json.format[GameSnapshot]

class FileIOJsonImpl extends FileIOInterface {
  
  override def save(snapshot: GameSnapshot, filename: String): Unit = {
    val jsonString = Json.prettyPrint(Json.toJson(snapshot))
    val pw = new java.io.PrintWriter(new java.io.File(filename))
    try {
      pw.write(jsonString)
    } finally {
      pw.close()
    }
  }

  override def load(filename: String): GameSnapshot = {
    val source = scala.io.Source.fromFile(filename)
    try {
      val jsonString = source.mkString
      Json.parse(jsonString).as[GameSnapshot]
    } finally {
      source.close()
    }
  }
}
