import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day8Test {
    private val day8 = Day8("resources/day_8_input.txt")

    @Test
    fun computePartOne() {
        assertEquals(318, day8.computePartOne())
    }

    @Test
    fun computePartTwo() {
        assertEquals(1126, day8.computePartTwo());
    }
}