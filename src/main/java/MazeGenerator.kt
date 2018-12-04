import CellType.*

object MazeGenerator {
    fun generateMaze(numRows: Int, numCols: Int, draw: (Maze) -> Unit): Triple<Maze, Cell, Cell> {
        var (maze, startCell, endCell) = initializeMaze(numRows, numCols)
        maze = maze.swap(startCell)

        var allFrontierCells = startCell.getFrontierCells(maze)

        while (allFrontierCells.isNotEmpty()) {
//            Thread.sleep(10)
            val (updatedMaze, updatedFrontierCells) = processNextMapChange(maze, allFrontierCells)
            maze = updatedMaze
            allFrontierCells = updatedFrontierCells
            draw(maze)
        }

        maze = maze.swap(endCell)
        draw(maze)

        return Triple(maze, startCell, endCell)
    }

    private fun processNextMapChange(maze: Maze, allFrontierCells: List<Cell>): Pair<Maze, List<Cell>> {
        var changedMaze = maze
        var newFrontierCells = allFrontierCells

        val frontierCell = newFrontierCells.shuffled().first()

        val listOfNeighborCells = frontierCell.getNeighborCells(changedMaze)

        if (listOfNeighborCells.isNotEmpty()) {
            val distanceToEndCell = calculateDistanceToEndOfMaze(frontierCell.position.x, frontierCell.position.y, changedMaze.end)
            changedMaze = changedMaze.swap(frontierCell.position, distanceToEndCell, Free)

            var (neighborCell, direction) = listOfNeighborCells.shuffled().first()
            changedMaze = neighborCell.freeAdjacentWall(changedMaze, direction)

            val retrievedFrontierCells = frontierCell.getFrontierCells(changedMaze)
            newFrontierCells = newFrontierCells.plus(retrievedFrontierCells)
            newFrontierCells = cullFrontierCells(newFrontierCells, changedMaze)

            changedMaze = updateMazeWithFrontierCells(changedMaze, retrievedFrontierCells)
        }

        newFrontierCells = newFrontierCells.minus(frontierCell)

        return Pair(changedMaze, newFrontierCells)
    }

    private fun updateMazeWithFrontierCells(maze: Maze, listOfFrontierCells: List<Cell>): Maze {
        var changedMaze = maze
        listOfFrontierCells.forEach {
            changedMaze = maze.swap(it)
        }

        return changedMaze
    }

    private fun initializeMaze(numRows: Int, numCols: Int): Triple<Maze, Cell, Cell> {
        val wallMaze = Array(numCols) { y ->
            Array(numRows) { x ->
                Cell(Position(x, y), (Double.MAX_VALUE * 100.0) / 100.0, Wall)
            }
        }

        val (startCell, endCell) = selectStartAndEndCells(numRows, numCols)
        val maze = Maze(wallMaze, startCell, endCell)

        return Triple(maze, startCell, endCell)
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

        val distanceToEndCell = calculateDistanceToEndOfMaze(x,y, maze.end)
        changedMaze = changedMaze.swap(Position(x, y), distanceToEndCell, Free)

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
        var topCell = maze.get(px, py - 2)
        var rightCell = maze.get(px + 2, py)
        var bottomCell = maze.get(px, py + 2)
        var leftCell = maze.get(px - 2, py)

        val frontierCells = ArrayList<Cell>()
        if (topCell.isNotFree()) {
            topCell = topCell.copy(type = CellType.Frontier)
            frontierCells.add(topCell)
        }
        if (rightCell.isNotFree()) {
            rightCell = rightCell.copy(type = CellType.Frontier)

            frontierCells.add(rightCell)
        }
        if (bottomCell.isNotFree()) {
            bottomCell = bottomCell.copy(type = CellType.Frontier)

            frontierCells.add(bottomCell)
        }
        if (leftCell.isNotFree()) {
            leftCell = leftCell.copy(type = CellType.Frontier)
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


                val endCell = Cell(Position(endX, endY), 0.0, End)

                val distanceToEndCell = calculateDistanceToEndOfMaze(startX, startY, endCell)
                val startCell = Cell(Position(startX, startY), distanceToEndCell, Start)

                Pair(startCell, endCell)
            }
            // Right
            2 -> {
                val startY = random(numCols)
                val startX = numRows - 1

                val endY = random(numCols)
                val endX = 0


                val endCell = Cell(Position(endX, endY), 0.0, End)

                val distanceToEndCell = calculateDistanceToEndOfMaze(startX, startY, endCell)
                val startCell = Cell(Position(startX, startY), distanceToEndCell, Start)

                Pair(startCell, endCell)
            }
            // Bottom
            3 -> {
                val startY = numCols - 1
                val startX = random(numRows)

                val endY = 0
                val endX = random(numRows)


                val endCell = Cell(Position(endX, endY), 0.0, End)

                val distanceToEndCell = calculateDistanceToEndOfMaze(startX, startY, endCell)
                val startCell = Cell(Position(startX, startY), distanceToEndCell, Start)

                Pair(startCell, endCell)
            }
            // Left
            4 -> {
                val startY = random(numCols)
                val startX = 0

                val endY = random(numCols)
                val endX = numRows - 1


                val endCell = Cell(Position(endX, endY), 0.0, End)

                val distanceToEndCell = calculateDistanceToEndOfMaze(startX, startY, endCell)
                val startCell = Cell(Position(startX, startY), distanceToEndCell, Start)

                Pair(startCell, endCell)
            }
            else -> throw IllegalStateException()
        }

        return Pair(startCell, endCell)
    }

    private fun calculateDistanceToEndOfMaze(x: Int, y: Int, end: Cell): Double {
        val endx = end.position.x.toDouble()
        val endy = end.position.y.toDouble()

        return Math.sqrt(Math.pow((endy - y), 2.0) + Math.pow((endx - x), 2.0)).round(2)
    }

    private fun Double.round(numberOfDecimalPlaces: Int): Double{
        val modifier = Math.pow(10.0,numberOfDecimalPlaces.toDouble())
        return (this * modifier) / modifier
    }

    data class Quad<T1, T2, T3, T4>(val v1: T1, val v2: T2, val v3: T3, val v4: T4)

    private fun random(num: Int) = (1 until num / 2).shuffled().first() * 2
}