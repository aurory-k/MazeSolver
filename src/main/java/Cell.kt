sealed class Cell {
    object Free : Cell()
    object Wall : Cell()
    object Start : Cell()
    object End : Cell()
    object Junction : Cell() {
        object top : Cell()
        object right : Cell()
        object bottom : Cell()
        object left : Cell()
    }

    override fun toString(): String {
        return when (this) {
            Cell.Free -> "0"
            Cell.Junction -> "0"
            Cell.Wall -> "1"
            Cell.Start -> "2"
            Cell.End -> "3"
            else -> "-1"
        }
    }
}

fun Cell.isFree(): Boolean {
    return if (this == null){
        false
    } else {
        when (this) {
            Cell.Free -> true
            else -> false
        }
    }
}

fun Cell.isNotFree(): Boolean {
    return !isFree()
}