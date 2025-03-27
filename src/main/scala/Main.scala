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
  println(msg)
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
  text()

def msg = "I am a chess board compiled by Scala 3. :)"
def shaded = "\u2593"


