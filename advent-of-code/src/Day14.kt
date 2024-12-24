import java.io.File

class Day14(width: Int, height: Int, inputPath: String) {
    private val ROBOT_REGEX = Regex("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)")

    private val dimensions = Vector(width, height)
    private val robots = readInput(inputPath)

    fun computePartOne() = computeSafetyFactor(repeatedly(robots, ::simulateStep, 100))

    fun computePartTwo() = generateSequence(robots, ::simulateStep).takeWhile { !isChristmasTree(it) }.count()

    private fun <T> repeatedly(initialValue: T, f: (T) -> T, iterations: Int) =
        generateSequence(initialValue, f).take(iterations + 1).last()

    private fun simulateStep(robots: List<Robot>) = robots.map {
        Robot(it.position.plus(it.velocity).modulo(dimensions), it.velocity)
    }

    private fun computeSafetyFactor(robots: List<Robot>) = robots
        .groupBy(::computeQuadrant)
        .filterKeys { it.first != null && it.second != null }
        .entries
        .map { it.value.size }
        .fold(1, Int::times)

    private fun longestStreak(numbers: List<Int>) = numbers
        .distinct()
        .sorted()
        .fold(0 to 0) { (count, last), number -> (if (number == last + 1) count + 1 else 1) to number }
        .first

    private fun isChristmasTree(robots: List<Robot>) = robots
        .groupBy { it.position.y }
        .mapValues { longestStreak(it.value.map { it.position.x }) }
        .filterValues { it > 5 }
        .isNotEmpty()

    private fun computeQuadrant(robot: Robot) = computeQuadrantIndex(robot.position.x, dimensions.x) to
            computeQuadrantIndex(robot.position.y, dimensions.y)

    private fun computeQuadrantIndex(position: Int, length: Int) =
        if (position < (length - 1) / 2) 0
        else if (position > length / 2) 1
        else null

    private fun readInput(inputPath: String) = File(inputPath)
        .readLines()
        .mapNotNull {
            ROBOT_REGEX.find(it)
                ?.destructured
                ?.let { (xP, yP, xV, yV) ->
                    Robot(Vector(xP.toInt(), yP.toInt()), Vector(xV.toInt(), yV.toInt()))
                }
        }

    private data class Vector(val x: Int, val y: Int) {
        fun plus(other: Vector) = Vector(x + other.x, y + other.y)
        fun modulo(modulus: Vector) = Vector(
            ((x % modulus.x) + modulus.x) % modulus.x,
            ((y % modulus.y) + modulus.y) % modulus.y
        )
    }

    private data class Robot(val position: Vector, val velocity: Vector)
}

fun main() {
    val day14 = Day14(101, 103, "resources/day_14_input.txt")
    println("Part one: ${day14.computePartOne()}")
    println("Part two: ${day14.computePartTwo()}")
}