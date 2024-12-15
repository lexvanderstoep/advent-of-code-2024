import java.io.File

class Day6(inputPath: String) {
    private val input = parseInput(inputPath)
    private var obstacles: Array<Array<Boolean>> = input.obstacles.copyDeep()
    private var position: Pair<Int, Int> = input.position.copy()
    private var direction: Pair<Int, Int> = input.direction.copy()
    private var visited: Array<Array<MutableSet<Pair<Int, Int>>>> = input.visited.copyDeep()
    private var looped: Boolean = false

    fun computePartOne(): Int {
        while (move()) {}
        return visited.sumOf { row -> row.count { it.isNotEmpty() } }
    }

    fun computePartTwo(): Int {
         return obstacles.indices.sumOf { y ->
            obstacles[y].indices.count { x ->
                resetInput()
                obstacles[y][x] = true
                while (move()) {}
                looped
            }
        }
    }

    private fun move(): Boolean {
        if (visited[position.second][position.first].contains(direction)) {
            looped = true
            return false
        }
        visited[position.second][position.first].add(direction)

        if (isObstacleAhead()) {
            rotate()
        } else {
            step()
        }
        return isInBounds(position)
    }

    private fun isObstacleAhead(): Boolean {
        val newPosition = (position.first + direction.first) to (position.second + direction.second)
        return isInBounds(newPosition) && obstacles[newPosition.second][newPosition.first]
    }

    private fun isInBounds(position: Pair<Int, Int>) =
        position.second in obstacles.indices && position.first in obstacles[position.second].indices

    private fun rotate() {
        direction = -direction.second to direction.first
    }

    private fun step() {
        position = position.first + direction.first to position.second + direction.second
    }

    private fun resetInput() {
        obstacles = input.obstacles.copyDeep()
        position = input.position.copy()
        direction = input.direction.copy()
        visited = input.visited.copyDeep()
        looped = false
    }

    private fun Array<Array<Boolean>>.copyDeep() = Array(size) { this[it].copyOf() }

    private fun Array<Array<MutableSet<Pair<Int, Int>>>>.copyDeep() =
        Array(size) { y -> Array(this[y].size) { x -> this[y][x].toMutableSet() } }

    private fun parseInput(inputPath: String): Input {
        var position = 0 to 0
        val obstacles = File(inputPath)
            .readLines()
            .mapIndexed { y, line ->
                line.mapIndexed { x, char ->
                    when (char) {
                        '#' -> true
                        '.' -> false
                        '^' -> {
                            position = x to y
                            false
                        }

                        else -> throw IllegalArgumentException("Unexpected character in input")
                    }
                }.toTypedArray()
            }.toTypedArray()
        val visited = Array(obstacles.size) { row ->
            Array(obstacles[row].size) { _ -> mutableSetOf<Pair<Int, Int>>() }
        }
        val direction = 0 to -1
        return Input(obstacles, position, direction, visited)
    }

    data class Input(
        val obstacles: Array<Array<Boolean>>,
        val position: Pair<Int, Int>,
        val direction: Pair<Int, Int>,
        val visited: Array<Array<MutableSet<Pair<Int, Int>>>>
    )
}

fun main() {
    val day6 = Day6("resources/day_6_input.txt")
    println("Part one: ${day6.computePartOne()}")
    println("Part two: ${day6.computePartTwo()}")
}