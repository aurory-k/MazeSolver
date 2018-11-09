import CellType.*

const val START = "start"
const val NONE = "none"
const val UP = "up"
const val DOWN = "down"
const val RIGHT = "right"
const val LEFT = "left"

sealed class Direction() {
    object Start : Direction()
    object None : Direction()
    object Up : Direction()
    object Right : Direction()
    object Down : Direction()
    object Left : Direction()
}

class Crawler(
        private var position: Position,
        private var maze: Maze,
        private var direction: String = START,
        private val listOfVisitedPositions: ArrayList<Position>,
        private val listOfJunctions: ArrayList<Junction>
        ) {
    private var topCell: Cell = Cell(Position(position.x, position.y + 1), Wall)
    private var rightCell: Cell = Cell(Position(position.x + 1, position.y), Wall)
    private var bottomCell: Cell = Cell(Position(position.x, position.y - 1), Wall)
    private var leftCell: Cell = Cell(Position(position.x - 1, position.y), Wall)

    fun crawl(): Maze {
        if (Solver.endOfMaze.type != Boundary) {
            return maze
        }

        while (direction != NONE) {
            val (isEnd, cell) = checkForEnd()
            if (isEnd) {
                Solver.endOfMaze = maze.get(cell.position)
                return maze
            }
            if (isJunction()) {
                val junction = Junction(position, findJunctionDirections(listOfVisitedPositions))
                if (!listOfJunctions.contains(junction)) {
                    listOfJunctions.add(junction)
                    spawnSearcherFromJunction(junction, listOfVisitedPositions, listOfJunctions)
                }
            }
            maze = move()
            println("----------------------------")
            println(maze.toString())
        }

        return maze
    }

    private fun spawnSearcherFromJunction(junction: Junction, listOfVisitedPositions: ArrayList<Position>, listOfJunctions: ArrayList<Junction>) {
        for (it in junction.searchableDirections) {
            val searcher = Crawler(junction.position, maze, it, listOfVisitedPositions, listOfJunctions)
            searcher.crawl()
        }
    }

    private fun move(): Maze {
        if (direction == UP && topCell.isFree()) {
            position = position.copy(y = position.y - 1)
        } else if (direction == RIGHT && rightCell.isFree()) {
            position = position.copy(x = position.x + 1)
        } else if (direction == DOWN && bottomCell.isFree()) {
            position = position.copy(y = position.y + 1)
        } else if (direction == LEFT && leftCell.isFree()) {
            position = position.copy(x = position.x - 1)
        } else {
            direction = NONE
        }

        listOfVisitedPositions.add(position)
        return maze.swap(position, Visited)
    }

    private fun isJunction(): Boolean {
        updateCardinalCells()
        if (topCell.isFree() || bottomCell.isFree()) {
            return rightCell.isFree() || leftCell.isFree()
        }

        if (leftCell.isFree() || rightCell.isFree()) {
            return topCell.isFree() || bottomCell.isFree()
        }

        return false
    }

    private fun updateCardinalCells() {
        topCell = maze.get(position.x, position.y - 1)
        rightCell = maze.get(position.x + 1, position.y)
        bottomCell = maze.get(position.x, position.y + 1)
        leftCell = maze.get(position.x - 1, position.y)
    }

    private fun checkForEnd(): Pair<Boolean, Cell> {
        updateCardinalCells()
        return when (End) {
            topCell.type -> Pair(true, topCell)
            rightCell.type -> Pair(true, rightCell)
            bottomCell.type -> Pair(true, bottomCell)
            leftCell.type -> Pair(true, leftCell)
            else -> Pair(false, topCell)
        }
    }

    private fun findJunctionDirections(listOfVisitedPositions: ArrayList<Position>): ArrayList<String> {
        updateCardinalCells()
        val listOfJunctionDirections = ArrayList<String>()
        if (topCell.isFree() && !listOfVisitedPositions.contains(topCell.position)) {
            listOfJunctionDirections.add(UP)
        }
        if (rightCell.isFree() && !listOfVisitedPositions.contains(rightCell.position)) {
            listOfJunctionDirections.add(RIGHT)
        }
        if (bottomCell.isFree() && !listOfVisitedPositions.contains(bottomCell.position)) {
            listOfJunctionDirections.add(DOWN)
        }
        if (leftCell.isFree() && !listOfVisitedPositions.contains(leftCell.position)) {
            listOfJunctionDirections.add(LEFT)
        }

        return listOfJunctionDirections
    }
}