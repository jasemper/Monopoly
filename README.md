## BEFORE PUSHING, PULL
## Monopoly written in Scala 3

## Files in main

### Main.scala
gives the frame from model.scala values and/or implementation
### main.worksheet.sc
Almost everything rough draft
### model.scala
creates the frame of the programm
### TUI_Ideas.scala
prints a Monopoly-playingfield to console
### TUI.worksheet.sc
Nothing important yet. will be more important, when game logic is transferred to the visual console output

## files in test

### Monopolytester.scala
A tester programm (`sbt test`) to check if functions do what they'Re supposed to do


## Usage
`sbt test` runs the tests
This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.
For more information on the sbt-dotty plugin, see the
[scala3-example-project](https://github.com/scala/scala3-example-project/blob/main/README.md).
