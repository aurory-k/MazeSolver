import CellType.*

class MazeGenerator {
    fun generateMaze(numRows: Int, numCols: Int): Quad<Maze, Cell, Cell, String> {
        var maze = initializeMaze(numRows, numCols)

        val (start, end, startDirection) = selectStartAndEndCells(numRows, numCols)
        var visitedCells = listOf<Cell>()
        maze = maze.swap(start)

        var allFrontierCells = getNewFrontierCells(start, maze, visitedCells)

        while (allFrontierCells.isNotEmpty()) {
            val currentCell = allFrontierCells.shuffled().first()
            var listOfNeighborCells = getFrontierNeighborCells(currentCell, maze, visitedCells)

            if (listOfNeighborCells.isNotEmpty()) {
                var (neighborCell, direction) = listOfNeighborCells.shuffled().first()
                var (x, y) = neighborCell.position
                when (direction) {
                    "top" -> y++
                    "right" -> x--
                    "bottom" -> y--
                    "left" -> x++
                }
                neighborCell = maze.get(x, y)
                if(numberOfFreeNeighborCells(neighborCell, maze) < 2){
                    maze = maze.swap(neighborCell.position, Free)
                }
                println("-----------------------------------")
                println(maze.toString())
                allFrontierCells = allFrontierCells.plus(getNewFrontierCells(neighborCell, maze, visitedCells))
                visitedCells = visitedCells.plus(currentCell)
            }
            allFrontierCells = allFrontierCells.minus(currentCell)
//            Thread.sleep(100)

            if(allFrontierCells.isEmpty()){
                maze = maze.swap(currentCell.position, End)
            }
        }

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

    private fun getFrontierNeighborCells(currentCell: Cell, maze: Maze, visitedCells: List<Cell>): List<Pair<Cell, String>> {
        val (px, py) = currentCell.position
        val topCell = maze.get(px, py - 2)
        val rightCell = maze.get(px + 2, py)
        val bottomCell = maze.get(px, py + 2)
        val leftCell = maze.get(px - 2, py)

        val neighborCells = ArrayList<Pair<Cell, String>>()
        if (topCell.isFree() && !visitedCells.contains(topCell) &&  py - 2 >= 0) {
            neighborCells.add(Pair(topCell, "top"))
        }

        if (rightCell.isFree() && !visitedCells.contains(rightCell) && px + 2 < maze.getRowSize()) {
            neighborCells.add(Pair(rightCell, "right"))
        }

        if (leftCell.isFree() && !visitedCells.contains(leftCell) && px - 2 >= 0) {
            neighborCells.add(Pair(leftCell, "left"))
        }

        if (bottomCell.isFree() && !visitedCells.contains(bottomCell) && py + 2 < maze.getColSize()) {
            neighborCells.add(Pair(bottomCell, "bottom"))
        }

        return neighborCells
    }

    private fun getNewFrontierCells(currentCell: Cell, maze: Maze, visitedCells: List<Cell>): List<Cell> {
        val (px, py) = currentCell.position
        val topCell = maze.get(px, py - 2)
        val rightCell = maze.get(px + 2, py)
        val bottomCell = maze.get(px, py + 2)
        val leftCell = maze.get(px - 2, py)

        val frontierCells = ArrayList<Cell>()
        if (topCell.isNotFree() && !frontierCells.contains(topCell) && !visitedCells.contains(topCell)) {
            frontierCells.add(topCell)
        }
        if (rightCell.isNotFree() && !frontierCells.contains(rightCell) && !visitedCells.contains(rightCell)) {
            frontierCells.add(rightCell)
        }
        if (bottomCell.isNotFree() && !frontierCells.contains(bottomCell) && !visitedCells.contains(bottomCell)) {
            frontierCells.add(bottomCell)
        }
        if (leftCell.isNotFree() && !frontierCells.contains(leftCell) && !visitedCells.contains(leftCell)) {
            frontierCells.add(leftCell)
        }

        return frontierCells
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