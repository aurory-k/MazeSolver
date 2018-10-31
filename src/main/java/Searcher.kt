class Searcher(var startPosition: Position, mazeArray: Array<Array<Cell>>){
    var position = startPosition
    var maze = mazeArray
    lateinit var direction: String
    var listOfVisitedPositions = ArrayList<Position>()

    fun solveMaze() : Position{

        search()

        return Position(0,0)
    }

    private fun search(){
        var listOfJunctions = findJunctions()
    }

    private fun findJunctions(): ArrayList<Junction> {
        var listOfJunctions = ArrayList<Junction>()



        return listOfJunctions
    }

    private fun isJunction(position: Position): Boolean {
        if(topCell.isFree() || bottomCell.isFree()){
            return rightCell.isFree() && leftCell.isFree()
        }

        if(leftCell.isFree() || rightCell.isFree()){
            return topCell.isFree() && bottomCell.isFree()
        }

        return false
    }

    private fun determineDirection(){
        val topCell = maze[position.y+1][position.x]
        val rightCell = maze[position.y][position.x+1]
        val bottomCell = maze[position.y-1][position.x]
        val leftCell = maze[position.y][position.x-1]


    }
}