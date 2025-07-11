package de.htwg.monopoly.model
import de.htwg.monopoly.model.strategy.PlayerStrategy

sealed trait Property {
  def name: String
  def owner: Option[String]
  def rent: Int
}

sealed trait EventEffect

case class Player(
  color: String,
  money: Int = 10000,
  position: Int = 0,
  pasch: Int = 0,
  roll: Int = 0,
  properties: Vector[Property] = Vector.empty,
  inJail: Boolean = false,
  strategy: Option[PlayerStrategy] = None
)

case class Street(
  name: String,
  owner: Option[String] = None,
  buildings: Int = 0,
  hotels: Int = 0,
  colorGroup: String,
  rent: Int = 100
) extends Property

case class Train(
  name: String,
  owner: Option[String] = None,
  rent: Int = 100
) extends Property

case class Utility(
  name: String,
  owner: Option[String] = None,
  rent: Int = 100
) extends Property

case class Event(description: String, effect: EventEffect)

case object GoToJail extends EventEffect
case class MoveTo(position: Int) extends EventEffect
case class Earn(amount: Int) extends EventEffect
case class Pay(amount: Int) extends EventEffect
case class PayPerBuilding(houseCost: Int, hotelCost: Int) extends EventEffect
case class MoveSpaces(spaces: Int) extends EventEffect
