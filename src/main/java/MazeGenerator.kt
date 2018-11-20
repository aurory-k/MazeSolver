import CellType.*

object MazeGenerator {
    fun generateMaze(numRows: Int, numCols: Int, draw: (Maze) -> Unit): Triple<Maze, Cell, Cell> {
        var maze = initializeMaze(numRows, numCols)

        val (startCell, endCell) = selectStartAndEndCells(numRows, numCols)
        maze = maze.swap(startCell)

        var allFrontierCells = startCell.getFrontierCells(maze)

        while (allFrontierCells.isNotEmpty()) {
//            Thread.sleep(5)
            val (updatedMaze, updatedFrontierCells) = processNextMapChange(maze, allFrontierCells)
            maze = updatedMaze
            allFrontierCells = updatedFrontierCells
            draw(maze)
        }

        maze = maze.swap(endCell)

        return Triple(maze, startCell, endCell)
    }

    private fun processNextMapChange(maze: Maze, allFrontierCells: List<Cell>): Pair<Maze, List<Cell>> {
        var changedMaze = maze
        var newFrontierCells = allFrontierCells

        val frontierCell = newFrontierCells.shuffled().first()

        val listOfNeighborCells = frontierCell.getNeighborCells(changedMaze)

        if (listOfNeighborCells.isNotEmpty()) {
            changedMaze = changedMaze.swap(frontierCell.position, Free)

            var (neighborCell, direction) = listOfNeighborCells.shuffled().first()
            changedMaze = neighborCell.freeAdjacentWall(changedMaze, direction)

            newFrontierCells = newFrontierCells.plus(frontierCell.getFrontierCells(changedMaze))
            newFrontierCells = cullFrontierCells(newFrontierCells, changedMaze)
        }

        newFrontierCells = newFrontierCells.minus(frontierCell)

        return Pair(changedMaze, newFrontierCells)
    }

    private fun initializeMaze(numRows: Int, numCols: Int): Maze {
        val wallMaze = Array(numCols) { y ->
            Array(numRows) { x ->
                Cell(Position(x, y), Wall)
            }
        }
        return Maze(wallMaze)
    }

    private fun cullFrontierCells(listOfFrontierCells: List<Cell>, maze: Maze): List<Cell> {
        return listOfFrontierCells.filter { frontierCell ->
            val mazeCell = maze.get(frontierCell.position)
            mazeCell.isNotFree()
        }
    }

    private fun Cell.freeAdjacentWall(maze: Maze, direction: String): Maze {
        var changedMaze = maze

        var (x, y) = position
        when (direction) {
            "top" -> y++
            "right" -> x--
            "bottom" -> y--
            "left" -> x++
        }
        changedMaze = changedMaze.swap(Position(x, y), Free)

        return changedMaze
    }

    private fun Cell.getNeighborCells(maze: Maze): List<Pair<Cell, String>> {
        val (px, py) = position
        val topCell = maze.get(px, py - 2)
        val rightCell = maze.get(px + 2, py)
        val bottomCell = maze.get(px, py + 2)
        val leftCell = maze.get(px - 2, py)

        val neighborCells = ArrayList<Pair<Cell, String>>()
        if (topCell.isFree() && py - 2 >= 0) {
            neighborCells.add(Pair(topCell, "top"))
        }

        if (rightCell.isFree() && px + 2 < maze.getRowSize()) {
            neighborCells.add(Pair(rightCell, "right"))
        }

        if (leftCell.isFree() && px - 2 >= 0) {
            neighborCells.add(Pair(leftCell, "left"))
        }

        if (bottomCell.isFree() && py + 2 < maze.getColSize()) {
            neighborCells.add(Pair(bottomCell, "bottom"))
        }

        return neighborCells
    }

    private fun Cell.getFrontierCells(maze: Maze): List<Cell> {
        val (px, py) = position
        val topCell = maze.get(px, py - 2)
        val rightCell = maze.get(px + 2, py)
        val bottomCell = maze.get(px, py + 2)
        val leftCell = maze.get(px - 2, py)

        val frontierCells = ArrayList<Cell>()
        if (topCell.isNotFree()) {
            frontierCells.add(topCell)
        }
        if (rightCell.isNotFree()) {
            frontierCells.add(rightCell)
        }
        if (bottomCell.isNotFree()) {
            frontierCells.add(bottomCell)
        }
        if (leftCell.isNotFree()) {
            frontierCells.add(leftCell)
        }

        return frontierCells
    }

    private fun selectStartAndEndCells(numRows: Int, numCols: Int): Pair<Cell, Cell> {
        val startSide = (1..4).shuffled().first()

        val (startCell, endCell) = when (startSide) {
            // Top
            1 -> {
                val startY = 0
                val startX = random(numRows)

                val endY = numCols - 1
                val endX = random(numRows)

                val startCell = Cell(Position(startX, startY), Start)
                val endCell = Cell(Position(endX, endY), End)

                Pair(startCell, endCell)
            }
            // Right
            2 -> {
                val startY = random(numCols)
                val startX = numRows - 1

                val endY = random(numCols)
                val endX = 0

                val startCell = Cell(Position(startX, startY), Start)
                val endCell = Cell(Position(endX, endY), End)

                Pair(startCell, endCell)
            }
            // Bottom
            3 -> {
                val startY = numCols - 1
                val startX = random(numRows)

                val endY = 0
                val endX = random(numRows)

                val startCell = Cell(Position(startX, startY), Start)
                val endCell = Cell(Position(endX, endY), End)

                Pair(startCell, endCell)
            }
            // Left
            4 -> {
                val startY = random(numCols)
                val startX = 0

                val endY = random(numCols)
                val endX = numRows - 1

                val startCell = Cell(Position(startX, startY), Start)
                val endCell = Cell(Position(endX, endY), End)

                Pair(startCell, endCell)
            }
            else -> throw IllegalStateException()
        }

        return Pair(startCell, endCell)
    }

    data class Quad<T1, T2, T3, T4>(val v1: T1, val v2: T2, val v3: T3, val v4: T4)

    private fun random(numRows: Int) = (1..numRows - 2).shuffled().first()
}