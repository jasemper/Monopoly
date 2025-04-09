package de.htwg.monopoly

sealed trait Property {
  def name: String
  def owner: Option[String]
}

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
