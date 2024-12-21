import java.io.File

class Day8(inputPath: String) {
    private val input = readInput(inputPath)
    private val antennas = input.antennas
    private val dimensions = input.dimensions

    fun computePartOne() = computeInterferenceLocations(listOf(-1, 2)).size

    fun computePartTwo() = computeInterferenceLocations((-dimensions.first..dimensions.first).toList()).size

    private fun computeInterferenceLocations(interferenceMultiples: List<Int>): Set<Pair<Int, Int>> {
        val interferenceLocations: MutableSet<Pair<Int, Int>> = mutableSetOf()
        for (frequencyAntennas in antennas.values) {
            for ((firstAntenna, secondAntenna) in allPairs(frequencyAntennas)) {
                for (i in interferenceMultiples) {
                    val antiNode = firstAntenna.plus(secondAntenna.minus(firstAntenna).scale(i))
                    if (antiNode.inBounds()) {
                        interferenceLocations.add(antiNode)
                    }
                }
            }
        }
        return interferenceLocations
    }

    private fun readInput(inputPath: String): Input {
        val antennaGrid = File(inputPath)
            .readLines()
        val dimensions = antennaGrid.first().length to antennaGrid.size
        val antennas = antennaGrid
            .flatMapIndexed() { y, line ->
                line.mapIndexedNotNull() { x, antenna ->
                    if (antenna != '.') antenna to (x to y) else null
                }
            }
            .groupBy({ (antenna, _) -> antenna }, { (_, coordinate) -> coordinate })
        return Input(antennas, dimensions)
    }

    private fun <T> allPairs(values: List<T>): List<Pair<T, T>> =
        values.flatMapIndexed { index, first -> values.drop(index + 1).map { second -> first to second } }

    private fun Pair<Int, Int>.minus(other: Pair<Int, Int>) = (this.first - other.first) to (this.second - other.second)

    private fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = (this.first + other.first) to (this.second + other.second)

    private fun Pair<Int, Int>.scale(scalar: Int) = (this.first * scalar) to (this.second * scalar)

    private fun Pair<Int, Int>.inBounds() =
        this.first in 0..<dimensions.first && this.second in 0..<dimensions.second

    private data class Input(val antennas: Map<Char, List<Pair<Int, Int>>>, val dimensions: Pair<Int, Int>)
}

fun main() {
    val day8 = Day8("resources/day_8_input.txt")
    println("Part one: ${day8.computePartOne()}")
    println("Part two: ${day8.computePartTwo()}")
}