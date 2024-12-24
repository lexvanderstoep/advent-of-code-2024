import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day13Test {
    private val day13 = Day13("resources/day_13_input.txt")

    @Test
    fun computePartOne() {
        assertEquals(31761, day13.computePartOne())
    }

    @Test
    fun computePartTwo() {
        assertEquals(90798500745591, day13.computePartTwo());
    }
}