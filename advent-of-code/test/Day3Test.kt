import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day3Test {
    private val day3 = Day3("resources/day_3_input.txt")

    @Test
    fun computePartOne() {
        assertEquals(168539636, day3.computePartOne())
    }

    @Test
    fun computePartTwo() {
        assertEquals(97529391, day3.computePartTwo());
    }
}