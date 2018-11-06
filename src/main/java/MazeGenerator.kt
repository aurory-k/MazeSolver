import CellType.*

class MazeGenerator {

    var listOfVisitedCells = ArrayList<Cell>()

    fun initializeMaze(numRows: Int, numCols: Int) {
        val wallMaze = Array(numCols) { y ->
            Array(numRows) { x ->
                Cell(Position(x, y), Wall)
            }
        }

        Maze.mazeArray = wallMaze
    }

    fun generateMaze(): Pair<Cell,Cell> {
        var listOfEdges = ArrayList<Cell>()

        val (start, end) = selectStartAndEndCells()
        listOfVisitedCells.add(start)
        Maze.mazeArray[start.position.y][start.position.x] = start

        listOfEdges.addAll(getNewEdges(start))

        while(listOfEdges.size > 0){
            val currentCell = listOfEdges.shuffled().first()
            val (px,py) = currentCell.position

            println("-----------------------------------")
            println(toString())

            if(!listOfVisitedCells.contains(currentCell) && numberOfFreeNeighborCells(currentCell) < 2){
                Maze.mazeArray[py][px].type = CellType.Free
                listOfEdges.addAll(getNewEdges(currentCell))
                listOfVisitedCells.add(currentCell)
            } else {
                Maze.mazeArray[py][px].type = CellType.Wall
            }
            listOfEdges.remove(currentCell)
//            Thread.sleep(100)
        }

        Maze.mazeArray[end.position.y][end.position.x] = end

        println("-----------------------------------")
        println(toString())

        return Pair(start,end)
    }

    private fun getNewEdges(currentCell: Cell): ArrayList<Cell> {
        val (px, py) = currentCell.position
        val topCell = Maze.mazeArray.getOrNull(py - 1)?.getOrNull(px).orElseBoundary(currentCell.position)
        val rightCell = Maze.mazeArray.getOrNull(py)?.getOrNull(px + 1).orElseBoundary(currentCell.position)
        val bottomCell = Maze.mazeArray.getOrNull(py + 1)?.getOrNull(px).orElseBoundary(currentCell.position)
        val leftCell = Maze.mazeArray.getOrNull(py)?.getOrNull(px - 1).orElseBoundary(currentCell.position)

        val newEdges = ArrayList<Cell>()
        if (topCell.isNotFree() && !newEdges.contains(topCell) && !listOfVisitedCells.contains(topCell)) {
            newEdges.add(topCell)
        }
        if (rightCell.isNotFree() && !newEdges.contains(rightCell) && !listOfVisitedCells.contains(rightCell)) {
            newEdges.add(rightCell)
        }
        if (bottomCell.isNotFree() && !newEdges.contains(bottomCell) && !listOfVisitedCells.contains(bottomCell)) {
            newEdges.add(bottomCell)
        }
        if (leftCell.isNotFree() && !newEdges.contains(leftCell) && !listOfVisitedCells.contains(leftCell)) {
            newEdges.add(leftCell)
        }

        return newEdges
    }

    private fun numberOfFreeNeighborCells(cell: Cell): Int{
        val (px, py) = cell.position
        val topCell = Maze.mazeArray.getOrNull(py - 1)?.getOrNull(px).orElseBoundary(cell.position)
        val rightCell = Maze.mazeArray.getOrNull(py)?.getOrNull(px + 1).orElseBoundary(cell.position)
        val bottomCell = Maze.mazeArray.getOrNull(py + 1)?.getOrNull(px).orElseBoundary(cell.position)
        val leftCell = Maze.mazeArray.getOrNull(py)?.getOrNull(px - 1).orElseBoundary(cell.position)
        var numberOfFreeCells = 0

        if(topCell.isFree()){
            numberOfFreeCells += 1
        }

        if(rightCell.isFree()){
            numberOfFreeCells += 1
        }

        if(bottomCell.isFree()){
            numberOfFreeCells += 1
        }

        if(leftCell.isFree()){
            numberOfFreeCells += 1
        }

        return numberOfFreeCells
    }

    private fun selectStartAndEndCells(): Pair<Cell,Cell> {

        val startSide = (1..4).shuffled().first()
        var startX = 0
        var startY = 0

        var endX = 0
        var endY = 0

        when (startSide) {
            1 -> {
                startY = 0
                startX = (1..Maze.mazeArray[0].size - 2).shuffled().first()

                endY = Maze.mazeArray.size - 1
                endX = (1..Maze.mazeArray[0].size - 2).shuffled().first()
            }
            2 -> {
                startY = (1..Maze.mazeArray.size - 2).shuffled().first()
                startX = Maze.mazeArray[0].size - 1

                endY = (1..Maze.mazeArray.size - 2).shuffled().first()
                endX = 0
            }
            3 -> {
                startY = Maze.mazeArray.size - 1
                startX = (1..Maze.mazeArray[0].size - 2).shuffled().first()

                endY = 0
                endX = (1..Maze.mazeArray[0].size - 2).shuffled().first()
            }
            4 -> {
                startY = (1..Maze.mazeArray.size - 2).shuffled().first()
                startX = 0

                endY = (1..Maze.mazeArray.size - 2).shuffled().first()
                endX = Maze.mazeArray[0].size - 1
            }
        }

        return Pair(Cell(Position(startX, startY), Start), Cell(Position(endX, endY), End))
    }
}