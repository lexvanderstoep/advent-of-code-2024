import java.io.File
import kotlin.math.abs

class Solution(inputPath: String) {
    private val left: List<Int>
    private val right: List<Int>
    init {
        val (parsedLeft, parsedRight) = parseInput(inputPath)
        left = parsedLeft
        right = parsedRight
    }

    fun computePartOne(): Int {
        return left
            .sorted()
            .zip(right.sorted())
            .sumOf { (left, right) -> abs(left - right) }
    }

    fun computePartTwo(): Int {
        val occurrenceCount = right.groupingBy { it }.eachCount()
        return left.sumOf { it * occurrenceCount.getOrDefault(it, 0) }
    }

    private fun parseInput(inputPath: String): Pair<List<Int>, List<Int>> {
        val input = File(inputPath).readText()
        val inputLines = input.split("\n")
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()
        inputLines.forEach {
            val (first, second) = it.split("   ").map { it.toInt() }
            leftList.add(first)
            rightList.add(second)
        }
        return Pair(leftList, rightList)
    }
}

fun main() {
    val solution = Solution("resources/day_1_input.txt")
    val answerPartOne = solution.computePartOne()
    println("Part one: $answerPartOne")
    val answerPartTwo = solution.computePartTwo()
    println("Part two: $answerPartTwo")
}