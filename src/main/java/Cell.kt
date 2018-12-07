sealed class CellType {
    object Free : CellType()
    object Wall : CellType()
    object Start : CellType()
    object End : CellType()
    object Frontier : CellType()
    object Visited : CellType()

    object Boundary : CellType()

    override fun toString(): String {
        return when (this) {
            is Free -> " "
            is Wall -> "*"
            is Start -> "S"
            is End -> "E"
            is Frontier -> "F"
            is Visited -> "X"
            is Boundary -> "#"
        }
    }
}

fun Cell?.isFree(): Boolean {
    if (this == null) {
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
    if (this == null) {
        return false
    }
    if (this.type == CellType.Boundary) {
        return false
    }
    return !isFree()
}

fun Cell?.orElseBoundary(position: Position): Cell {
    if (this != null) {
        return this
    }
    return Cell(Position(position.x, position.y), Double.MAX_VALUE, CellType.Boundary)
}

data class Cell(val position: Position, val weight: Double, val type: CellType)

data class Position(val x: Int, val y: Int)

data class Junction(val position: Position, val searchableDirections: ArrayList<String>)