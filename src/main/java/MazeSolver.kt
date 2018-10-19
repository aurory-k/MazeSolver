sealed class Cell {
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
    return if (this == null){
        false
    } else {
        when (this) {
            Cell.Free -> true
            else -> false
        }
    }
}

fun Cell.isNotFree(): Boolean {
    return !isFree()
}

data class Position(var x: Int, var y: Int)

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
                arrayOf(Cell.Wall, Cell.Start, Cell.Wall, Cell.Wall),
                arrayOf(Cell.Wall, Cell.Free, Cell.Free, Cell.End),
                arrayOf(Cell.Wall, Cell.Free, Cell.Wall, Cell.Wall),
                arrayOf(Cell.Wall, Cell.Wall, Cell.Wall, Cell.Wall)
        )
        )
    }
}

class Solver(maze: Maze) {
    var endPosition = Position(2, 1)
    var staticMaze = maze.staticMaze

    fun findJunctions(start: Position) {
        var startDirection = determineStartDirection(start, staticMaze)
        var currentCell = staticMaze[start.y][start.x]

        while(currentCell.isFree() || currentCell == Cell.Start){
            println("Current Cell is at location (${start.x},${start.y}) with value of: $currentCell")
            when (startDirection) {
                "left" -> start.x--
                "right" -> start.x++
                "up" -> start.y--
                else -> start.y++
            }
            currentCell = staticMaze[start.y][start.x]
            when(isJunction(staticMaze, Position(start.x,start.y))){
                true -> println("Cell at (${start.x},${start.y}) is a junction")
            }
            println("New Cell is at location (${start.x},${start.y}) with value of: $currentCell")
            println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
        }
    }

    private fun determineStartDirection(start: Position, maze: Array<Array<out Cell>>): String{
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

    private fun isJunction(maze: Array<Array<out Cell>>, position: Position): Boolean {
        val centerCell = maze.getOrNull(position.y)?.getOrNull(position.x)
        val topCell = maze.getOrNull(position.y - 1)?.getOrNull(position.x)
        val rightCell = maze.getOrNull(position.y)?.getOrNull(position.x + 1)
        val bottomCell = maze.getOrNull(position.y + 1)?.getOrNull(position.x)
        val leftCell = maze.getOrNull(position.y)?.getOrNull(position.x - 1)

        if (centerCell?.isNotFree() == true) {
            println("Cell at position (${position.x},${position.y}) with value:$centerCell is not a junction because it is not free.")
            return false
        }

        if (leftCell?.isFree() == true || rightCell?.isFree() == true) {
            return topCell?.isFree()!! || bottomCell?.isFree()!!
        }

        if (topCell?.isFree() == true || bottomCell?.isFree() == true) {
            return leftCell?.isFree()!! || rightCell?.isFree()!!
        }

        println("Cell at position (${position.x},${position.y}) with value:$centerCell is not a junction because there are no orthogonal free cells.")
        return false
    }
}


fun main(args: Array<String>) {
    println(Maze.STATIC_MAZE)
    val solver = Solver(Maze.STATIC_MAZE)
    solver.findJunctions(Position(1, 0))
}