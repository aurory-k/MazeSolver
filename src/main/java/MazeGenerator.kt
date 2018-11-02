import CellType.*

class MazeGenerator {
    companion object {
        var mazeArray = arrayOf(
                arrayOf(Cell(Position(0, 0), Wall), Cell(Position(1, 0), End), Cell(Position(2, 0), Wall), Cell(Position(3, 0), Wall), Cell(Position(4, 0), Wall), Cell(Position(5, 0), Wall), Cell(Position(6, 0), Wall)),
                arrayOf(Cell(Position(0, 1), Wall), Cell(Position(1, 1), Free), Cell(Position(2, 1), Wall), Cell(Position(3, 1), Wall), Cell(Position(4, 1), Free), Cell(Position(5, 1), Wall), Cell(Position(6, 1), Wall)),
                arrayOf(Cell(Position(0, 2), Wall), Cell(Position(1, 2), Free), Cell(Position(2, 2), Free), Cell(Position(3, 2), Wall), Cell(Position(4, 2), Free), Cell(Position(5, 2), Free), Cell(Position(6, 2), Wall)),
                arrayOf(Cell(Position(0, 3), Wall), Cell(Position(1, 3), Wall), Cell(Position(2, 3), Free), Cell(Position(3, 3), Wall), Cell(Position(4, 3), Free), Cell(Position(5, 3), Wall), Cell(Position(6, 3), Wall)),
                arrayOf(Cell(Position(0, 4), Wall), Cell(Position(1, 4), Free), Cell(Position(2, 4), Free), Cell(Position(3, 4), Wall), Cell(Position(4, 4), Free), Cell(Position(5, 4), Free), Cell(Position(6, 4), Wall)),
                arrayOf(Cell(Position(0, 5), Wall), Cell(Position(1, 5), Wall), Cell(Position(2, 5), Free), Cell(Position(3, 5), Free), Cell(Position(4, 5), Free), Cell(Position(5, 5), Wall), Cell(Position(6, 5), Wall)),
                arrayOf(Cell(Position(0, 6), Wall), Cell(Position(1, 6), Wall), Cell(Position(2, 6), Start), Cell(Position(3, 6), Wall), Cell(Position(4, 6), Wall), Cell(Position(5, 6), Wall), Cell(Position(6, 6), Wall))
        )
    }

    override fun toString(): String {
        return mazeArray.joinToString(separator = "\n") { cells ->
            cells.joinToString(separator = "|", prefix = "|", postfix = "|") {
                it.type.toString()
            }
        }
    }

    fun generateMazeFromString(mazeString: String) {
        val maze = mazeString.split("\n").mapIndexed { rowNumber, row ->
            row.split("|")
                    .mapIndexed { columnNumber, cell ->
                        Cell(Position(columnNumber, rowNumber), stringToCellType(cell))
                    }.toTypedArray()
        }.toTypedArray()

        mazeArray = maze
    }

    private fun stringToCellType(s: String): CellType {
        return when (s) {
            " " -> Free
            "*" -> Wall
            "S" -> Start
            "E" -> End
            "X" -> Visited
            else -> Boundary
        }
    }

    private fun initializeMaze(numRows: Int, numCols: Int) {
        var wallMaze = Array(numCols) { y ->
            Array(numRows) { x ->
                Cell(Position(y, x), Wall)
            }
        }

        mazeArray = wallMaze
    }

    fun generateMaze(numRows: Int, numCols: Int) {
        initializeMaze(numRows, numCols)
        var listOfVisitedCells = ArrayList<Cell>()
        var listOfEdges = ArrayList<Cell>()

        var start = selectStartCell()
        mazeArray[start.position.x][start.position.y] = start

        listOfEdges.addAll(getNewEdges(start))
    }

    private fun getNewEdges(currentCell: Cell): ArrayList<Cell> {
        val topCell = mazeArray.getOrNull(currentCell.position.y)?.getOrNull(currentCell.position.x - 1).orElseBoundary(currentCell.position)
        val rightCell = mazeArray.getOrNull(currentCell.position.y)?.getOrNull(currentCell.position.x - 1).orElseBoundary(currentCell.position)
        val bottomCell = mazeArray.getOrNull(currentCell.position.y)?.getOrNull(currentCell.position.x - 1).orElseBoundary(currentCell.position)
        val leftCell = mazeArray.getOrNull(currentCell.position.y)?.getOrNull(currentCell.position.x - 1).orElseBoundary(currentCell.position)

        var newEdges = ArrayList<Cell>()
        if (topCell.isNotFree()) {
            newEdges.add(topCell)
        }
        if (rightCell.isNotFree()) {
            newEdges.add(rightCell)
        }
        if (bottomCell.isNotFree()) {
            newEdges.add(bottomCell)
        }
        if (leftCell.isNotFree()) {
            newEdges.add(leftCell)
        }

        return newEdges
    }

    private fun selectStartCell(): Cell {

        val startSide = (1..4).shuffled().first()
        var startX = 1
        var startY = 0

        when (startSide) {
            1 -> {
                startY = 0
                startX = (1..mazeArray[0].size - 2).shuffled().first()
            }
            2 -> {
                startY = (1..mazeArray.size - 2).shuffled().first()
                startX = mazeArray[0].size - 1
            }
            3 -> {
                startY = mazeArray.size - 1
                startX = (1..mazeArray[0].size - 2).shuffled().first()
            }
            4 -> {
                startY = (1..mazeArray.size - 2).shuffled().first()
                startX = 0
            }
        }

        return Cell(Position(startX, startY), Start)
    }
}