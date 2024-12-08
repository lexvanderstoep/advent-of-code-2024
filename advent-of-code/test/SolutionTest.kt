import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SolutionTest {
    private val solution = Solution("resources/day_1_input.txt")

    @Test
    fun computePartOne() {
        assertEquals(1651298, solution.computePartOne())
    }

    @Test
    fun computePartTwo() {
        assertEquals(21306195, solution.computePartTwo());
    }
}