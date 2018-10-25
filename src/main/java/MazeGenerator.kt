class MazeGenerator{

    val stringMazeArray = arrayOf(
            arrayOf("1","2","1","1","1","1","1"),
            arrayOf("1","0","1","1","0","1","1"),
            arrayOf("1","0","0","1","0","0","3"),
            arrayOf("1","1","0","1","0","1","1"),
            arrayOf("1","0","0","1","0","0","1"),
            arrayOf("1","1","0","0","0","1","1"),
            arrayOf("1","1","1","1","1","1","1")
    )

    fun translateStringMazeToCellArray(): ArrayList<ArrayList<out Cell>>?{
        var cellMazeArray = ArrayList<ArrayList<out Cell>>()
        for(y in stringMazeArray.indices){
            for(x in stringMazeArray[0].indices){
                println("Value: ${stringMazeArray[y][x]} at position: ($x,$y)")
                if(stringMazeArray[y][x] == "0"){
                    cellMazeArray.add(y, Cell.Free(Position(y,x)))
                } else {
                    cell = Cell.End(Position(y,x))
                }

            }
        }

        return null
    }

    companion object {

    }

}