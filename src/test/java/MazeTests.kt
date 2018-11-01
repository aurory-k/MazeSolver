import org.junit.Test
import kotlin.test.assertEquals

class MazeTests{
    @Test
    fun `searcher finds correct junctions`(){
        println(MazeGenerator().toString())
        val searcher = Searcher(Position(2,6), MazeGenerator.mazeArray)
        searcher.search()
        Solver.listOfJunctions.forEach { junction ->
            println(junction.toString())
        }
        assertEquals(7, Solver.listOfJunctions.size)
    }
}
