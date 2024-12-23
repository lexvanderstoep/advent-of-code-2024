import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day11Test {
    private val day11 = Day11("resources/day_11_input.txt")

    @Test
    fun computePartOne() {
        assertEquals(203457, day11.computePartOne())
    }

    @Test
    fun computePartTwo() {
        assertEquals(241394363462435, day11.computePartTwo());
    }
}