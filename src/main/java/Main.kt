fun main(args : Array<String>) {
    val mazeGenerator = MazeGenerator()
    println(mazeGenerator.toString())
    println("-------------------------------")

    val searcher = Searcher(Position(1,0), MazeGenerator.mazeArray)
    searcher.solveMaze()
}