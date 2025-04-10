package de.htwg.monopoly

object Main extends App {

  val players: Vector[Player] = Vector(
    Player("Blue"),
    Player("Green"),
    Player("Yellow"),
    Player("Orange"),
    Player("Purple")
  )

  val Streets: Array[Street] = Array(
    Street("Mediterranean Avenue", None, 0, 0, "Brown"), // 0
    Street("Baltic Avenue", None, 0, 0, "Brown"),

    Street("Oriental Avenue", None, 0, 0, "Light Blue"), // 2
    Street("Vermont Avenue", None, 0, 0, "Light Blue"),
    Street("Connecticut Avenue", None, 0, 0, "Light Blue"),

    Street("St. Charles Place", None, 0, 0, "Pink"), // 5
    Street("States Avenue", None, 0, 0, "Pink"),
    Street("Virginia Avenue", None, 0, 0, "Pink"),

    Street("St. James Place", None, 0, 0, "Orange"), // 8
    Street("Tennessee Avenue", None, 0, 0, "Orange"),
    Street("New York Avenue", None, 0, 0, "Orange"),

    Street("Kentucky Avenue", None, 0, 0, "Red"), // 11
    Street("Indiana Avenue", None, 0, 0, "Red"),
    Street("Illinois Avenue", None, 0, 0, "Red"),

    Street("Atlantic Avenue", None, 0, 0, "Yellow"), // 14
    Street("Ventnor Avenue", None, 0, 0, "Yellow"),
    Street("Marvin Gardens", None, 0, 0, "Yellow"),

    Street("Pacific Avenue", None, 0, 0, "Green"), // 17
    Street("North Carolina Avenue", None, 0, 0, "Green"),
    Street("Pennsylvania Avenue", None, 0, 0, "Green"),

    Street("Park Place", None, 0, 0, "Dark Blue"), // 20
    Street("Boardwalk", None, 0, 0, "Dark Blue")
  )

  val Trains: Array[Railroad] = Array(
    Railroad("Reading Railroad", None),
    Railroad("Pennsylvania Railroad", None),
    Railroad("B&O Railroad", None),
    Railroad("Short Line", None)
  )

  val Utilities: Array[Utility] = Array(
    Utility("Electric Company", None),
    Utility("Water Works", None)
  )

  val Events: Array[Event] = Array(
    Event("Advance to Go (Collect $200)", MoveTo(0)),
    Event("Go to Jail. Do not pass Go, do not collect $200", GoToJail),
    Event("Advance to Illinois Avenue", MoveTo(13)),
    Event("Advance to St. Charles Place", MoveTo(5)),
    Event("Bank pays you dividend of $50", Earn(50)),
    Event("Pay poor tax of $15", Pay(15)),
    Event("You have been elected Chairman of the Board. Pay each player $50", Pay(50)),
    Event("Make general repairs on all your property: For each house pay $25, for each hotel $100", PayPerBuilding(25, 100)),
    Event("Advance token to the Electric Company", MoveTo(12)),
    Event("Advance token to the Reading Railroad", MoveTo(7)),
    Event("Go back 3 spaces", MoveSpaces(-3)),
    Event("Advance to Boardwalk", MoveTo(21)),
    Event("Take a trip to Reading Railroad", MoveTo(7)),
    Event("Pay school fees of $150", Pay(150))
  )


  def addMoney(players: Vector[Player], index: Int, muula: Int): Vector[Player] = {
    val player = players(index)
    val updatedPlayer = player.copy(money = player.money + muula)
    players.updated(index, updatedPlayer)
  }

  def movePlayer(players: Vector[Player], index: Int, spaces: Int): Vector[Player] = {
    val player = players(index)
    val newPosition = (player.position + spaces) % 40
    val passedGo = (player.position + spaces) > 40
    val onGO = (player.position + spaces) == 40

    val updatedPlayers = if (onGO) {
      addMoney(players, index, 400)
    } else if (passedGo) {
      addMoney(players, index, 200)
    } else {
      players
    }
    updatedPlayers.updated(index, updatedPlayers(index).copy(position = newPosition))

  }

  def giveOwner(player: Player, fieldnr: Int): Unit = {
    // Define the street, railroad, and utility field numbers
    val streetnrs = Array(0, 3, 6, 8, 9, 11, 13, 14, 16, 18, 19, 21, 23, 24, 26, 27, 29, 31, 32, 34, 37, 39)
    val trainnrs = Array(5, 15, 25, 35)

    // Handling Street ownership
    if (streetnrs contains fieldnr) {
      val streetIndex = streetnrs.indexOf(fieldnr)
      Streets(streetIndex) = Streets(streetIndex).copy(owner = Some(player.color))
    } 
    // Handling Railroad ownership
    else if (trainnrs contains fieldnr) {
      val trainIndex = (fieldnr - 5) / 10
      Trains(trainIndex) = Trains(trainIndex).copy(owner = Some(player.color))
    } 
    // Handling Utility ownership
    else if (fieldnr == 12 || fieldnr == 28) {
      val utilityIndex = fieldnr / 10 - 1
      Utilities(utilityIndex) = Utilities(utilityIndex).copy(owner = Some(player.color))
    }
  }



  //Testing the code with print statements -- Testing the code with print statements -- Testing the code with print statements

  val player = Player("Blue")
  giveOwner(player, 0) // Should assign ownership of "Mediterranean Avenue" to "Blue"
  println(Streets(0))  // Should show that Mediterranean Avenue's owner is now "Blue"


  val updatedPlayers = movePlayer(players, 0, 5)
  println(updatedPlayers(0)) // Output updated player after move
}