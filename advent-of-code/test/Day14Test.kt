import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day14Test {
    private val day14 = Day14(101, 103, "resources/day_14_input.txt")

    @Test
    fun computePartOne() {
        assertEquals(230172768, day14.computePartOne())
    }

    @Test
    fun computePartTwo() {
        assertEquals(8087, day14.computePartTwo());
    }
}