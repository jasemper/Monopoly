[![Coverage Status](https://coveralls.io/repos/github/jasemper/Monopoly/badge.svg?branch=master)](https://coveralls.io/github/jasemper/Monopoly?branch=master)
![GitHub last commit](https://img.shields.io/github/last-commit/jasemper/Monopoly)

# Monopoly written in Scala 3

# ToDo:
## TOFIX:

## Abgabe:

## Implement:
### Build only when owned street and on field
### Correct property values
### Rent increase with hotels, houses, full streets, both companies
### Roll again (only) after pasch
### (no-pasch -> pasch)
### Player can roll dice
### Player can goto Jail (field)
### Player can leave Jail (money after 3 rounds)
### Create a GUI
### Implement additional Event fields


# Usage
'sbt test' runs the tests (I hope they alll work)
'sbt run' starts the main class that uses the input to let you play the game without money nor end
This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.
For more information on the sbt-dotty plugin, see the
[scala3-example-project](https://github.com/scala/scala3-example-project/blob/main/README.md).
