class Searcher(private var position: Position, private var maze: Array<Array<Cell>>) {
    private var direction: String = "start"

    var listOfVisitedPositions = ArrayList<Position>()

    private var topCell: Cell? = Cell(Position(position.x, position.y + 1), CellType.Wall)
    private var rightCell: Cell? = Cell(Position(position.x + 1, position.y), CellType.Wall)
    private var bottomCell: Cell? = Cell(Position(position.x, position.y - 1), CellType.Wall)
    private var leftCell: Cell? = Cell(Position(position.x - 1, position.y), CellType.Wall)

    fun solveMaze(): Position {

        search()

        return Position(0, 0)
    }

    private fun search() {
        val listOfJunctions = findJunctions()

        listOfJunctions.forEach { junction ->
            println(junction.toString())
        }
    }

    private fun findJunctions(): ArrayList<Junction> {
        val listOfJunctions = ArrayList<Junction>()
        determineDirection()

        while (direction != "none") {
            if (isJunction()) {
                listOfJunctions.add(Junction(position, findJunctionDirections()))
            }
            listOfVisitedPositions.add(position)
            move()
            determineDirection()
        }

        return listOfJunctions
    }

    private fun move() {
        when (direction) {
            "up" -> position = position.copy(y = position.y - 1)
            "right" -> position = position.copy(x = position.x + 1)
            "down" -> position = position.copy(y = position.y + 1)
            "left" -> position = position.copy(x = position.x - 1)
        }
    }

    private fun isJunction(): Boolean {
        updateCardinalCells()
        if (topCell.isFree() || bottomCell.isFree()) {
            return rightCell.isFree() || leftCell.isFree()
        }

        if (leftCell.isFree() || rightCell.isFree()) {
            return topCell.isFree() || bottomCell.isFree()
        }

        return false
    }

    private fun determineDirection() {
        updateCardinalCells()
        direction = when {
            topCell.isFree() && !listOfVisitedPositions.contains(topCell?.position) -> "up"
            rightCell.isFree() && !listOfVisitedPositions.contains(rightCell?.position) -> "right"
            bottomCell.isFree() && !listOfVisitedPositions.contains(bottomCell?.position) -> "down"
            leftCell.isFree() && !listOfVisitedPositions.contains(leftCell?.position) -> "left"
            else -> "none"
        }
//        println("Direction is: $direction")
    }

    private fun updateCardinalCells() {
//        println("Current Position: (${position.x},${position.y})")
        topCell = maze.getOrNull(position.y - 1)?.getOrNull(position.x)
        rightCell = maze.getOrNull(position.y)?.getOrNull(position.x + 1)
        bottomCell = maze.getOrNull(position.y + 1)?.getOrNull(position.x)
        leftCell = maze.getOrNull(position.y)?.getOrNull(position.x - 1)
    }

    private fun findJunctionDirections(): ArrayList<String> {
        updateCardinalCells()
        val listOfJunctionDirections = ArrayList<String>()
        if (topCell.isFree() && !listOfVisitedPositions.contains(topCell?.position)) {
            listOfJunctionDirections.add("up")
        }
        if (rightCell.isFree() && !listOfVisitedPositions.contains(rightCell?.position)) {
            listOfJunctionDirections.add("right")
        }
        if (bottomCell.isFree() && !listOfVisitedPositions.contains(bottomCell?.position)) {
            listOfJunctionDirections.add("down")
        }
        if (leftCell.isFree() && !listOfVisitedPositions.contains(leftCell?.position)) {
            listOfJunctionDirections.add("left")
        }

        return listOfJunctionDirections
    }
}