data class Position(var x: Int, var y: Int)

class Maze(val staticMaze: Array<Array<out Cell>>) {

     fun isJunction(position: Position): Boolean {
        val centerCell = staticMaze.getOrNull(position.y)?.getOrNull(position.x)
        val topCell = staticMaze.getOrNull(position.y - 1)?.getOrNull(position.x)
        val rightCell = staticMaze.getOrNull(position.y)?.getOrNull(position.x + 1)
        val bottomCell = staticMaze.getOrNull(position.y + 1)?.getOrNull(position.x)
        val leftCell = staticMaze.getOrNull(position.y)?.getOrNull(position.x - 1)
        var listOfOpenDirections = ArrayList<String>()

        if (centerCell?.isNotFree() == true) {
            println("Cell at position (${position.x},${position.y}) with value:$centerCell is not a junction because it is not free.")
            return false
        }

        if (leftCell?.isFree() == true || rightCell?.isFree() == true) {
            if (topCell?.isFree()!!) {
                listOfOpenDirections.add("up")
            }

        }

        if (topCell?.isFree() == true || bottomCell?.isFree() == true) {
            return leftCell?.isFree()!! || rightCell?.isFree()!!
        }

        println("Cell at position (${position.x},${position.y}) with value:$centerCell is not a junction because there are no orthogonal free cells.")
        return false
    }

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