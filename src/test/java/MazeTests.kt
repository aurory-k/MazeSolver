import org.junit.Test
import kotlin.test.assertEquals

class MazeTests {
    @Test
    fun `crawler finds correct junctions`() {
        var stringMaze = """
*|*|*|*|*|*|*
*| | | | | |*
*| |*|*|*| |*
S| |*|*|*| |E
*| |*|*|*| |*
*| | | | | |*
*|*|*|*|*|*|*
            """.trimIndent()
        val maze = Maze.generateMazeFromString(stringMaze)
        println(maze.toString())
        val searcher = Crawler(Position(0, 3), maze)
        searcher.crawl()
        Solver.listOfJunctions.forEach { junction ->
            println(junction.toString())
        }
        assertEquals(5, Solver.listOfJunctions.size)
    }

    @Test
    fun `maze randomly generates correctly`() {
        val mazeGenerator = MazeGenerator()
        val (maze, start, end) = mazeGenerator.generateMaze(20,20)
        val searcher = Crawler(start.position, maze)
        searcher.crawl()
        assertEquals(end, Solver.endOfMaze)
    }
}
