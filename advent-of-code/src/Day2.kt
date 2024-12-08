import java.io.File
import kotlin.math.abs

class Day2(inputPath: String) {
    private val reports: List<List<Int>>

    init {
        reports = parseInput(inputPath)
    }

    private fun isSafeReport(report: List<Int>): Boolean {
        val gradients = report.zipWithNext{ a, b -> a.compareTo(b) }
        val distances = report.zipWithNext{ a, b -> abs(a - b) }
        return gradients.distinct().size == 1 && distances.all{ it in 1..3 }
    }

    private fun isToleratedSafeReport(report: List<Int>): Boolean {
        return report.indices.any { index ->
            isSafeReport(report.filterIndexed { i, _ -> i != index })
        }
    }

    fun computePartOne() = reports.count(::isSafeReport)

    fun computePartTwo() = reports.count(::isToleratedSafeReport)

    private fun parseInput(inputPath: String): List<List<Int>> {
        return File(inputPath).readLines().map {
            it.split(" ").map(String::toInt)
        }
    }
}

fun main() {
    val day2 = Day2("resources/day_2_input.txt")
    val answerPartOne = day2.computePartOne()
    println("Part one: $answerPartOne")
    val answerPartTwo = day2.computePartTwo()
    println("Part two: $answerPartTwo")
}