fun main(args : Array<String>) {
    val mazeGenerator = MazeGenerator()
    mazeGenerator.initializeMaze(5 , 5)
    mazeGenerator.generateMaze()
}