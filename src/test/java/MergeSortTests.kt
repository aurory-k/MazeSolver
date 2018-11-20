import mergeSort.NormalMergeSorter
import mergeSort.RXMergeSorter
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

class NormalMergeSortTests {

    @Test
    fun `random list is correctly sorted`() {
        val sorter = NormalMergeSorter()
        val list = listGen.generateList(1000000)
        assertThat(sorter.sort(list)).isEqualTo(list.sorted())
    }

    @Test
    fun `list is correctly sorted`() {
        val sorter = NormalMergeSorter()
        val unsortedList = listOf(9,7,8,6,4,5,3,2,1)
        assertThat(sorter.sort(unsortedList)).containsExactly(1,2,3,4,5,6,7,8,9)
    }

    @Test
    fun `list is correctly sorted when input is empty`() {
        val sorter = NormalMergeSorter()
        val unsortedList = listOf<Int>()
        assertThat(sorter.sort(unsortedList)).isEmpty()
    }

    @Test
    fun `list is correctly sorted when input is of size 1`() {
        val sorter = NormalMergeSorter()
        val unsortedList = listOf(1)
        assertThat(sorter.sort(unsortedList)).containsExactly(1)
    }

    @Test
    fun `list is correctly sorted when input is of size 2`() {
        val sorter = NormalMergeSorter()
        val unsortedList = listOf(3,1)
        assertThat(sorter.sort(unsortedList)).containsExactly(1,3)
    }
}

class RXMergeSortTests {

    @Test
    fun `random list is correctly sorted`() {
        val sorter = RXMergeSorter
        val list = listGen.generateList(100000)
        assertThat(sorter.sort(list).blockingGet()).isEqualTo(list.sorted())
    }

    @Test
    fun `list is correctly sorted`() {
        val sorter = RXMergeSorter
        val unsortedList = listOf(9,7,8,6,4,5,3,2,1)
        assertThat(sorter.sort(unsortedList).blockingGet()).containsExactly(1,2,3,4,5,6,7,8,9)
    }

    @Test
    fun `list is correctly sorted when input is empty`() {
        val sorter = RXMergeSorter
        val unsortedList = listOf<Int>()
        assertThat(sorter.sort(unsortedList).blockingGet()).isEmpty()
    }

    @Test
    fun `list is correctly sorted when input is of size 1`() {
        val sorter = RXMergeSorter
        val unsortedList = listOf(1)
        assertThat(sorter.sort(unsortedList).blockingGet()).containsExactly(1)
    }

    @Test
    fun `list is correctly sorted when input is of size 2`() {
        val sorter = RXMergeSorter
        val unsortedList = listOf(3,1)
        assertThat(sorter.sort(unsortedList).blockingGet()).containsExactly(1,3)
    }
}

object listGen {
    private var random = Random()


    fun generateList(size: Int): List<Int>{
        return (1..size).map { random.nextInt() }
    }

}
