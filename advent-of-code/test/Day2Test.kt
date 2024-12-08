import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day2Test {
    private val day2 = Day2("resources/day_2_input.txt")

    @Test
    fun computePartOne() {
        assertEquals(463, day2.computePartOne())
    }

    @Test
    fun computePartTwo() {
        assertEquals(514, day2.computePartTwo());
    }
}