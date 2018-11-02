class Searcher(){

    fun generateJunctionList(){

    }

    private fun isJunction(cell: Cell): Boolean{
        val topCell: Cell? = MazeGenerator.mazeArray.getOrNull(cell.position.y - 1)?.getOrNull(cell.position.x)
        val rightCell: Cell? = MazeGenerator.mazeArray.getOrNull(cell.position.y)?.getOrNull(cell.position.x + 1)
        val bottomCell: Cell? = MazeGenerator.mazeArray.getOrNull(cell.position.y + 1)?.getOrNull(cell.position.x)
        val leftCell: Cell? = MazeGenerator.mazeArray.getOrNull(cell.position.y)?.getOrNull(cell.position.x - 1)

        if (topCell.isFree() || bottomCell.isFree()) {
            return rightCell.isFree() || leftCell.isFree()
        }

        if (leftCell.isFree() || rightCell.isFree()) {
            return topCell.isFree() || bottomCell.isFree()
        }

        return false
    }

}