import CellType.*

sealed class CellType {
    object Free : CellType()
    object Wall : CellType()
    object Start : CellType()
    object End : CellType()
    object Frontier : CellType()
    object Visited : CellType()
    object Solution : CellType()

    object Boundary : CellType()

    override fun toString(): String {
        return when (this) {
            is Free -> "Free"
            is Wall -> "Wall"
            is Start -> "Start"
            is End -> "End"
            is Frontier -> "Frontier"
            is Visited -> "Visited"
            is Boundary -> "#"
            is Solution -> "Solution"
        }
    }
}

fun Cell?.isFree(): Boolean {
    if (this == null) {
        return false
    }
    return when (this.type) {
        is Free -> true
        is Start -> true
        is Visited -> true
        is End -> true
        else -> false
    }
}

fun Cell?.isNotFree(): Boolean {
    if (this == null) {
        return false
    }
    if (this.type == Boundary) {
        return false
    }
    return !isFree()
}

fun Cell?.orElseBoundary(position: Position): Cell {
    if (this != null) {
        return this
    }
    return Cell(Position(position.x, position.y), Double.MAX_VALUE, Boundary)
}

data class Cell(val position: Position, val weight: Double, val type: CellType)

data class Position(val x: Int, val y: Int)

data class Junction(val position: Position, val searchableDirections: ArrayList<String>)