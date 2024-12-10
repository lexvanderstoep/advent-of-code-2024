import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day4Test {
    private val day4 = Day4("resources/day_4_input.txt")

    @Test
    fun computePartOne() {
        assertEquals(2557, day4.computePartOne())
    }

    @Test
    fun computePartTwo() {
        assertEquals(1854, day4.computePartTwo());
    }
}