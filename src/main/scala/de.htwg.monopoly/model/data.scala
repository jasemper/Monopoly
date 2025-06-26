package de.htwg.monopoly.model

object data {
  val InitPlayers: Vector[Player] = Vector(
    Player("Blue"),
    Player("Green"),
    Player("Yellow"),
    Player("Orange"),
    Player("Purple")
  )

  val InitStreets: Vector[Street] = Vector(
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

  val InitTrains: Vector[Train] = Vector(
    Train("Reading Railroad", None),
    Train("Pennsylvania Railroad", None),
    Train("B&O Railroad", None),
    Train("Short Line", None)
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
    2 -> "Event",
    3 -> InitStreets(1).name,
    4 -> "Income Tax",
    5 -> InitTrains(0).name,
    6 -> InitStreets(2).name,
    7 -> "Event",
    8 -> InitStreets(3).name,
    9 -> InitStreets(4).name,
    10 -> "Jail / Just Visiting",
    11 -> InitStreets(5).name,
    12 -> InitUtilities(0).name,
    13 -> InitStreets(6).name,
    14 -> InitStreets(7).name,
    15 -> InitTrains(1).name,
    16 -> InitStreets(8).name,
    17 -> "Event",
    18 -> InitStreets(9).name,
    19 -> InitStreets(10).name,
    20 -> "Free Parking",
    21 -> InitStreets(11).name,
    22 -> "Event",
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
    33 -> "Event",
    34 -> InitStreets(19).name,
    35 -> InitTrains(3).name,
    36 -> "Event",
    37 -> InitStreets(20).name,
    38 -> "Luxury Tax",
    39 -> InitStreets(21).name
  )
}