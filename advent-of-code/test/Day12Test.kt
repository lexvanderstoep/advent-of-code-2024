import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day12Test {
    private val day12 = Day12("resources/day_12_input.txt")

    @Test
    fun computePartOne() {
        assertEquals(1473276, day12.computePartOne())
    }

    @Test
    fun computePartTwo() {
        assertEquals(901100, day12.computePartTwo());
    }
}