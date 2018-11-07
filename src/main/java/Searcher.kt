class Searcher() {

    fun generateJunctionList() {

    }

    private fun isJunction(cell: Cell, maze: Maze): Boolean {
        val (x, y) = cell.position
        val topCell = maze.get(x,y - 1)
        val rightCell = maze.get(x + 1, y)
        val bottomCell = maze.get(x , y + 1)
        val leftCell = maze.get(x - 1, y)

        if (topCell.isFree() || bottomCell.isFree()) {
            return rightCell.isFree() || leftCell.isFree()
        }

        if (leftCell.isFree() || rightCell.isFree()) {
            return topCell.isFree() || bottomCell.isFree()
        }

        return false
    }

}