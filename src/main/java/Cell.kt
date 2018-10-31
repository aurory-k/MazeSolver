sealed class CellType {
    object Free : CellType()
    object Wall : CellType()
    object Start : CellType()
    object End : CellType()

    override fun toString(): String {
        return when (this) {
            is CellType.Free -> "0"
            is CellType.Wall -> "1"
            is CellType.Start -> "2"
            is CellType.End -> "3"
            else -> "-1"
        }
    }
}

fun Cell.isFree(): Boolean {
    return when (this.type) {
        is CellType.Free -> true
        else -> false
    }
}

fun Cell.isNotFree(): Boolean {
    return !isFree()
}

data class Cell(var position: Position, val type: CellType) : CellType()

data class Position(var x: Int, var y: Int)

data class Junction(var position: Position, var searchableDirections: ArrayList<String>) : CellType()