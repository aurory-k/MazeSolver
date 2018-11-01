import org.junit.Test
import kotlin.test.assertEquals

class MazeTests{
    @Test
    fun `searcher finds correct junctions`(){
        println(MazeGenerator().toString())
        val searcher = Searcher(Position(6,2), MazeGenerator.mazeArray)
        searcher.findJunctions()
        Solver.listOfJunctions.forEach { junction ->
            println(junction.toString())
        }
        assertEquals(7, Solver.listOfJunctions.size)
    }
}