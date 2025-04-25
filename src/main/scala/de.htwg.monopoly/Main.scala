package de.htwg.monopoly

object Main {
  @main def hello(): Unit = {
    //print(helloMessage())
    helloMessage()
  }
  def helloMessage(): String = {
    "    Hello Players.\n  Wanna play a game?\n"
  }

  val Players: Vector[Player] = Vector(
    Player("Blue"),
    Player("Green"),
    Player("Yellow"),
    Player("Orange"),
    Player("Purple")
  )

  val Streets: Vector[Street] = Vector(
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

  val Trains: Vector[Railroad] = Vector(
    Railroad("Reading Railroad", None),
    Railroad("Pennsylvania Railroad", None),
    Railroad("B&O Railroad", None),
    Railroad("Short Line", None)
  )

  val Utilities: Vector[Utility] = Vector(
    Utility("Electric Company", None),
    Utility("Water Works", None)
  )

  val Events: Vector[Event] = Vector(
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

  val board: Vector[(Int, String)] = Vector(
    0 -> "Go",
    1 -> Streets(0).name,
    2 -> "Community Chest",
    3 -> Streets(1).name,
    4 -> "Income Tax",
    5 -> Trains(0).name,
    6 -> Streets(2).name,
    7 -> "Chance",
    8 -> Streets(3).name,
    9 -> Streets(4).name,
    10 -> "Jail / Just Visiting",
    11 -> Streets(5).name,
    12 -> Utilities(0).name,
    13 -> Streets(6).name,
    14 -> Streets(7).name,
    15 -> Trains(1).name,
    16 -> Streets(8).name,
    17 -> "Community Chest",
    18 -> Streets(9).name,
    19 -> Streets(10).name,
    20 -> "Free Parking",
    21 -> Streets(11).name,
    22 -> "Chance",
    23 -> Streets(12).name,
    24 -> Streets(13).name,
    25 -> Trains(2).name,
    26 -> Streets(14).name,
    27 -> Streets(15).name,
    28 -> Utilities(1).name,
    29 -> Streets(16).name,
    30 -> "Go To Jail",
    31 -> Streets(17).name,
    32 -> Streets(18).name,
    33 -> "Community Chest",
    34 -> Streets(19).name,
    35 -> Trains(3).name,
    36 -> "Chance",
    37 -> Streets(20).name,
    38 -> "Luxury Tax",
    39 -> Streets(21).name
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

  def giveOwner(player: Player, fieldnr: Int): (Vector[Street], Vector[Railroad], Vector[Utility]) = {
    val streetnrs = Array(1, 3, 6, 8, 9, 11, 13, 14, 16, 18, 19, 21, 23, 24, 26, 27, 29, 31, 32, 34, 37, 39)
    val trainnrs = Array(5, 15, 25, 35)

    val updatedStreets = 
    if (streetnrs contains fieldnr) {
      val streetIndex = streetnrs.indexOf(fieldnr)
      Streets.updated(streetIndex, Streets(streetIndex).copy(owner = Some(player.color)))
    } else Streets

    val updatedTrains = 
    if (trainnrs contains fieldnr) {
      val trainIndex = (fieldnr - 5) / 10
      Trains.updated(trainIndex, Trains(trainIndex).copy(owner = Some(player.color)))
    } else Trains

    val updatedUtilities = 
    if (fieldnr == 12 || fieldnr == 28) {
      val utilityIndex = fieldnr / 10 - 1
      Utilities.updated(utilityIndex, Utilities(utilityIndex).copy(owner = Some(player.color)))
    } else Utilities

    (updatedStreets, updatedTrains, updatedUtilities)
  }

  def showCurrentState(): Unit = {
    println("Current Player Status:")
    for (player <- Players) {
      println(s"Player ${player.color}: ${player.money} dollars")
    }

    println("\nGame Board Status:")

    for ((fieldNr, fieldName) <- board) {
      println(s"Field $fieldNr: $fieldName")

      val maybeStreet = Streets.find(_.name == fieldName)
      val maybeTrain = Trains.find(_.name == fieldName)
      val maybeUtility = Utilities.find(_.name == fieldName)

      (maybeStreet orElse maybeTrain orElse maybeUtility).foreach { prop =>
        println(s"  Owner: ${prop.owner.getOrElse("No owner")}")
      }

      maybeStreet.foreach { street =>
        println(s"  Houses: ${street.buildings}")
        println(s"  Hotels: ${street.hotels}")
      }

      val playersOnField = Players.filter(_.position == fieldNr).map(_.color)
      val playersString = if (playersOnField.isEmpty) "No one" else playersOnField.mkString(", ")
      println(s"  Players standing here: $playersString")
      println("-" * 40)
    }
  }

  def statusReport(players: Vector[Player], Streets: Vector[Street], Trains: Vector[Railroad], Utilities: Vector[Utility]):  Unit = {
    println("Current Player status:")
    for (player <- players) {
      println(f"Player ${player.color}%8s: ${player.money}%6d dollars at ${player.position}%2d")
    }
  }

  //showCurrentState()

  //TODO: implement getOwner from worksheet
  //TODO: implement ownsStreet from worksheet
  //TODO: implement drawCard + Events
}