sealed class CellType {
    object Free : CellType()
    object Wall : CellType()
    object Start : CellType()
    object End : CellType()
    object Visited : CellType()

    override fun toString(): String {
        return when (this) {
            is CellType.Free -> "0"
            is CellType.Wall -> "1"
            is CellType.Start -> "2"
            is CellType.End -> "3"
            is CellType.Visited -> "X"
            else -> "-1"
        }
    }
}

fun Cell?.isFree(): Boolean {
    if (this == null){
        return false
    }
    return when (this.type) {
        is CellType.Free -> true
        is CellType.Start -> true
        is CellType.Visited -> true
        else -> false
    }
}

fun Cell?.isNotFree(): Boolean {
    return !isFree()
}

data class Cell(val position: Position, var type: CellType) : CellType()

data class Position(val x: Int, val y: Int)

data class Junction(val position: Position, val searchableDirections: ArrayList<String>) : CellType()