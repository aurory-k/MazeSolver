import CellType.*
import java.util.*

class AStarSearcher(private var maze: Maze) {

    fun solve(draw: (Maze) -> Unit) {
        var position = maze.start.position
        var openList = listOf<Cell>()
        val closedList: PriorityQueue<Cell> = PriorityQueue(10, CellComparator())
        closedList.add(maze.start)
        openList = openList.plus(expandOpenList(position, closedList))

        while (openList.isNotEmpty()) {
            if (checkForEnd(position)) {
                //println("ClosedList: $closedList")
                break
            }

            val (newPosition, cellThatSearcherMovedTo, changedMaze) = move(position, openList, maze)
            position = newPosition
            maze = changedMaze
            draw(maze)

            closedList.add(cellThatSearcherMovedTo)
            openList = openList.minus(cellThatSearcherMovedTo)

            openList = openList.plus(expandOpenList(position, closedList))
            //Thread.sleep(5)
        }

        val solutionList = calculateShortestPath(closedList)

        solutionList.forEach { cell ->
            maze = if(cell.type == Visited){
                maze.swap(cell.position, cell.weight, CellType.Solution)
            } else {
                maze.swap(cell.position, cell.weight, CellType.Start)
            }
            draw(maze)
        }
    }

    private fun calculateShortestPath(listOfCells: PriorityQueue<Cell>): List<Cell> {
        val firstCell = listOfCells.poll()

        var solutionList = listOf(Cell(firstCell.position, firstCell.weight, Visited))

        while (!solutionList.contains(maze.start)) {
            val anchorCell = solutionList[solutionList.size - 1]
            val cellToAdd = anchorCell.getClosestAdjacentCell(solutionList)
            solutionList = solutionList.plus(cellToAdd)
            listOfCells.remove(cellToAdd)
        }

        println("solution?: $solutionList")

        return solutionList
    }

    private fun Cell.getClosestAdjacentCell(solutionList: List<Cell>): Cell {
        val (px, py) = this.position

        val topCell = maze.get(px, py - 1)
        val rightCell = maze.get(px + 1, py)
        val bottomCell = maze.get(px, py + 1)
        val leftCell = maze.get(px - 1, py)


        val listOfAdjacentCells = listOf(topCell,rightCell,bottomCell,leftCell)


        var closestCell = Cell(Position(0,0), Double.MIN_VALUE, Boundary)

        for(cell in listOfAdjacentCells){
            if(!solutionList.contains(cell) && cell.weight >= closestCell.weight && (cell.type == Visited || cell.type == Start)){
                closestCell = cell
            }
        }

        return closestCell
    }

    private fun checkForEnd(position: Position): Boolean {
        val (px, py) = position

        val topCell = maze.get(px, py - 1)
        val rightCell = maze.get(px + 1, py)
        val bottomCell = maze.get(px, py + 1)
        val leftCell = maze.get(px - 1, py)

        return (topCell.type is End || rightCell.type == End || bottomCell.type == End || leftCell.type == End)
    }

    private fun expandOpenList(position: Position, closedList: PriorityQueue<Cell>): List<Cell> {
        val (px, py) = position
        var addedCells = listOf<Cell>()

        val topCell = maze.get(px, py - 1)
        val rightCell = maze.get(px + 1, py)
        val bottomCell = maze.get(px, py + 1)
        val leftCell = maze.get(px - 1, py)

        if (topCell.type == Free && !closedList.contains(topCell)) {
            addedCells = addedCells.plus(topCell)
        }

        if (rightCell.type == Free && !closedList.contains(rightCell)) {
            addedCells = addedCells.plus(rightCell)
        }

        if (bottomCell.type == Free && !closedList.contains(bottomCell)) {
            addedCells = addedCells.plus(bottomCell)
        }

        if (leftCell.type == Free && !closedList.contains(leftCell)) {
            addedCells = addedCells.plus(leftCell)
        }

        return addedCells
    }

    private fun move(position: Position, openList: List<Cell>, maze: Maze): Triple<Position, Cell, Maze> {
        val cell = openList.minBy { cell -> cell.weight }
        val changedMaze = maze.swap(cell!!.position, cell.weight, Visited)
        val newPosition = position.copy(x = cell.position.x, y = cell.position.y)

        return Triple(newPosition, cell, changedMaze)
    }

}

private class CellComparator : Comparator<Cell> {
    override fun compare(a: Cell, b: Cell): Int {
        return when {
            a.weight > b.weight -> 1
            a.weight == b.weight -> 0
            a.weight < b.weight -> -1
            else -> -2
        }
    }
}