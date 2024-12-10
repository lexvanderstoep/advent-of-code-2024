import java.io.File

class Day4(inputPath: String) {
    private val directions =
        listOf((-1 to 0), (-1 to 1), (0 to 1), (1 to 1), (1 to 0), (1 to -1), (0 to -1), (-1 to -1))
    private val word = listOf('X', 'M', 'A', 'S')

    private val crossIndices = listOf((-1 to -1), (-1 to 1), (1 to 1), (1 to -1))
    private val crossWord = listOf('M', 'S', 'S', 'M')

    private val grid: List<List<Char>>

    init {
        grid = parseInput(inputPath)
    }

    fun computePartOne() =
        grid.indices.sumOf { row ->
            grid[row].indices.sumOf { column ->
                directions.count { direction ->
                    checkWord(row, column, direction)
                }
            }
        }

    fun computePartTwo() =
        grid.indices.sumOf { row ->
            grid[row].indices.sumOf { column ->
                (1..4).count { rotation ->
                    checkCrossWord(row, column, rotate(crossWord, rotation))
                }
            }
        }

    private fun checkWord(row: Int, column: Int, direction: Pair<Int, Int>): Boolean {
        word.indices.forEach { index ->
            val expectedLetter = word[index]
            val rowToCheck = row + index * direction.first
            val columnToCheck = column + index * direction.second
            if (!validIndex(rowToCheck, columnToCheck)) {
                return false
            }
            val actualLetter = grid[rowToCheck][columnToCheck]
            if (expectedLetter != actualLetter) {
                return false
            }
        }
        return true
    }

    private fun checkCrossWord(row: Int, column: Int, expectedLetters: List<Char>): Boolean {
        if (grid[row][column] != 'A') {
            return false
        }
        expectedLetters.zip(crossIndices).forEach { (expectedLetter, crossing) ->
            val rowToCheck = row + crossing.first
            val columnToCheck = column + crossing.second
            if (!validIndex(rowToCheck, columnToCheck)) {
                return false
            }
            val actualLetter = grid[rowToCheck][columnToCheck]
            if (expectedLetter != actualLetter) {
                return false
            }
        }
        return true
    }

    private fun validIndex(row: Int, column: Int): Boolean = row in grid.indices && column in grid[row].indices

    private fun <T> rotate(list: List<T>, shift: Int): List<T> {
        if (list.isEmpty()) return list
        val modularShift = (shift % list.size + list.size) % list.size
        return list.takeLast(modularShift) + list.dropLast(modularShift)
    }

    private fun parseInput(inputPath: String): List<List<Char>> {
        return File(inputPath)
            .readLines()
            .stream()
            .map(String::toList)
            .toList()
    }
}

fun main() {
    val day4 = Day4("resources/day_4_input.txt")
    println("Part one: ${day4.computePartOne()}")
    println("Part two: ${day4.computePartTwo()}")
}