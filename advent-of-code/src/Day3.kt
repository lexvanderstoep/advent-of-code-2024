import java.io.File

class Day3(inputPath: String) {
    private val memory: String = File(inputPath).readText()

    fun computePartOne(): Int {
        val pattern = Regex("mul\\(\\d+,\\d+\\)")
        val instructions = pattern.findAll(memory).map(MatchResult::value)
        return instructions.map(::evaluateInstruction).sum()
    }

    fun computePartTwo(): Int {
        val pattern = Regex("mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\)")
        val instructions = pattern.findAll(memory).map(MatchResult::value)
        var enabled = true
        return instructions
            .map {
                when (it) {
                    "don't()" -> {
                        enabled = false; 0
                    }

                    "do()" -> {
                        enabled = true; 0
                    }

                    else -> if (enabled) evaluateInstruction(it) else 0
                }
            }
            .sum()
    }

    private fun evaluateInstruction(instruction: String): Int {
        return Regex("\\d+").findAll(instruction).fold(1) { acc, result -> acc * result.value.toInt() }
    }

}

fun main() {
    val day3 = Day3("resources/day_3_input.txt")
    val answerPartOne = day3.computePartOne()
    println("Part one: $answerPartOne")
    val answerPartTwo = day3.computePartTwo()
    println("Part two: $answerPartTwo")
}