package de.htwg.monopoly

sealed trait Property {
  def name: String
  def owner: Option[String]
}

sealed trait EventEffect

case class Player(
  color: String,
  money: Int = 10000,
  position: Int = 0,
  inJail: Boolean = false
)

case class Street(
  name: String,
  owner: Option[String] = None,
  buildings: Int = 0,
  hotels: Int = 0,
  colorGroup: String
) extends Property

case class Railroad(
  name: String,
  owner: Option[String] = None
) extends Property

case class Utility(
  name: String,
  owner: Option[String] = None
) extends Property

case class Event(description: String, effect: EventEffect)


case object GoToJail extends EventEffect
case class MoveTo(position: Int) extends EventEffect
case class Earn(amount: Int) extends EventEffect
case class Pay(amount: Int) extends EventEffect
case class PayPerBuilding(houseCost: Int, hotelCost: Int) extends EventEffect
case class MoveSpaces(spaces: Int) extends EventEffect
