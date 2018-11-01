fun main(args : Array<String>) {
    val mazeGenerator = MazeGenerator()
    println(mazeGenerator.toString())
    println("-------------------------------")

    val searcher = Searcher(Position(3,6), MazeGenerator.mazeArray)
    searcher.solveMaze()
}