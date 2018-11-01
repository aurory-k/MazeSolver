class Searcher(private var position: Position, private var maze: Array<Array<Cell>>) {
    private var direction: String = "start"

    private var topCell: Cell? = Cell(Position(position.x, position.y + 1), CellType.Wall)
    private var rightCell: Cell? = Cell(Position(position.x + 1, position.y), CellType.Wall)
    private var bottomCell: Cell? = Cell(Position(position.x, position.y - 1), CellType.Wall)
    private var leftCell: Cell? = Cell(Position(position.x - 1, position.y), CellType.Wall)

    fun solveMaze(): Position {
        return Position(0, 0)
    }

     fun findJunctions(){
         if(direction == "start"){
             determineDirection()
         }

        while (direction != "none") {
            if (isJunction()) {
                val junction = Junction(position, findJunctionDirections())
                if(!junctionAlreadyExists(junction)){
                    Solver.listOfJunctions.add(junction)
                    spawnSearcherFromJunction(junction)
                }
            }
            Solver.listOfVisitedPositions.add(position)
            move()
            determineDirection()
            println("Direction: $direction")
        }
    }

    private fun spawnSearcherFromJunction(junction: Junction){
        for (it in junction.searchableDirections) {
            val searcher = Searcher(junction.position, maze)
            searcher.setDirection(it)
            searcher.findJunctions()
        }
    }

    private fun move() {

        if(direction == "up" && topCell.isFree()){
            position = position.copy(y = position.y - 1)
        }else if(direction == "right" && rightCell.isFree()){
            position = position.copy(x = position.x + 1)
        }else if(direction == "down" && bottomCell.isFree()){
            position = position.copy(y = position.y + 1)
        }else if(direction == "left" && leftCell.isFree()){
            position = position.copy(x = position.x - 1)
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
            topCell.isFree() && isNotAlreadyVisited(topCell?.position) -> "up"
            rightCell.isFree() && isNotAlreadyVisited(rightCell?.position) -> "right"
            bottomCell.isFree() && isNotAlreadyVisited(bottomCell?.position) -> "down"
            leftCell.isFree() && isNotAlreadyVisited(leftCell?.position) -> "left"
            else -> "none"
        }
    }

    private fun setDirection(direction: String){
        this.direction = direction
    }

    private fun setPosition(position: Position){
        this.position = position
    }

    private fun updateCardinalCells() {
        topCell = maze.getOrNull(position.y - 1)?.getOrNull(position.x)
        rightCell = maze.getOrNull(position.y)?.getOrNull(position.x + 1)
        bottomCell = maze.getOrNull(position.y + 1)?.getOrNull(position.x)
        leftCell = maze.getOrNull(position.y)?.getOrNull(position.x - 1)
    }

    private fun isNotAlreadyVisited(position: Position?): Boolean {
        return !isAlreadyVisited(position)
    }

    private fun isAlreadyVisited(position: Position?): Boolean {
        return Solver.listOfVisitedPositions.contains(position)
    }

    private fun junctionAlreadyExists(junction: Junction): Boolean{
        Solver.listOfJunctions.forEach { junctionInList ->
            if (junction.position == junctionInList.position){
                return true
            }
        }

        return false
    }

    private fun findJunctionDirections(): ArrayList<String> {
        updateCardinalCells()
        val listOfJunctionDirections = ArrayList<String>()
        if (topCell.isFree() && isNotAlreadyVisited(topCell?.position)) {
            listOfJunctionDirections.add("up")
        }
        if (rightCell.isFree() && isNotAlreadyVisited(rightCell?.position)) {
            listOfJunctionDirections.add("right")
        }
        if (bottomCell.isFree() && isNotAlreadyVisited(bottomCell?.position)) {
            listOfJunctionDirections.add("down")
        }
        if (leftCell.isFree() && isNotAlreadyVisited(leftCell?.position)) {
            listOfJunctionDirections.add("left")
        }

        return listOfJunctionDirections
    }
}