import org.junit.Test
import kotlin.test.assertEquals

class MazeTests {
    @Test
    fun `maze randomly generates correctly`() {
        val mazeGenerator = MazeGenerator()
        val (maze, start, end, startDirection) = mazeGenerator.generateMaze(NUM_ROWS, NUM_COLS)
        val searcher = Crawler(start.position, maze, startDirection, listOf())
        searcher.crawl()
        assertEquals(end, Solver.endOfMaze)
    }
}
