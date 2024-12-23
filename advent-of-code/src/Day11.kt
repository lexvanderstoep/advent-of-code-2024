import java.io.File

class Day11(inputPath: String) {
    private val stoneCounts: Map<Long, Long> = readInput(inputPath)

    fun computePartOne() = computeTotalStones(repeatedly(25, ::blink, stoneCounts))

    fun computePartTwo() = computeTotalStones(repeatedly(75, ::blink, stoneCounts))

    private fun blink(stoneCounts: Map<Long, Long>) =
        stoneCounts
            .flatMap { (number, count) ->
                evolve(number).map { it to count }
            }
            .groupingBy { (number, _) -> number }
            .fold(0L) { totalCount, (_, count) -> totalCount + count }

    private fun evolve(number: Long): List<Long> {
        if (number == 0L) return listOf(1)
        val decimals = number.toString()
        if (decimals.length % 2 == 0) {
            return listOf(
                decimals.take(decimals.length / 2).toLong(),
                decimals.drop(decimals.length / 2).toLong()
            )
        } else {
            return listOf(number * 2024)
        }
    }

    private fun <T> repeatedly(times: Int, function: (T) -> T, value: T) =
        generateSequence(value, function).take(times + 1).last()

    private fun computeTotalStones(stoneCounts: Map<Long, Long>) = stoneCounts.entries.sumOf { it.value }

    private fun readInput(inputPath: String) = File(inputPath)
        .readLines()
        .first()
        .split(" ")
        .map(String::toLong)
        .groupingBy { it }.eachCount()
        .mapValues { it.value.toLong() }
}

fun main() {
    val day11 = Day11("resources/day_11_input.txt")
    println("Part one: ${day11.computePartOne()}")
    println("Part two: ${day11.computePartTwo()}")
}