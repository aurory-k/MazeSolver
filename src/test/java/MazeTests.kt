import org.junit.Test
import kotlin.test.assertEquals

class MazeTests {
    @Test
    fun `crawler finds correct junctions`() {
        var maze = """
*|*|*|*|*|*|*
*| | | | | |*
*| |*|*|*| |*
S| |*|*|*| |E
*| |*|*|*| |*
*| | | | | |*
*|*|*|*|*|*|*
            """.trimIndent()
        val mazeGenerator = Maze()
        mazeGenerator.generateMazeFromString(maze)
        println(Maze().toString())
        val searcher = Crawler(Position(0, 3), Maze.mazeArray)
        searcher.crawl()
        Solver.listOfJunctions.forEach { junction ->
            println(junction.toString())
        }
        assertEquals(5, Solver.listOfJunctions.size)
    }

    @Test
    fun `maze randomly generates correctly`() {
        val mazeGenerator = MazeGenerator()
        mazeGenerator.initializeMaze(10,10)
        val (start,end) = mazeGenerator.generateMaze()
        val searcher = Crawler(start.position, Maze.mazeArray)
        searcher.crawl()
        assertEquals(end, Solver.endOfMaze)
    }
}
