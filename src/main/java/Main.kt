import CellType.*
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JFrame
import javax.swing.JPanel

const val NUM_ROWS = 81
const val NUM_COLS = 81
const val CELL_SIZE = 10

fun main(args: Array<String>) {
    val mazeGenerator = MazeGenerator()
    val (maze, start, end) = mazeGenerator.generateMaze(NUM_ROWS, NUM_COLS)

    //val crawler = Crawler(start.position, maze, startDirection, listOf())
    //val crawledPositions = crawler.crawl()
    //var crawledMaze = maze

//    crawledPositions.forEach { position ->
//        crawledMaze = maze.swap(position, Visited)
//    }

    val frame = JFrame()
    frame.isUndecorated = true
    frame.isVisible = true
    frame.setSize(NUM_ROWS * CELL_SIZE, NUM_COLS * CELL_SIZE)

    val canvas = Canvas(maze)
    canvas.setSize(NUM_ROWS * CELL_SIZE, NUM_COLS * CELL_SIZE)

    frame.contentPane.add(canvas)
}

class Canvas(private val maze: Maze) : JPanel() {

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D

        g2.color = Color.BLACK
        for (y in 0 until maze.getColSize()) {
            for (x in 0 until maze.getRowSize()) {
                val type = maze.get(x, y).type
                when (type) {
                    Free -> g2.color = Color.WHITE
                    Wall -> g2.color = Color.BLACK
                    Start -> g2.color = Color.GREEN
                    End -> g2.color = Color.RED
                    Visited -> g2.color = Color.BLUE
                    else -> g2.color = Color.BLACK
                }
                g2.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
            }
        }
    }
}