sealed class Cell {
    data class Free(var position: Position) : Cell()
    data class Wall(var position: Position) : Cell()
    data class Start(var position: Position) : Cell()
    data class End(var position: Position) : Cell()

    override fun toString(): String {
        return when (this) {
            is Cell.Free -> "0"
            is Cell.Wall -> "1"
            is Cell.Start -> "2"
            is Cell.End -> "3"
        }
    }
}

fun Cell.isFree(): Boolean {
    return when (this) {
        is Cell.Free -> true
        else -> false
    }
}

fun Cell.isNotFree(): Boolean {
    return !isFree()
}

data class Position(var x: Int, var y: Int)
