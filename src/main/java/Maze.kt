import CellType.*

class Maze(private val mazeArray: Array<Array<Cell>>, val start: Cell, val end: Cell) {
    companion object {
        fun generateMazeFromString(mazeString: String): Maze {
            val maze = mazeString.split("\n").mapIndexed { rowNumber, row ->
                row.split("|")
                        .mapIndexed { columnNumber, cell ->
                            Cell(Position(columnNumber, rowNumber), 0.0, stringToCellType(cell))
                        }.toTypedArray()
            }.toTypedArray()

            return Maze(maze, Cell(Position(0,0), 0.0, Start), Cell(Position(0,0), 0.0, End))
        }

        private fun stringToCellType(s: String): CellType {
            return when (s) {
                " " -> Free
                "*" -> Wall
                "S" -> Start
                "E" -> End
                "X" -> Visited
                else -> Boundary
            }
        }
    }

    fun getRowSize(): Int {
        return mazeArray[0].size
    }

    fun getColSize(): Int {
        return mazeArray.size
    }

    fun get(x: Int, y: Int): Cell {
        return mazeArray.getOrNull(y)?.getOrNull(x).orElseBoundary(Position(x, y))
    }

    fun get(position: Position): Cell {
        val (x, y) = position
        return get(x, y)
    }

    private fun Array<Array<Cell>>.copy() = map { it.clone() }.toTypedArray()

    fun swap(position: Position, weight: Double, type: CellType): Maze {
        val mazeArray = this.mazeArray.copy()
        mazeArray[position.y][position.x] = Cell(position, weight, type)
        return Maze(mazeArray, this.start, this.end)
    }

    fun swap(cell: Cell): Maze {
        return swap(cell.position, cell.weight, cell.type)
    }

    override fun toString(): String {
        return this.mazeArray.joinToString(separator = "\n") { cells ->
            cells.joinToString(separator = "|", prefix = "|", postfix = "|") {
                it.type.toString()
            }
        }
    }

}