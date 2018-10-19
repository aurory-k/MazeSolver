class Solver(maze: Maze, startPosition: Position, endPosition: Position) {
    var maze = maze
    var startPosition = startPosition
    var endPosition = endPosition

    fun run() {
        var mySearcher = Searcher(maze, startPosition)
    }
}