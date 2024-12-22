import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day9Test {
    private val day9 = Day9("resources/day_9_input.txt")

    @Test
    fun computePartOne() {
        assertEquals(6332189866718, day9.computePartOne())
    }

    @Test
    fun computePartTwo() {
        assertEquals(6353648390778, day9.computePartTwo());
    }
}