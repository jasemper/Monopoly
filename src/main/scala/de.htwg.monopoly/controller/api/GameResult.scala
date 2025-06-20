package de.htwg.monopoly.controller.api

sealed trait GameResult
case class Success(spacesMoved: Option[Int] = None) extends GameResult
case class Error(message: String) extends GameResult
