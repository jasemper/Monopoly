import scala.util.Random

case class Player(color: String, money: Int, position: Int)
val players = Array(
  Player("Blue", 10000, 0),
  Player("Green", 10000, 0),
  Player("Yellow", 10000, 0),
  Player("Red", 10000, 0),
  Player("Purple", 10000, 0)
)
for (i <- 0 until 5){
  print(players(i))
}

case class Counter(number: Int)
val variables = Array(
  Counter(0), // Counter of Päsche
  Counter(0) //Counter for active Player
)

sealed trait Property {
  def name: String
  def owner: String
}

case class Street(name: String, owner: String, buildings: Int, hotels: Int, colorGroup: String) extends Property
case class Railroad(name: String, owner: String) extends Property
case class Utility(name: String, owner: String) extends Property

val Streets: Array[Street] = Array(
  Street("Mediterranean Avenue", "", 0, 0, "Brown"), // 0
  Street("Baltic Avenue", "", 0, 0, "Brown"),

  Street("Oriental Avenue", "", 0, 0, "Light Blue"), //2
  Street("Vermont Avenue", "", 0, 0, "Light Blue"),
  Street("Connecticut Avenue", "", 0, 0, "Light Blue"),

  Street("St. Charles Place", "", 0, 0, "Pink"), // 5
  Street("States Avenue", "", 0, 0, "Pink"),
  Street("Virginia Avenue", "", 0, 0, "Pink"),

  Street("St. James Place", "", 0, 0, "Orange"), // 8
  Street("Tennessee Avenue", "", 0, 0, "Orange"),
  Street("New York Avenue", "", 0, 0, "Orange"),

  Street("Kentucky Avenue", "", 0, 0, "Red"), // 11
  Street("Indiana Avenue", "", 0, 0, "Red"),
  Street("Illinois Avenue", "", 0, 0, "Red"),

  Street("Atlantic Avenue", "", 0, 0, "Yellow"), // 14
  Street("Ventnor Avenue", "", 0, 0, "Yellow"),
  Street("Marvin Gardens", "", 0, 0, "Yellow"),

  Street("Pacific Avenue", "", 0, 0, "Green"), // 17
  Street("North Carolina Avenue", "", 0, 0, "Green"),
  Street("Pennsylvania Avenue", "", 0, 0, "Green"),

  Street("Park Place", "", 0, 0, "Dark Blue"), //20
  Street("Boardwalk", "", 0, 0, "Dark Blue")
)

val Trains: Array[Railroad] = Array(
  Railroad("Reading Railroad", ""),
  Railroad("Pennsylvania Railroad", ""),
  Railroad("B&O Railroad", ""),
  Railroad("Short Line", "")
)

val Utilities: Array[Utility] = Array(
  Utility("Electric Company", ""),
  Utility("Water Works", "")
)

//throw dice // currentplayer = players(variables(1).number)


//move Player
if (players(variables(1).number).position != -1){
  var pos = (players(variables(1).number).position+5)%39
  if (players(variables(1).number).position+5 == 39){
    players(variables(1).number) = players(variables(1).number).copy(money = players(variables(1).number).money+400)
  } else if (players(variables(1).number).position+5 > 39){
    players(variables(1).number) = players(variables(1).number).copy(money = players(variables(1).number).money+200)
  }
  players(variables(1).number) = players(variables(1).number).copy(position = pos)
} else {
  //throwdice
  if (variables(0).number == 1){
    players(variables(1).number) = players(variables(1).number).copy(position = 10+5)
    variables(0) = variables(0).copy(number = 0)
  }
}

def rollDice(): Unit ={
  val dice1 = Random.nextInt(6) + 1
  val dice2 = Random.nextInt(6) + 1
  val dicecount = dice1+dice2
  if (dice1 == dice2){
    variables(0) = variables(0).copy(number = variables(0).number+1)
    if (variables(0).number == 3) {
      movePlayer(-1)
      variables(0) = variables(0).copy(number = 0)
    }
  } else {
    variables(0) = variables(0).copy(number = 0)
  }
} 

def addmoney(player: String, muula: Int): Unit = {
  //Use negative amounts to remove money
}

def movePlayer(tofield: Int): Unit = {
  if (tofield == 0){
    addmoney(players(variables(1).number).color, 400)
  } else if (tofield > players(variables(1).number).position) {
    addmoney(players(variables(1).number).color, 200)
  }
  players(variables(1).number) = players(variables(1).number).copy(position = tofield)
}

def getOwner(fieldnr: Int): String = {
  val streetnrs = Array(0,3,6,8,9,11,13,14,16,18,19,21,23,24,26,27,29,31,32,34,37,39)
  val trainnrs = Array(5,15,25,35)
  if (streetnrs contains fieldnr) {
    Streets(streetnrs.indexOf(fieldnr)).owner
  } else if (trainnrs contains fieldnr) {
    Trains((fieldnr-5)/10).owner
  } else if (fieldnr == 12 || fieldnr == 28) {
    Utilities(fieldnr/10-1).owner
  } else {
    "NFS" //Not for sale
  }
}

def giveOwner(player: Player, fieldnr: Int): Unit = {
  val streetnrs = Array(0,3,6,8,9,11,13,14,16,18,19,21,23,24,26,27,29,31,32,34,37,39)
  val trainnrs = Array(5,15,25,35)
  if (streetnrs contains fieldnr) {
    Streets(streetnrs.indexOf(fieldnr)) = Streets(streetnrs.indexOf(fieldnr)).copy(owner = player.color)
  } else if (trainnrs contains fieldnr) {
    Trains((fieldnr-5)/10) = Trains((fieldnr-5)/10).copy(owner = player.color)
  } else if (fieldnr == 12 || fieldnr == 28) {
    Utilities(fieldnr/10-1) = Utilities(fieldnr/10-1).copy(owner = player.color)
  }
}

def ownsFullSet(player: Player, color: String): Boolean = {
  val streetsInGroup = Streets.filter(_.colorGroup == color)
  streetsInGroup.forall(_.owner.contains(player))
}


// ---------------- TESTING--------------------------
// Example usage
val testPlayer = players(0) // Blue Player

giveOwner(testPlayer, 39)
giveOwner(testPlayer, 37)
print(Streets(11))

println(s"${testPlayer.color} owns full Dark Blue set: ${ownsFullSet(testPlayer, "Dark Blue")}")

val vrt = Char.MaxValue.toInt
