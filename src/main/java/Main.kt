import CellType.*
import io.reactivex.processors.BehaviorProcessor
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JFrame
import javax.swing.JPanel

//729
//243
//81
//27
const val NUM_ROWS = 243
const val NUM_COLS = 243
const val CELL_SIZE = 4

fun main(args: Array<String>) {

    val frame = JFrame("Maze Solver")
    frame.isUndecorated = true
    frame.isVisible = true
    frame.setSize(NUM_ROWS * CELL_SIZE, NUM_COLS * CELL_SIZE)

    val canvas = Canvas()
    canvas.setSize(NUM_ROWS * CELL_SIZE, NUM_COLS * CELL_SIZE)

    frame.contentPane.add(canvas)

    val (maze, start, end) = MazeGenerator.generateMaze(NUM_ROWS, NUM_COLS) { maze ->
        canvas.updateMaze(maze)
        canvas.repaint()
    }

    //val crawler = Crawler(start.position, maze, startDirection, listOf())
    //val crawledPositions = crawler.crawl()
    //var crawledMaze = maze

//    crawledPositions.forEach { position ->
//        crawledMaze = maze.swap(position, Visited)
//    }
}

class Canvas(private var maze: Maze = Maze.generateMazeFromString("")) : JPanel() {

    fun updateMaze(newMaze: Maze) {
        maze = newMaze
    }

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
                    Frontier -> g2.color = Color.MAGENTA
                    Visited -> g2.color = Color.BLUE
                    else -> g2.color = Color.BLACK
                }
                g2.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
            }
        }
    }
}