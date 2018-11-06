import CellType.*

class Crawler(private var position: Position, private var maze: Array<Array<Cell>>) {
    private var direction: String = "start"

    private var topCell: Cell = Cell(Position(position.x, position.y + 1), Wall)
    private var rightCell: Cell = Cell(Position(position.x + 1, position.y), Wall)
    private var bottomCell: Cell = Cell(Position(position.x, position.y - 1), Wall)
    private var leftCell: Cell = Cell(Position(position.x - 1, position.y), Wall)

    fun crawl() {
        if(Solver.endOfMaze.type != Boundary){
            return
        }

//        Solver.listOfVisitedPositions.add(position)
        if (direction == "start") {
            determineDirection()
        }

        while (direction != "none") {
            val (isEnd, cell) = checkForEnd(maze)
            if(isEnd){
                Solver.endOfMaze = Maze.mazeArray[cell.position.y][cell.position.x]
                return
            }
            if (isJunction()) {
                val junction = Junction(position, findJunctionDirections())
                if (Solver.junctionDoesNotExist(junction)) {
                    Solver.listOfJunctions.add(junction)
                    spawnSearcherFromJunction(junction)
                }
            }
            move()
            println("---------------------------------")
            println(Maze().toString())
            determineDirection()
        }
    }

    private fun spawnSearcherFromJunction(junction: Junction) {
        for (it in junction.searchableDirections) {
            val searcher = Crawler(junction.position, maze)
            searcher.setDirection(it)
            searcher.crawl()
        }
    }

    private fun move() {
        if (direction == "up" && topCell.isFree()) {
            position = position.copy(y = position.y - 1)
        } else if (direction == "right" && rightCell.isFree()) {
            position = position.copy(x = position.x + 1)
        } else if (direction == "down" && bottomCell.isFree()) {
            position = position.copy(y = position.y + 1)
        } else if (direction == "left" && leftCell.isFree()) {
            position = position.copy(x = position.x - 1)
        }

        Solver.listOfVisitedPositions.add(position)
        Maze.mazeArray[position.y][position.x].type = Visited
    }

    private fun isJunction(): Boolean {
        updateCardinalCells(maze)
        if (topCell.isFree() || bottomCell.isFree()) {
            return rightCell.isFree() || leftCell.isFree()
        }

        if (leftCell.isFree() || rightCell.isFree()) {
            return topCell.isFree() || bottomCell.isFree()
        }

        return false
    }

    private fun determineDirection() {
        updateCardinalCells(maze)
        direction = when {
            topCell.isFree() && Solver.isNotAlreadyVisited(topCell?.position) -> "up"
            rightCell.isFree() && Solver.isNotAlreadyVisited(rightCell?.position) -> "right"
            bottomCell.isFree() && Solver.isNotAlreadyVisited(bottomCell?.position) -> "down"
            leftCell.isFree() && Solver.isNotAlreadyVisited(leftCell?.position) -> "left"
            else -> "none"
        }
    }

    private fun setDirection(direction: String) {
        this.direction = direction
    }

    private fun updateCardinalCells(maze: Array<Array<Cell>>) {
        topCell = maze.getOrNull(position.y - 1)?.getOrNull(position.x).orElseBoundary(position)
        rightCell = maze.getOrNull(position.y)?.getOrNull(position.x + 1).orElseBoundary(position)
        bottomCell = maze.getOrNull(position.y + 1)?.getOrNull(position.x).orElseBoundary(position)
        leftCell = maze.getOrNull(position.y)?.getOrNull(position.x - 1).orElseBoundary(position)
    }

    private fun checkForEnd(maze: Array<Array<Cell>>): Pair<Boolean, Cell> {
        updateCardinalCells(maze)
        return when (End) {
            topCell.type -> Pair(true, topCell)
            rightCell.type -> Pair(true, rightCell)
            bottomCell.type -> Pair(true, bottomCell)
            leftCell.type -> Pair(true, leftCell)
            else -> Pair(false, topCell)
        }
    }

    private fun findJunctionDirections(): ArrayList<String> {
        updateCardinalCells(maze)
        val listOfJunctionDirections = ArrayList<String>()
        if (topCell.isFree() && Solver.isNotAlreadyVisited(topCell.position)) {
            listOfJunctionDirections.add("up")
        }
        if (rightCell.isFree() && Solver.isNotAlreadyVisited(rightCell?.position)) {
            listOfJunctionDirections.add("right")
        }
        if (bottomCell.isFree() && Solver.isNotAlreadyVisited(bottomCell?.position)) {
            listOfJunctionDirections.add("down")
        }
        if (leftCell.isFree() && Solver.isNotAlreadyVisited(leftCell?.position)) {
            listOfJunctionDirections.add("left")
        }

        return listOfJunctionDirections
    }
}