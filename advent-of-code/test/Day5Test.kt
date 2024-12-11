import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day5Test {
    private val day5 = Day5("resources/day_5_input.txt")

    @Test
    fun computePartOne() {
        assertEquals(6949, day5.computePartOne())
    }

    @Test
    fun computePartTwo() {
        assertEquals(4145, day5.computePartTwo());
    }
}