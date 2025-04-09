package de.htwg.monopoly

object Main extends App {

  val players: Vector[Player] = Vector(
    Player("Blue"),
    Player("Green"),
    Player("Yellow"),
    Player("Orange"),
    Player("Purple")
  )

  // Move the active player (e.g., player 0) forward 5 positions
  def movePlayer(players: Vector[Player], index: Int, spaces: Int): Vector[Player] = {
    val player = players(index)
    val newPosition = (player.position + spaces) % 40
    val passedGo = (player.position + spaces) >= 40
    val newMoney = if (passedGo) player.money + 200 else player.money
    players.updated(index, player.copy(position = newPosition, money = newMoney))
  }

  val updatedPlayers = movePlayer(players, 0, 5)
  println(updatedPlayers(0)) // See the new state of the moved player
}