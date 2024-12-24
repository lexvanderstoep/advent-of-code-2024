import java.io.File
import kotlin.math.roundToLong

private const val CLAW_MACHINE_REGEX = "" +
        "Button A: X\\+(\\d+), Y\\+(\\d+)\n" +
        "Button B: X\\+(\\d+), Y\\+(\\d+)\n" +
        "Prize: X=(\\d+), Y=(\\d+)"


class Day13(inputPath: String) {
    private val clawMachines: List<ClawMachine> = readInput(inputPath)

    fun computePartOne() = clawMachines
        .mapNotNull(::solve)
        .sumOf(::computeCost)

    fun computePartTwo() = clawMachines
        .map(::applyCorrection)
        .mapNotNull(::solve)
        .sumOf(::computeCost)

    private fun solve(clawMachine: ClawMachine): Pair<Long, Long>? {
        val (a, b, p) = clawMachine

        val determinant = (a.x * b.y - b.x * a.y).toDouble()

        if (determinant == 0.0) return null

        val alpha = 1 / determinant * (b.y * p.x - b.x * p.y)
        val beta = 1 / determinant * (-a.y * p.x + a.x * p.y)

        if (p.x != alpha.roundToLong() * a.x + beta.roundToLong() * b.x) return null
        if (p.y != alpha.roundToLong() * a.y + beta.roundToLong() * b.y) return null

        return alpha.roundToLong() to beta.roundToLong()
    }

    private fun computeCost(clawSolution: Pair<Long, Long>) = clawSolution.first * 3 + clawSolution.second

    private fun applyCorrection(clawMachine: ClawMachine) =
        ClawMachine(
            clawMachine.buttonA,
            clawMachine.buttonB,
            clawMachine.prize.plus(Vector(1, 1).scale(10000000000000))
        )

    private fun readInput(inputPath: String) = File(inputPath)
        .readText().split("\n\n")
        .mapNotNull {
            Regex(CLAW_MACHINE_REGEX)
                .find(it)
                ?.destructured
                ?.let { (xA, yA, xB, yB, xP, yP) ->
                    ClawMachine(
                        Vector(xA.toLong(), yA.toLong()),
                        Vector(xB.toLong(), yB.toLong()),
                        Vector(xP.toLong(), yP.toLong())
                    )
                }
        }

    private data class Vector(val x: Long, val y: Long) {
        fun plus(other: Vector) = Vector(x + other.x, y + other.y)

        fun scale(scalar: Long) = Vector(x * scalar, y * scalar)
    }

    private data class ClawMachine(val buttonA: Vector, val buttonB: Vector, val prize: Vector)
}

fun main() {
    val day13 = Day13("resources/day_13_input.txt")
    println("Part one: ${day13.computePartOne()}")
    println("Part two: ${day13.computePartTwo()}")
}