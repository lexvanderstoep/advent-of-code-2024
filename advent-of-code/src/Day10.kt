import java.io.File

class Day10(inputPath: String) {
    private val heightMap: Map<Pair<Int, Int>, Int> = readInput(inputPath)

    fun computePartOne() = heightMap.keys.sumOf(::computeScore)

    fun computePartTwo() = heightMap.keys.sumOf(::computeRating)

    private fun computeScore(position: Pair<Int, Int>) = findReachablePeaks(position, 0).distinct().size

    private fun computeRating(position: Pair<Int, Int>) = findReachablePeaks(position, 0).size

    private fun findReachablePeaks(position: Pair<Int, Int>, expectedHeight: Int): List<Pair<Int, Int>> {
        if (!heightMap.containsKey(position) || heightMap[position] != expectedHeight) {
            return emptyList()
        }

        if (expectedHeight == 9) return listOf(position)

        val adjacentPositions = listOf(
            position.first + 1 to position.second,
            position.first to position.second + 1,
            position.first - 1 to position.second,
            position.first to position.second - 1
        )

        return adjacentPositions
            .flatMap { findReachablePeaks(it, expectedHeight + 1) }
            .toList()
    }

    private fun readInput(inputPath: String) = File(inputPath)
        .readLines()
        .flatMapIndexed { y, line ->
            line.mapIndexed { x, height -> (x to y) to height.digitToInt() }
        }
        .toMap()
}

fun main() {
    val day10 = Day10("resources/day_10_input.txt")
    println("Part one: ${day10.computePartOne()}")
    println("Part two: ${day10.computePartTwo()}")
}