import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day1Test {
    private val day1 = Day1("resources/day_1_input.txt")

    @Test
    fun computePartOne() {
        assertEquals(1651298, day1.computePartOne())
    }

    @Test
    fun computePartTwo() {
        assertEquals(21306195, day1.computePartTwo());
    }
}