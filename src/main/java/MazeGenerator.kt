import CellType.*

class MazeGenerator {
    fun generateMaze(numRows: Int, numCols: Int): Quad<Maze, Cell, Cell, String> {
        var maze = initializeMaze(numRows, numCols)

        val (start, end, startDirection) = selectStartAndEndCells(numRows, numCols)

        var visitedCells = listOf(start)

        maze = maze.swap(start)

        var allEdges = getNewEdges(start, maze, visitedCells)

        while (allEdges.isNotEmpty()) {
            val currentCell = allEdges.shuffled().first()

            println("-----------------------------------")
            println(maze.toString())

            if (!visitedCells.contains(currentCell) && numberOfFreeNeighborCells(currentCell, maze) < 2) {
                maze = maze.swap(currentCell.position, Free)
                allEdges = allEdges.plus(getNewEdges(currentCell, maze, visitedCells))
                visitedCells = visitedCells.plus(currentCell)
            } else {
                maze = maze.swap(currentCell.position, Wall)

            }
            allEdges = allEdges.minus(currentCell)
//            Thread.sleep(100)
        }

        maze = maze.swap(end)

        println("-----------------------------------")
        println(maze.toString())

        return Quad(maze, start, end, startDirection)
    }

    private fun initializeMaze(numRows: Int, numCols: Int): Maze {
        val wallMaze = Array(numCols) { y ->
            Array(numRows) { x ->
                Cell(Position(x, y), Wall)
            }
        }

        return Maze(wallMaze)
    }

    private fun getNewEdges(currentCell: Cell, maze: Maze, visitedCells: List<Cell>): List<Cell> {
        val (px, py) = currentCell.position
        val topCell = maze.get(px, py - 1)
        val rightCell = maze.get(px + 1, py)
        val bottomCell = maze.get(px, py + 1)
        val leftCell = maze.get(px - 1, py)

        val newEdges = ArrayList<Cell>()
        if (topCell.isNotFree() && !newEdges.contains(topCell) && !visitedCells.contains(topCell)) {
            newEdges.add(topCell)
        }
        if (rightCell.isNotFree() && !newEdges.contains(rightCell) && !visitedCells.contains(rightCell)) {
            newEdges.add(rightCell)
        }
        if (bottomCell.isNotFree() && !newEdges.contains(bottomCell) && !visitedCells.contains(bottomCell)) {
            newEdges.add(bottomCell)
        }
        if (leftCell.isNotFree() && !newEdges.contains(leftCell) && !visitedCells.contains(leftCell)) {
            newEdges.add(leftCell)
        }

        return newEdges
    }

    private fun numberOfFreeNeighborCells(cell: Cell, maze: Maze): Int {
        val (px, py) = cell.position
        val topCell = maze.get(px, py - 1)
        val rightCell = maze.get(px + 1, py)
        val bottomCell = maze.get(px, py + 1)
        val leftCell = maze.get(px - 1, py)
        var numberOfFreeCells = 0

        if (topCell.isFree()) {
            numberOfFreeCells += 1
        }

        if (rightCell.isFree()) {
            numberOfFreeCells += 1
        }

        if (bottomCell.isFree()) {
            numberOfFreeCells += 1
        }

        if (leftCell.isFree()) {
            numberOfFreeCells += 1
        }

        return numberOfFreeCells
    }

    private fun selectStartAndEndCells(numRows: Int, numCols: Int): Triple<Cell, Cell, String> {
        val startSide = (1..4).shuffled().first()

        val (startX, startY, endX, endY, startDirection) = when (startSide) {
            1 -> {
                val startY = 0
                val startX = random(numRows)

                val endY = numCols - 1
                val endX = random(numRows)

                val startDirection = "down"
                Quint(startX, startY, endX, endY, startDirection)
            }
            2 -> {
                val startY = random(numCols)
                val startX = numRows - 1

                val endY = random(numCols)
                val endX = 0

                val startDirection = "left"
                Quint(startX, startY, endX, endY, startDirection)
            }
            3 -> {
                val startY = numCols - 1
                val startX = random(numRows)

                val endY = 0
                val endX = random(numRows)

                val startDirection = "up"
                Quint(startX, startY, endX, endY, startDirection)
            }
            4 -> {
                val startY = random(numCols)
                val startX = 0

                val endY = random(numCols)
                val endX = numRows - 1

                val startDirection = "right"
                Quint(startX, startY, endX, endY, startDirection)
            }
            else -> throw IllegalStateException()
        }

        return Triple(Cell(Position(startX, startY), Start), Cell(Position(endX, endY), End), startDirection)
    }

    data class Quad<T1, T2, T3, T4>(val v1: T1, val v2: T2, val v3: T3, val v4: T4)
    private data class Quint<T1, T2, T3, T4, T5>(val v1: T1, val v2: T2, val v3: T3, val v4: T4, val v5: T5)

    private fun random(numRows: Int) = (1..numRows - 2).shuffled().first()
}