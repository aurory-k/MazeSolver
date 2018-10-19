fun main(args: Array<String>) {
    println(Maze.STATIC_MAZE)
    val solver = Solver(Maze.STATIC_MAZE, Position(1, 0), Position(3, 1)).r
}