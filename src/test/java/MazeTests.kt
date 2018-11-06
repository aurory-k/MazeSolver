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
        val mazeGenerator = MazeGenerator()
        mazeGenerator.generateMazeFromString(maze)
        println(MazeGenerator().toString())
        val searcher = Crawler(Position(0, 3), MazeGenerator.mazeArray)
        searcher.crawl()
        Solver.listOfJunctions.forEach { junction ->
            println(junction.toString())
        }
        assertEquals(5, Solver.listOfJunctions.size)
    }

    @Test
    fun `maze generates correctly`() {
//        var maze = """
//*|*|*|*|*|*|*
//*|*|*|*|*|*|*
//*|*|*|*|*|*|*
//*|*|*|*|*|*|*
//*|*|*|*|*|*|*
//*|*|*|*|*|*|*
//*|*|*|*|*|*|*
//            """.trimIndent()
//        val mazeGenerator = MazeGenerator()
//        mazeGenerator.generateMazeFromString(maze)
//        println(MazeGenerator().toString())
        val mazeGenerator = MazeGenerator()
        mazeGenerator.initializeMaze(5,5)
        val start = mazeGenerator.generateMaze()
//        val searcher = Crawler(start.position, MazeGenerator.mazeArray)
//        searcher.crawl()
//        Solver.listOfJunctions.forEach { junction ->
//            println(junction.toString())
//        }
    }
}
