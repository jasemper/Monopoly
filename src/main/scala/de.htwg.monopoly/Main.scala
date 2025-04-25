package de.htwg.monopoly

import scala.io.StdIn.readLine

object Main {
  @main def startGame(): Unit = {
    inputPoC()
  }


  val InitPlayers: Vector[Player] = Vector(
    Player("Blue"),
    Player("Green"),
    Player("Yellow"),
    Player("Orange"),
    Player("Purple")
  )

  val InitStreets: Vector[Street] = Vector(
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

  val InitTrains: Vector[Railroad] = Vector(
    Railroad("Reading Railroad", None),
    Railroad("Pennsylvania Railroad", None),
    Railroad("B&O Railroad", None),
    Railroad("Short Line", None)
  )

  val InitUtilities: Vector[Utility] = Vector(
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

  val Board: Vector[(Int, String)] = Vector(
    0 -> "Go",
    1 -> InitStreets(0).name,
    2 -> "Community Chest",
    3 -> InitStreets(1).name,
    4 -> "Income Tax",
    5 -> InitTrains(0).name,
    6 -> InitStreets(2).name,
    7 -> "Chance",
    8 -> InitStreets(3).name,
    9 -> InitStreets(4).name,
    10 -> "Jail / Just Visiting",
    11 -> InitStreets(5).name,
    12 -> InitUtilities(0).name,
    13 -> InitStreets(6).name,
    14 -> InitStreets(7).name,
    15 -> InitTrains(1).name,
    16 -> InitStreets(8).name,
    17 -> "Community Chest",
    18 -> InitStreets(9).name,
    19 -> InitStreets(10).name,
    20 -> "Free Parking",
    21 -> InitStreets(11).name,
    22 -> "Chance",
    23 -> InitStreets(12).name,
    24 -> InitStreets(13).name,
    25 -> InitTrains(2).name,
    26 -> InitStreets(14).name,
    27 -> InitStreets(15).name,
    28 -> InitUtilities(1).name,
    29 -> InitStreets(16).name,
    30 -> "Go To Jail",
    31 -> InitStreets(17).name,
    32 -> InitStreets(18).name,
    33 -> "Community Chest",
    34 -> InitStreets(19).name,
    35 -> InitTrains(3).name,
    36 -> "Chance",
    37 -> InitStreets(20).name,
    38 -> "Luxury Tax",
    39 -> InitStreets(21).name
  )


  def addMoney(Players: Vector[Player], index: Int, muula: Int): Vector[Player] = {
    val player = Players(index)
    val updatedPlayers = player.copy(money = player.money + muula)
    Players.updated(index, updatedPlayers)
  }

  def movePlayer(Players: Vector[Player], index: Int, spaces: Int): Vector[Player] = {
    val player = Players(index)
    val newPosition = (player.position + spaces) % 40
    val passedGo = (player.position + spaces) > 40
    val onGO = (player.position + spaces) == 40

    val updatedPlayers = if (onGO) {
      addMoney(Players, index, 400)
    } else if (passedGo) {
      addMoney(Players, index, 200)
    } else {
      Players
    }
    updatedPlayers.updated(index, updatedPlayers(index).copy(position = newPosition))
  }

  def getWinner(Players: Vector[Player]): String = {
  val remaining = Players.filter(_.money > 0)
  remaining match {
    case Vector(single) => single.color
    case _ => ""
  }
}

  def giveOwner(player: Player, fieldNr: Int, Streets: Vector[Street], Trains: Vector[Railroad], Utilities: Vector[Utility]): (Vector[Street], Vector[Railroad], Vector[Utility]) = {
    val streetnrs = Array(1, 3, 6, 8, 9, 11, 13, 14, 16, 18, 19, 21, 23, 24, 26, 27, 29, 31, 32, 34, 37, 39)
    val trainnrs = Array(5, 15, 25, 35)

    val updatedStreets = 
    if (streetnrs contains fieldNr) {
      val streetIndex = streetnrs.indexOf(fieldNr)
      Streets.updated(streetIndex, Streets(streetIndex).copy(owner = Some(player.color)))
    } else Streets

    val updatedTrains = 
    if (trainnrs contains fieldNr) {
      val trainIndex = (fieldNr - 5) / 10
      Trains.updated(trainIndex, Trains(trainIndex).copy(owner = Some(player.color)))
    } else Trains

    val updatedUtilities = 
    if (fieldNr == 12 || fieldNr == 28) {
      val utilityIndex = fieldNr / 10 - 1
      Utilities.updated(utilityIndex, Utilities(utilityIndex).copy(owner = Some(player.color)))
    } else Utilities

    (updatedStreets, updatedTrains, updatedUtilities)
  }

  def getOwner(fieldNr: Int, Streets: Vector[Street], Trains: Vector[Railroad], Utilities: Vector[Utility]): String = {
  if (fieldNr < 0 || fieldNr >= Board.length) return ""

  val fieldName = Board(fieldNr)._2

  Streets.find(_.name == fieldName).flatMap(_.owner)
    .orElse(Trains.find(_.name == fieldName).flatMap(_.owner))
    .orElse(Utilities.find(_.name == fieldName).flatMap(_.owner))
    .getOrElse("")
}
  def getHouses(fieldNr: Int, Streets: Vector[Street], Trains: Vector[Railroad], Utilities: Vector[Utility]): Int = {
    if (fieldNr < 0 || fieldNr >= Board.length) return 0
    val fieldName = Board(fieldNr)._2
    Streets.find(_.name == fieldName).map(_.buildings)
      .getOrElse(0)
  }
  def getHotels(fieldNr: Int, Streets: Vector[Street], Trains: Vector[Railroad], Utilities: Vector[Utility]): Int = {
    if (fieldNr < 0 || fieldNr >= Board.length) return 0
    val fieldName = Board(fieldNr)._2
    Streets.find(_.name == fieldName).map(_.hotels)
      .getOrElse(0)
  }

  def statusReport(Players: Vector[Player], Streets: Vector[Street], Trains: Vector[Railroad], Utilities: Vector[Utility]):  Unit = {
    println("Current Player Status:\n| Name    | Money | Pos | Jail |")
    for (player <- Players) {
      //println(f"Player ${player.color}%8s: ${player.money}%6d dollars at ${player.position}%2d")
      printf("| %-8s|%6d |  %2d | %5s|\n", player.color, player.money, player.position, player.inJail)
    }
    println("\nCurrent Game Board Status:\n| Nr | Field                 | Owner   | House | Hotel | Players on field")
    for ((fieldNr, fieldName) <- Board) {

      val maybeStreet = Streets.find(_.name == fieldName)
      val maybeTrain = Trains.find(_.name == fieldName)
      val maybeUtility = Utilities.find(_.name == fieldName)

      //(maybeStreet orElse maybeTrain orElse maybeUtility).foreach { prop =>  println(s"  Owner: ${prop.owner.getOrElse("No owner")}") }
      val owner = getOwner(fieldNr, Streets, Trains, Utilities) 
      val houses = getHouses(fieldNr, Streets, Trains, Utilities)
      val hotels = getHotels(fieldNr, Streets, Trains, Utilities)

      val playersOnField = Players.filter(_.position == fieldNr).map(_.color)
      val playersString = if (playersOnField.isEmpty) "" else playersOnField.mkString(", ")
      printf("| %2d | %-22s| %-8s|   %1d   |   %1d   | %-7s\n", fieldNr, fieldName, owner, houses, hotels, playersString)
    }
  }

  def inputPoC(): Unit = {
    var streets = InitStreets
    var trains = InitTrains
    var utilities = InitUtilities
    val player = Player("BetaTester")
    statusReport(InitPlayers, streets, trains, utilities)
    var input = ""
    while ({
      print("Enter property number to buy (or 'exit' to quit): ")
      input = scala.io.StdIn.readLine()
      input != "exit"
    }) {
      try {
        val num = input.toInt
        val (newStreets, newTrains, newUtilities) = giveOwner(player, num, streets, trains, utilities)
        streets = newStreets
        trains = newTrains
        utilities = newUtilities

        statusReport(InitPlayers, streets, trains, utilities)
      } catch {
        case _: NumberFormatException =>
          println("Please enter a valid number.")
      }
    }
  }


  //val player = Player("Blue")
  //val (updatedStreets, _, _) = Main.giveOwner(player, 14,)
  //statusReport(InitPlayers, updatedStreets, InitTrains, InitUtilities)
  //showCurrentState()

  //TODO: implement addbuilding
  //TODO: implement addhotel
  //TODO: implement ownsStreet from worksheet
  //TODO: implement drawCard + Events
}