class MazeGenerator {
    companion object {
        val mazeArray = arrayOf(
                arrayOf(Cell.Wall(Position(0, 0)), Cell.Start(Position(1, 0)), Cell.Wall(Position(2, 0)), Cell.Wall(Position(3, 0)), Cell.Wall(Position(4, 0)), Cell.Wall(Position(5, 0)), Cell.Wall(Position(6, 0))),
                arrayOf(Cell.Wall(Position(0, 1)), Cell.Free(Position(1, 1)), Cell.Wall(Position(2, 1)), Cell.Wall(Position(3, 1)), Cell.Free(Position(4, 1)), Cell.Wall(Position(5, 1)), Cell.Wall(Position(6, 1))),
                arrayOf(Cell.Wall(Position(0, 2)), Cell.Free(Position(1, 2)), Cell.Free(Position(2, 2)), Cell.Wall(Position(3, 2)), Cell.Free(Position(4, 2)), Cell.Free(Position(5, 2)), Cell.End(Position(6, 2))),
                arrayOf(Cell.Wall(Position(0, 3)), Cell.Wall(Position(1, 3)), Cell.Free(Position(2, 3)), Cell.Wall(Position(3, 3)), Cell.Free(Position(4, 3)), Cell.Wall(Position(5, 3)), Cell.Wall(Position(6, 3))),
                arrayOf(Cell.Wall(Position(0, 4)), Cell.Free(Position(1, 4)), Cell.Free(Position(2, 4)), Cell.Wall(Position(3, 4)), Cell.Free(Position(4, 4)), Cell.Free(Position(5, 4)), Cell.Wall(Position(6, 4))),
                arrayOf(Cell.Wall(Position(0, 5)), Cell.Wall(Position(1, 5)), Cell.Free(Position(2, 5)), Cell.Free(Position(3, 5)), Cell.Free(Position(4, 5)), Cell.Wall(Position(5, 5)), Cell.Wall(Position(6, 5))),
                arrayOf(Cell.Wall(Position(0, 6)), Cell.Wall(Position(1, 6)), Cell.Wall(Position(2, 6)), Cell.Wall(Position(3, 6)), Cell.Wall(Position(4, 6)), Cell.Wall(Position(5, 6)), Cell.Wall(Position(6, 6)))
        )
    }

    override fun toString(): String {
//        return mazeArray.joinToString(separator = "\n") { cells ->
//            cells.joinToString(separator = "|", prefix = "|", postfix = "|") {
//                it.toString()
//            }
//        }
        return "YOO"
    }
}