class Searcher(maze: Maze, position: Position){
    private var maze = maze
    private var mazeArray = Maze.STATIC_MAZE.staticMaze
    private var startPosition = position
    var currentDirection = updateDirection(position, mazeArray)

    fun searchFromJunction(position: Position, direction: String, maze: Array<Array<out Cell>>) {

    }

    fun findJunctions(): ArrayList<Position> {
        var currentCell = mazeArray[startPosition.y][startPosition.x]
        var listOfJunctions = ArrayList<Position>()

        while (currentCell.isFree() || currentCell == Cell.Start) {
            when (currentDirection) {
                "left" -> startPosition.x--
                "right" -> startPosition.x++
                "up" -> startPosition.y--
                else -> startPosition.y++
            }
            currentCell = mazeArray[startPosition.y][startPosition.x]
            when (maze.isJunction(Position(startPosition.x, startPosition.y))) {
                true -> listOfJunctions.add(Position(startPosition.x, startPosition.y))
            }
        }

        println(listOfJunctions.toString())
        return listOfJunctions
    }

    fun updateDirection(start: Position, maze: Array<Array<out Cell>>): String {
        val topCell = maze.getOrNull(start.y - 1)?.getOrNull(start.x)
        val rightCell = maze.getOrNull(start.y)?.getOrNull(start.x + 1)
        val leftCell = maze.getOrNull(start.y)?.getOrNull(start.x - 1)

        return when {
            leftCell?.isFree() == true -> "left"
            rightCell?.isFree() == true -> "right"
            topCell?.isFree() == true -> "up"
            else -> "down"
        }
    }
}