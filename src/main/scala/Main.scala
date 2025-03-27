@main def hello(): Unit =
  def text(): Unit = {
    print("  __      __                           ____           _             \n")
    print(" |  \\    /  |                         |  _ \\         | |            \n")
    print(" |   \\  /   |   ___    _____    ___   | |_| |  ___   | |   _    _   \n")
    print(" | |\\ \\/ /| |  / _ \\  |  _  \\  / _ \\  |  __/  / _ \\  | |  \\ \\  / /  \n")
    print(" | | \\__/ | | | |_| | | | | | | |_| | | |    | |_| | | |_  \\ \\/ /   \n")
    print(" |_|      |_|  \\___/  |_| |_|  \\___/  |_|     \\___/  \\__|   \\  /    \n")
    print("                                                            / /     \n")
    print("                                                           /_/      \n")
  }
  println("Hello world!")
  //MonoPoly text
  text()
  //Monopoly field
  println(monofield + "\n \n")
  def chess(): Unit = {
    //Chess field
    //P Pawn
    //R Rook
    //K Knight
    //B Bishop
    //Q Queen
    //M King
    for (i<-0 until 8) {
      for (j<-0 until 8){
        if ((i+j) %2 == 0){
          print(shaded)
        } else {
          print(" ")
        }
      }
      print("\n")
    }
  }

def msg = "I am a chess board compiled by Scala 3. :)"
def shaded = "\u2593"
def monofield= """┌─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┐
│         │         │         │         │         │         │         │         │         │         │         │
│         │         │         │         │         │         │         │         │         │         │         │
│         │         │         │         │         │         │         │         │         │         │         │
│  START  │  BROWN  │  Draw   │  BROWN  │ pay 200 │  TRAIN  │  BLUE   │  Draw   │  BLUE   │  BLUE   │  Visit  │
├─────────┼─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┼─────────┤
│         │                                                                                         │         │
│         │                                                                                         │         │
│         │                                                                                         │         │
│ PURPLE  │                                                                                         │  PINK   │
├─────────┤                                                                                         ├─────────┤
│         │                                                                                         │         │
│         │                                                                                         │         │
│         │                                                                                         │         │
│ pay 100 │                                                                                         │  Power  │
├─────────┤                                                                                         ├─────────┤
│         │                                                                                         │         │
│         │                                                                                         │         │
│         │                                                                                         │         │
│ PURPLE  │                                                                                         │  PINK   │
├─────────┤                                                                                         ├─────────┤
│         │                                                                                         │         │
│         │                                                                                         │         │
│         │                                                                                         │         │
│  Extra  │                                                                                         │  PINK   │
├─────────┤                                                                                         ├─────────┤
│         │                                                                                         │         │
│         │                                                                                         │         │
│         │                                                                                         │         │
│  TRAIN  │                                                                                         │  TRAIN  │
├─────────┤                                                                                         ├─────────┤
│         │                                                                                         │         │
│         │                                                                                         │         │
│         │                                                                                         │         │
│  GREEN  │                                                                                         │ ORANGE  │
├─────────┤                                                                                         ├─────────┤
│         │                                                                                         │         │
│         │                                                                                         │         │
│         │                                                                                         │         │
│  Extra  │                                                                                         │  Extra  │
├─────────┤                                                                                         ├─────────┤
│         │                                                                                         │         │
│         │                                                                                         │         │
│         │                                                                                         │         │
│  GREEN  │                                                                                         │ ORANGE  │
├─────────┤                                                                                         ├─────────┤
│         │                                                                                         │         │
│         │                                                                                         │         │
│         │                                                                                         │         │
│  GREEN  │                                                                                         │ ORANGE  │
├─────────┼─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┬─────────┼─────────┤
│         │         │         │         │         │         │         │         │         │         │         │
│         │         │         │         │         │         │         │         │         │         │         │
│         │         │         │         │         │         │         │         │         │         │         │
│ To Jail │ YELLOW  │  Power  │ YELLOW  │ YELLOW  │  TRAIN  │   RED   │   RED   │  Extra  │   RED   │  Free   │
└─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┴─────────┘"""
// special ascii by https://www.christianlehmann.eu/sonderzeichen/?open=linien


