import java.io.File

class Day12(inputPath: String) {
    private val garden: Set<Plot> = readInput(inputPath)

    fun computePartOne() = divideIntoRegions().sumOf { it.size * computePerimeter(it) }

    fun computePartTwo() = divideIntoRegions().sumOf { it.size * computeSides(it) }

    private fun divideIntoRegions(): Set<Set<Plot>> {
        val visitedPlots = mutableSetOf<Plot>()
        val regions = mutableSetOf<Set<Plot>>()
        garden.forEach { plot ->
            if (!visitedPlots.contains(plot)) {
                val region = visitRegion(plot)
                regions.add(region)
                visitedPlots.addAll(region)
            }
        }
        return regions
    }

    private fun computePerimeter(region: Set<Plot>) = region.sumOf { plot -> 4 - getAdjacentPlots(plot, region).size }

    private fun computeSides(region: Set<Plot>) = region.sumOf { computeConvexity(it, region) } +
            garden.filter { !region.contains(it) }.sumOf { computeConcavity(it, region) }


    private fun computeConvexity(plot: Plot, region: Set<Plot>): Int {
        val adjacentPlots = getAdjacentPlots(plot, region)
        if (adjacentPlots.size == 0) {
            return 4
        }
        if (adjacentPlots.size == 1) {
            var diagonalOppositePlots = listOf<Plot>()
            val oppositePosition = plot.position.plus(plot.position.minus(adjacentPlots[0].position))
            if (plot.position.x == adjacentPlots[0].position.x) {
                diagonalOppositePlots = listOf(
                    Plot(Position(oppositePosition.x - 1, oppositePosition.y), plot.plantType),
                    Plot(Position(oppositePosition.x + 1, oppositePosition.y), plot.plantType)
                )
            }
            if (plot.position.y == adjacentPlots[0].position.y) {
                diagonalOppositePlots = listOf(
                    Plot(Position(oppositePosition.x, oppositePosition.y - 1), plot.plantType),
                    Plot(Position(oppositePosition.x, oppositePosition.y + 1), plot.plantType)
                )
            }
            val diagonalOppositePlotsInRegion = diagonalOppositePlots.count { region.contains(it) }
            if (diagonalOppositePlotsInRegion == 2) {
                return 0
            }
            if (diagonalOppositePlotsInRegion == 1) {
                return 1
            }
            return 2
        }
        if (adjacentPlots.size == 2 &&
            adjacentPlots[0].position.x != adjacentPlots[1].position.x &&
            adjacentPlots[0].position.y != adjacentPlots[1].position.y
        ) {
            val diagonalPlot = Plot(
                plot.position
                    .plus(plot.position.minus(adjacentPlots[0].position))
                    .plus(plot.position.minus(adjacentPlots[1].position)),
                plot.plantType
            )
            if (diagonalPlot in region) {
                return 0
            }
            return 1
        }
        return 0
    }

    private fun computeConcavity(plot: Plot, region: Set<Plot>): Int {
        val adjacentPlots = getAdjacentPlots(plot, region)
        if (adjacentPlots.size == 4) {
            return 4
        }
        if (adjacentPlots.size == 3) {
            return 2
        }
        if (adjacentPlots.size == 2 &&
            adjacentPlots[0].position.x != adjacentPlots[1].position.x &&
            adjacentPlots[0].position.y != adjacentPlots[1].position.y
        ) {
            return 1
        }
        return 0
    }

    private fun getAdjacentPlots(plot: Plot, plots: Set<Plot>) = plots.filter {
        it.position in listOf(
            Position(plot.position.x - 1, plot.position.y),
            Position(plot.position.x, plot.position.y - 1),
            Position(plot.position.x + 1, plot.position.y),
            Position(plot.position.x, plot.position.y + 1)
        )
    }

    private fun visitRegion(plot: Plot): Set<Plot> {
        val visitedPlots = mutableSetOf<Plot>()
        val plotsToVisit = mutableListOf(plot)
        val region = mutableSetOf<Plot>()
        while (plotsToVisit.isNotEmpty()) {
            val visitingPlot = plotsToVisit.removeFirst()

            if (visitedPlots.contains(visitingPlot)) continue
            visitedPlots.add(visitingPlot)

            if (visitingPlot.plantType != plot.plantType) continue

            region.add(visitingPlot)
            plotsToVisit.addAll(getAdjacentPlots(visitingPlot, garden))
        }
        return region
    }

    private fun readInput(inputPath: String) = File(inputPath)
        .readLines()
        .flatMapIndexed { y, line ->
            line.mapIndexed { x, plantType -> Plot(Position(x, y), plantType) }
        }
        .toSet()

    data class Position(val x: Int, val y: Int) {
        fun plus(other: Position) = Position(x + other.x, y + other.y)

        fun minus(other: Position) = Position(x - other.x, y - other.y)
    }

    data class Plot(val position: Position, val plantType: Char)
}

fun main() {
    val day12 = Day12("resources/day_12_input.txt")
    println("Part one: ${day12.computePartOne()}")
    println("Part two: ${day12.computePartTwo()}")
}