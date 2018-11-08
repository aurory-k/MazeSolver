import CellType.*

class MazeGenerator {
    fun generateMaze(numRows: Int, numCols: Int): Quad<Maze, Cell, Cell, String> {
        var maze = initializeMaze(numRows, numCols)

        val (start, startDirection) = selectStartAndEndCells(numRows, numCols)
        var endCell = Cell(Position(0,0), End)
        var mazeCells = listOf<Cell>()
        maze = maze.swap(start)

        var allFrontierCells = start.getFrontierCells(maze)

        while (allFrontierCells.isNotEmpty()) {
            val frontierCell = allFrontierCells.shuffled().first()

            val listOfNeighborCells = frontierCell.getNeighborCells(maze)

            if (listOfNeighborCells.isNotEmpty()) {
                var (neighborCell, direction) = listOfNeighborCells.shuffled().first()
                maze = maze.swap(frontierCell.position, Free)
                var (x, y) = neighborCell.position
                when (direction) {
                    "top" -> y++
                    "right" -> x--
                    "bottom" -> y--
                    "left" -> x++
                }
                neighborCell = maze.get(x, y)
                maze = maze.swap(neighborCell.position, Free)
                //println("-----------------------------------")
                //println(maze.toString())
                allFrontierCells = allFrontierCells.plus(frontierCell.getFrontierCells(maze))
                allFrontierCells = cullFrontierCells(allFrontierCells, maze)
                mazeCells = mazeCells.plus(frontierCell)
            }
            allFrontierCells = allFrontierCells.minus(frontierCell)
//            Thread.sleep(100)

            if (allFrontierCells.isEmpty()) {
                maze = maze.swap(frontierCell.position, End)
                endCell = Cell(frontierCell.position, End)
            }
        }

        //println("-----------------------------------")
        //println(maze.toString())

        return Quad(maze, start, endCell, startDirection)
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

    private fun selectStartAndEndCells(numRows: Int, numCols: Int): Pair<Cell, String> {
        val startSide = (1..4).shuffled().first()

        val (startX, startY, startDirection) = when (startSide) {
            1 -> {
                val startY = 0
                val startX = random(numRows)

                val startDirection = "down"
                Triple(startX, startY, startDirection)
            }
            2 -> {
                val startY = random(numCols)
                val startX = numRows - 1

                val startDirection = "left"
                Triple(startX, startY, startDirection)
            }
            3 -> {
                val startY = numCols - 1
                val startX = random(numRows)

                val startDirection = "up"
                Triple(startX, startY, startDirection)
            }
            4 -> {
                val startY = random(numCols)
                val startX = 0

                val startDirection = "right"
                Triple(startX, startY, startDirection)
            }
            else -> throw IllegalStateException()
        }

        return Pair(Cell(Position(startX, startY), Start), startDirection)
    }

    data class Quad<T1, T2, T3, T4>(val v1: T1, val v2: T2, val v3: T3, val v4: T4)

    private fun random(numRows: Int) = (1..numRows - 2).shuffled().first() % 2
}