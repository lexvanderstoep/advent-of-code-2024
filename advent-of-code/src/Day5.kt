import java.io.File

class Day5(inputPath: String) {
    private val ordering: List<Pair<Int, Int>>
    private val updates: List<List<Int>>

    init {
        val input = parseInput(inputPath)
        ordering = input.first
        updates = input.second
    }

    fun computePartOne() = updates.filter(::isOrdered).sumOf(::median)

    fun computePartTwo() = updates.filter { !isOrdered(it) }.map(::order).sumOf(::median)

    private fun isOrdered(update: List<Int>): Boolean {
        val pageIndices = update.withIndex().associate { it.value to it.index }
        return ordering.all { (left, right) ->
            (pageIndices[left] ?: Int.MIN_VALUE) < (pageIndices[right] ?: Int.MAX_VALUE) }
    }

    private fun order(update: List<Int>) = update.sortedWith(::comparator)

    private fun median(update: List<Int>) = update[update.size / 2]

    private fun comparator(left: Int, right: Int): Int = when {
        ordering.contains(left to right) -> -1
        ordering.contains(right to left) -> 1
        else -> 0
    }

    private fun parseInput(inputPath: String): Pair<List<Pair<Int, Int>>, List<List<Int>>> {
        val (orderingInput, updatesInput) = File(inputPath).readText().split("\n\n")
        val ordering = orderingInput.split("\n").map { line ->
            val (left, right) = line.split("|").map(String::toInt)
            (left to right)
        }
        val updates = updatesInput.split("\n").map { it.split(",").map(String::toInt) }
        return ordering to updates
    }
}

fun main() {
    val day5 = Day5("resources/day_5_input.txt")
    println("Part one: ${day5.computePartOne()}")
    println("Part two: ${day5.computePartTwo()}")
}