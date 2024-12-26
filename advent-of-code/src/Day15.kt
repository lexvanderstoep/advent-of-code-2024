import java.io.File

class Day15(private val inputPath: String) {
    fun computePartOne(): Int {
        val (warehouse, moves) = readInput(inputPath)
        moves.forEach { move -> warehouse.robot.move(warehouse, move) }
        return computeCoordinateScore(warehouse)
    }

    fun computePartTwo(): Int {
        val (warehouse, moves) = readInput(inputPath).let { applyCorrection(it.first) to it.second }
        moves.forEach { move -> warehouse.robot.move(warehouse, move) }
        return computeCoordinateScore(warehouse)
    }

    private fun computeCoordinateScore(warehouse: Warehouse) = warehouse.boxes
        .sumOf { it.leftPosition.x + 100 * it.leftPosition.y }

    private fun applyCorrection(warehouse: Warehouse): Warehouse {
        val walls = warehouse.walls.flatMap { wall ->
            setOf(
                Wall(Vector(wall.position.x * 2, wall.position.y)),
                Wall(Vector(wall.position.x * 2 + 1, wall.position.y))
            )
        }
        val boxes = warehouse.boxes.map { box ->
            Box(
                Vector(box.leftPosition.x * 2, box.leftPosition.y),
                Vector(box.rightPosition.x * 2 + 1, box.rightPosition.y)
            )
        }
        val robot = warehouse.robot.let { Robot(Vector(it.position.x * 2, it.position.y)) }
        return Warehouse(walls, boxes, robot)
    }

    private fun readInput(inputPath: String): Pair<Warehouse, List<Vector>> {
        val (warehouseInput, movesInput) = File(inputPath).readText().split("\n\n")
        val walls = mutableListOf<Wall>()
        val boxes = mutableListOf<Box>()
        var robot: Robot? = null
        warehouseInput.lines().forEachIndexed { y, line ->
            line.forEachIndexed { x, field ->
                when (field) {
                    '#' -> walls.add(Wall(Vector(x, y)))
                    'O' -> boxes.add(Box(Vector(x, y), Vector(x, y)))
                    '@' -> {
                        robot = Robot(Vector(x, y))
                    }
                }
            }
        }

        val moves = movesInput.filter { it != '\n' }.map {
            when (it) {
                '<' -> Vector(-1, 0)
                '>' -> Vector(1, 0)
                '^' -> Vector(0, -1)
                'v' -> Vector(0, 1)
                else -> throw IllegalArgumentException("Illegal move found")
            }
        }
        return Warehouse(walls, boxes, robot!!) to moves
    }

    private data class Warehouse(
        val walls: List<Wall>,
        val boxes: List<Box>,
        val robot: Robot
    ) {
        fun getRigidBodies(): List<RigidBody> {
            return walls + boxes + robot
        }
    }

    private data class Vector(val x: Int, val y: Int) {
        fun plus(other: Vector) = Vector(x + other.x, y + other.y)
    }

    private interface RigidBody {
        fun occupies(position: Vector): Boolean

        fun canMove(warehouse: Warehouse, direction: Vector): Boolean {
            val nextPositions = getNextPositions(direction)
            return nextPositions.all { nextPosition ->
                warehouse.getRigidBodies().filter { it.occupies(nextPosition) && it != this }
                    .all { it.canMove(warehouse, direction) }
            }
        }

        fun move(warehouse: Warehouse, direction: Vector)

        fun getNextPositions(direction: Vector): List<Vector>
    }

    private class Wall(val position: Vector) : RigidBody {
        override fun occupies(position: Vector) = this.position == position

        override fun canMove(warehouse: Warehouse, direction: Vector) = false

        override fun move(warehouse: Warehouse, direction: Vector) {}

        override fun getNextPositions(direction: Vector) = listOf<Vector>()
    }

    private class Box(var leftPosition: Vector, var rightPosition: Vector) : RigidBody {
        override fun occupies(position: Vector) = leftPosition.x <= position.x &&
                position.x <= rightPosition.x &&
                leftPosition.y == position.y &&
                rightPosition.y == position.y

        override fun move(warehouse: Warehouse, direction: Vector) {
            if (!canMove(warehouse, direction)) return

            getNextPositions(direction).forEach { nextPosition ->
                warehouse.getRigidBodies()
                    .filter { it.occupies(nextPosition) && it != this }
                    .forEach { it.move(warehouse, direction) }
            }
            leftPosition = leftPosition.plus(direction)
            rightPosition = rightPosition.plus(direction)
        }

        override fun getNextPositions(direction: Vector) =
            listOf(leftPosition.plus(direction), rightPosition.plus(direction)).distinct()
    }

    private class Robot(var position: Vector) : RigidBody {
        override fun occupies(position: Vector) = this.position == position

        override fun move(warehouse: Warehouse, direction: Vector) {
            if (!canMove(warehouse, direction)) return

            getNextPositions(direction).forEach { nextPosition ->
                warehouse.getRigidBodies()
                    .filter { it.occupies(nextPosition) }
                    .forEach { it.move(warehouse, direction) }
            }

            position = position.plus(direction)
        }

        override fun getNextPositions(direction: Vector) = listOf(position.plus(direction))
    }
}

fun main() {
    val day15 = Day15("resources/day_15_input.txt")
    println("Part one: ${day15.computePartOne()}")
    println("Part two: ${day15.computePartTwo()}")
}