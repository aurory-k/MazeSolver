import org.junit.Test

class MazeTests {
    @Test
    fun `maze randomly generates correctly`() {
        val mazeGenerator = MazeGenerator()
        val (maze, start, end) = mazeGenerator.generateMaze(NUM_ROWS, NUM_COLS)
//        val crawler = Crawler(start.position, maze, startDirection, listOf())
//        crawler.crawl()
//        assertEquals(end, Solver.endOfMaze)
    }
}
