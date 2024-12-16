import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day7Test {
    private val day7 = Day7("resources/day_7_input.txt")

    @Test
    fun computePartOne() {
        assertEquals(267566105056, day7.computePartOne())
    }

    @Test
    fun computePartTwo() {
        assertEquals(116094961956019, day7.computePartTwo());
    }
}