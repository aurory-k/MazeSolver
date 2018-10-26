import CellType.*

class MazeGenerator {
    companion object {
        val mazeArray = arrayOf(
                arrayOf(Cell(Position(0, 0), Wall), Cell(Position(1, 0), Start), Cell(Position(2, 0), Wall), Cell(Position(3, 0), Wall), Cell(Position(4, 0), Wall), Cell(Position(5, 0), Wall), Cell(Position(6, 0), Wall)),
                arrayOf(Cell(Position(0, 1), Wall), Cell(Position(1, 1), Free), Cell(Position(2, 1), Wall), Cell(Position(3, 1), Wall), Cell(Position(4, 1), Free), Cell(Position(5, 1), Wall), Cell(Position(6, 1), Wall)),
                arrayOf(Cell(Position(0, 2), Wall), Cell(Position(1, 2), Free), Cell(Position(2, 2), Free), Cell(Position(3, 2), Wall), Cell(Position(4, 2), Free), Cell(Position(5, 2), Free), Cell(Position(6, 2), End)),
                arrayOf(Cell(Position(0, 3), Wall), Cell(Position(1, 3), Wall), Cell(Position(2, 3), Free), Cell(Position(3, 3), Wall), Cell(Position(4, 3), Free), Cell(Position(5, 3), Wall), Cell(Position(6, 3), Wall)),
                arrayOf(Cell(Position(0, 4), Wall), Cell(Position(1, 4), Free), Cell(Position(2, 4), Free), Cell(Position(3, 4), Wall), Cell(Position(4, 4), Free), Cell(Position(5, 4), Free), Cell(Position(6, 4), Wall)),
                arrayOf(Cell(Position(0, 5), Wall), Cell(Position(1, 5), Wall), Cell(Position(2, 5), Free), Cell(Position(3, 5), Free), Cell(Position(4, 5), Free), Cell(Position(5, 5), Wall), Cell(Position(6, 5), Wall)),
                arrayOf(Cell(Position(0, 6), Wall), Cell(Position(1, 6), Wall), Cell(Position(2, 6), Wall), Cell(Position(3, 6), Wall), Cell(Position(4, 6), Wall), Cell(Position(5, 6), Wall), Cell(Position(6, 6), Wall))
        )
    }

    override fun toString(): String {
        return mazeArray.joinToString(separator = "\n") { cells ->
            cells.joinToString(separator = "|", prefix = "|", postfix = "|") {
                it.type.toString()
            }
        }
    }
}