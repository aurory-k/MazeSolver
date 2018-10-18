sealed class Cell() {
    object Free : Cell()
    object Wall : Cell()
    object Start : Cell()
    object End : Cell()

    override fun toString(): String {
        return when (this) {
            Cell.Free -> "0"
            Cell.Wall -> "1"
            Cell.Start -> "2"
            Cell.End -> "3"
        }
    }
}

fun Cell.isFree(): Boolean {
    return when (this) {
        Cell.Free -> true
        else -> false
    }
}

fun Cell.isNotFree(): Boolean {
    return !isFree()
}

data class Position(val x: Int, val y: Int)

class Maze(val staticMaze: Array<Array<out Cell>>) {

    override fun toString(): String {
        return this.staticMaze.joinToString(separator = "\n") { cells ->
            cells.joinToString(separator = "|", prefix = "|", postfix = "|") {
                it.toString()
            }
        }
    }

    companion object {
        val STATIC_MAZE = Maze(arrayOf(
                arrayOf(Cell.Wall, Cell.Free, Cell.Wall, Cell.Wall),
                arrayOf(Cell.Start, Cell.Free, Cell.Free, Cell.Wall),
                arrayOf(Cell.Wall, Cell.Free, Cell.Wall, Cell.Wall),
                arrayOf(Cell.Wall, Cell.Free, Cell.Free, Cell.End)
        )
        )
    }
}

class Solver(maze: Maze) {
    var startPosition = Position(0, 1)
    var endPosition = Position(2, 1)
    var staticMaze = maze.staticMaze

    fun findJunctions() {
        for (y in startPosition.y until staticMaze[0].size) {
            for (x in startPosition.x until staticMaze[0].size) {
                val currentCell = staticMaze[y][x]
                val currentPosition = Position(x, y)

                when (isJunction(staticMaze, currentPosition)) {
                    true -> println("Cell at ($y,$x) is a junction")
                }
            }
        }
    }

    private fun isJunction(maze: Array<Array<out Cell>>, position: Position): Boolean {
        val centerCell = maze.getOrNull(position.y)?.getOrNull(position.x)
        val topCell = maze.getOrNull(position.y - 1)?.getOrNull(position.x)
        val rightCell = maze.getOrNull(position.y)?.getOrNull(position.x + 1)
        val bottomCell = maze.getOrNull(position.y + 1)?.getOrNull(position.x)
        val leftCell = maze.getOrNull(position.y)?.getOrNull(position.x - 1)

        if (centerCell?.isNotFree() == true) {
            println("Cell at position (${position.y},${position.x}) with value:$centerCell is not a junction because it is not free.")
            return false
        }

        if (leftCell?.isFree() == true || rightCell?.isFree() == true) {
            if (topCell != null) {
                return topCell.isFree()
            }
            if (bottomCell != null) {
                return bottomCell.isFree()
            }
            return false
        }

        if (topCell?.isFree() == true || bottomCell?.isFree() == true) {
            if (leftCell != null) {
                return leftCell.isFree()
            }
            if (rightCell != null) {
                return rightCell.isFree()
            }
            return false
        }

        println("Cell at position (${position.y},${position.x}) with value:$centerCell is not a junction because there are no orthogonal free cells.")
        return false
    }
}


fun main(args: Array<String>) {
    println(Maze.STATIC_MAZE)
    var solver = Solver(Maze.STATIC_MAZE)
    solver.findJunctions()
}