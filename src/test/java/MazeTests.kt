import org.junit.Test
import kotlin.test.assertEquals

class MazeTests{
    @Test
    fun `searcher finds correct junctions`(){
        val searcher = Searcher(Position(1,0), MazeGenerator.mazeArray)
        val listOfJunctions = searcher.findJunctions()
        assertEquals(listOfJunctions.size, 7)
    }
}