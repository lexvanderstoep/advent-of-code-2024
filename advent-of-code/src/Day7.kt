import java.io.File

class Day7(inputPath: String) {
    private val equations: List<Pair<Long, List<Int>>> = parseInput(inputPath)

    fun computePartOne() = equations
        .filter { isSolvable(it, listOf(Operator.ADD, Operator.MULTIPLY)) }
        .sumOf(::value)

    fun computePartTwo() = equations
        .filter { isSolvable(it, Operator.entries) }
        .sumOf(::value)

    private fun value(equation: Pair<Long, List<Int>>) = equation.first

    private fun isSolvable(equation: Pair<Long, List<Int>>, operators: List<Operator>) =
        generateOperatorCombinations(equation.second.size, operators).any { operationCombination ->
            isSolved(equation, operationCombination)
        }

    private fun isSolved(equation: Pair<Long, List<Int>>, operators: List<Operator>): Boolean {
        val solution = equation.first
        val operands = equation.second

        val value = operands
            .drop(1)
            .zip(operators)
            .fold(operands.first().toLong()) { acc, (operand, operator) ->
                when (operator) {
                    Operator.ADD -> acc + operand
                    Operator.MULTIPLY -> acc * operand
                    Operator.CONCATENATION -> (acc.toString() + operand.toString()).toLong()
                }
            }
        return solution == value
    }

    private fun generateOperatorCombinations(numberOfOperands: Int, operators: List<Operator>): List<List<Operator>> {
        if (numberOfOperands == 2) {
            return operators.map { listOf(it) }
        }
        val operatorsCombinations = mutableListOf<List<Operator>>()
        for (operator in operators) {
            val subCombinations = generateOperatorCombinations(numberOfOperands - 1, operators)
            operatorsCombinations.addAll(subCombinations.map { listOf(operator) + it })
        }
        return operatorsCombinations
    }

    private fun parseInput(inputPath: String) = File(inputPath)
        .readLines()
        .map { line ->
            val (solutionInput, operandsInput) = line.split(":")
            val solution = solutionInput.toLong()
            val operands = operandsInput
                .split(" ")
                .filter(String::isNotEmpty)
                .map(String::toInt)
            solution to operands
        }

    private enum class Operator {
        ADD,
        MULTIPLY,
        CONCATENATION,
        ;
    }
}

fun main() {
    val day7 = Day7("resources/day_7_input.txt")
    println("Part one: ${day7.computePartOne()}")
    println("Part two: ${day7.computePartTwo()}")
}