import scala.util.Random

// Create Players
case class Player(color: String, money: Int, position: Int)
val players = Array(
  Player("Blue", 10000, 0),
  Player("White", 10000, 0),
  Player("Yellow", 10000, 0),
  Player("Orange", 10000, 0),
  Player("Purple", 10000, 0)
)

//throw dice
var currentplayer = players(0)
val dice1 = Random.nextInt(6) + 1
val dice2 = Random.nextInt(6) + 1
val dicecount = dice1+dice2
var pasch = 0
if (dice1 == dice2){
  pasch = pasch + 1
  if (pasch == 3) {
    currentplayer = currentplayer.copy(position = -1)
    pasch = 0
  }
} else {
  pasch = 0
}

//move Player
if (currentplayer.position != -1){
  var pos = (currentplayer.position+dicecount)%39
  if (currentplayer.position+dicecount == 39){
    currentplayer = currentplayer.copy(money = currentplayer.money+400)
  } else if (currentplayer.position+dicecount > 39){
    currentplayer = currentplayer.copy(money = currentplayer.money+200)
  }
  currentplayer = currentplayer.copy(position = pos)
} else {
  //throwdice
  if (pasch == 1){
    currentplayer = currentplayer.copy(position = 10+dicecount)
    pasch = 0
  }
}
//addmoney


//Property Mother class
sealed trait Property {
  def name: String
  def owner: Option[Player]
}

// Create Streets building on Property
case class Street(name: String, var owner: Option[Player], var buildings: Int, var hotels: Int, colorGroup: String) extends Property
// Create Railroads building on Property
case class Railroad(name: String, var owner: Option[Player]) extends Property
// Create Utilities building on Property
case class Utility(name: String, var owner: Option[Player]) extends Property

// Array containing all Monopoly streets with color groups
val Streets: Array[Street] = Array(
  Street("Mediterranean Avenue", None, 0, 0, "Brown"),
  Street("Baltic Avenue", None, 0, 0, "Brown"),

  Street("Oriental Avenue", None, 0, 0, "Light Blue"),
  Street("Vermont Avenue", None, 0, 0, "Light Blue"),
  Street("Connecticut Avenue", None, 0, 0, "Light Blue"),

  Street("St. Charles Place", None, 0, 0, "Pink"),
  Street("States Avenue", None, 0, 0, "Pink"),
  Street("Virginia Avenue", None, 0, 0, "Pink"),

  Street("St. James Place", None, 0, 0, "Orange"),
  Street("Tennessee Avenue", None, 0, 0, "Orange"),
  Street("New York Avenue", None, 0, 0, "Orange"),

  Street("Kentucky Avenue", None, 0, 0, "Red"),
  Street("Indiana Avenue", None, 0, 0, "Red"),
  Street("Illinois Avenue", None, 0, 0, "Red"),

  Street("Atlantic Avenue", None, 0, 0, "Yellow"),
  Street("Ventnor Avenue", None, 0, 0, "Yellow"),
  Street("Marvin Gardens", None, 0, 0, "Yellow"),

  Street("Pacific Avenue", None, 0, 0, "Green"),
  Street("North Carolina Avenue", None, 0, 0, "Green"),
  Street("Pennsylvania Avenue", None, 0, 0, "Green"),

  Street("Park Place", None, 0, 0, "Dark Blue"),
  Street("Boardwalk", None, 0, 0, "Dark Blue")
)

// Array containing all Monopoly railroads
val Railroads: Array[Railroad] = Array(
  Railroad("Reading Railroad", None),
  Railroad("Pennsylvania Railroad", None),
  Railroad("B&O Railroad", None),
  Railroad("Short Line", None)
)

// Array containing all Monopoly utilities
val Utilities: Array[Utility] = Array(
  Utility("Electric Company", None),
  Utility("Water Works", None)
)

// Function to check if a player owns all streets of a color group
def ownsFullSet(player: Player, color: String): Boolean = {
  val streetsInGroup = Streets.filter(_.colorGroup == color)
  streetsInGroup.forall(_.owner.contains(player))
}


// ---------------- TESTING--------------------------
// Example usage
val testPlayer = players(0) // Blue Player

Streets.find(_.name == "Park Place").foreach(_.owner = Some(testPlayer))
Streets.find(_.name == "Boardwalk").foreach(_.owner = Some(testPlayer))

println(s"${testPlayer.color} owns full Dark Blue set: ${ownsFullSet(testPlayer, "Dark Blue")}")

val x = Char.MaxValue.toInt
