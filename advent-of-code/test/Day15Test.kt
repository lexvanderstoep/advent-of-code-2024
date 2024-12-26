import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day15Test {
    private val day15 = Day15("resources/day_15_input.txt")

    @Test
    fun computePartOne() {
        assertEquals(1438161, day15.computePartOne())
    }

    @Test
    fun computePartTwo() {
        assertEquals(1437981, day15.computePartTwo());
    }
}