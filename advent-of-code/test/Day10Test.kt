import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day10Test {
    private val day10 = Day10("resources/day_10_input.txt")

    @Test
    fun computePartOne() {
        assertEquals(733, day10.computePartOne())
    }

    @Test
    fun computePartTwo() {
        assertEquals(1514, day10.computePartTwo());
    }
}