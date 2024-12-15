import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day6Test {
    private val day6 = Day6("resources/day_6_input.txt")

    @Test
    fun computePartOne() {
        assertEquals(4559, day6.computePartOne())
    }

    @Test
    fun computePartTwo() {
        assertEquals(1604, day6.computePartTwo());
    }
}