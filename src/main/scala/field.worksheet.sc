print("\u2593 ")
print("\u2593 ")
val colors = Array(
    "\u2593[33m",  // Brown
    "\u2593[94m",  // Light Blue
    "\u2593[95m",  // Pink
    "\u2593[91m",  // Orange
    "\u2593[31m",  // Red
    "\u2593[93m",  // Yellow
    "\u2593[32m",  // Green
    "\u2593[34m"   // Dark Blue
  )
  val reset = "\u2593[0m" // Reset color
  print(colors(0), colors(2))


  val fields = Array(
    "start", "brown", "extra", "brown", "pay 200", "train", "light blue", "extra", "light blue", "light blue",
    "visitor", "pink", "power facility", "pink", "pink", "train", "orange", "extra", "orange", "orange",
    "free parking", "red", "extra", "red", "red", "train", "yellow", "yellow", "power facility", "yellow",
    "to jail", "green", "green", "extra", "green", "train", "extra", "dark blue", "pay 100", "dark blue"
  ) 
  fields.length
  for (i <- 0 until 40){
    print(fields(i) + "\n")
  }