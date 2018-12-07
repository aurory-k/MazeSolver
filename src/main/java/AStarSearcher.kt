import CellType.Free

class AStarSearcher(private var maze: Maze) {

    fun solve(draw: (Maze) -> Unit) {
        var position = maze.start.position
        var openList = listOf<Cell>()
        var closedList = listOf(maze.start)

        openList = openList.plus(expandOpenList(position, closedList))

        while (openList.isNotEmpty() && position != maze.end.position) {
            println("OpenList: $openList")
            val (newPosition, cellThatSearcherMovedTo, changedMaze) = move(position, openList, maze)
            position = newPosition
            maze = changedMaze
            draw(maze)

            closedList = closedList.plus(cellThatSearcherMovedTo)
            openList = openList.minus(cellThatSearcherMovedTo)

            openList = openList.plus(expandOpenList(position, closedList))
            Thread.sleep(100)
        }

        println("End?: ${maze.get(position)}")

    }

    private fun expandOpenList(position: Position, closedList:List<Cell>): List<Cell> {
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
        val changedMaze = maze.swap(cell!!.position, cell.weight, CellType.Visited)
        val newPosition = position.copy(x = cell.position.x, y = cell.position.y)

        return Triple(newPosition, cell, changedMaze)
    }

}