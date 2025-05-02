[![Coverage Status](https://coveralls.io/repos/github/jasemper/Monopoly/badge.svg?branch=master)](https://coveralls.io/github/jasemper/Monopoly?branch=master)

## BEFORE PUSHING, PULL
## Monopoly written in Scala 3

## Files in main

### data.scala
contains all the default data the game needs at start to fill the classes defined in model.scala
### functions.scala
contains all the functions needed to run the game and change stuff
### Main.scala
uses the inputProofofConcept from tui.scala to give properties to Player "BetaTester"
### main.worksheet.sc
Almost everything rough draft
### model.scala
creates the frame of the programm and sets up all the classes
### tui.scala
prints a deconstructed version of the Monopoly-playingfield to console

## files in test

### Monopolytester.scala
A tester programm (`sbt test`) to check if functions do what they'Re supposed to do


## Usage
'sbt test' runs the tests
'sbt run' starts the main class that uses the input test to give properties to Player "BetaTester"
This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.
For more information on the sbt-dotty plugin, see the
[scala3-example-project](https://github.com/scala/scala3-example-project/blob/main/README.md).
