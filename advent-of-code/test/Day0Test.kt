import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day0Test {
    private val day0 = Day0("resources/day_0_input.txt")

    @Test
    fun computePartOne() {
        assertEquals(0, day0.computePartOne())
    }

    @Test
    fun computePartTwo() {
        assertEquals(0, day0.computePartTwo());
    }
}