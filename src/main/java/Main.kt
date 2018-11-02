fun main(args : Array<String>) {
    val mazeGenerator = MazeGenerator()
    mazeGenerator.generateMaze(10,10)
    println(mazeGenerator.toString())
    println("-------------------------------")
}