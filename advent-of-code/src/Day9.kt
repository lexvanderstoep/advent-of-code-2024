import java.io.File
import java.util.*

class Day9(inputPath: String) {
    private val denseDiskMap: List<Int> = readInput(inputPath)

    fun computePartOne() = checksum(compactBlocks(diskBlockMap(denseDiskMap)))

    fun computePartTwo() = checksum(fileMapToBlockMap(compactFiles(diskFileMap(denseDiskMap))))

    private fun diskBlockMap(diskMap: List<Int>) =
        diskMap.flatMapIndexed { index, digit ->
            if (index % 2 == 0) List(digit) { index / 2 }
            else List(digit) { null }
        }

    private fun diskFileMap(denseDiskMap: List<Int>): List<Pair<Int, Int?>> {
        return denseDiskMap.mapIndexed { index, digit ->
            if (index % 2 == 0) digit to index / 2
            else digit to null
        }
    }

    private fun fileMapToBlockMap(fileMap: List<Pair<Int, Int?>>) = fileMap.flatMap { (size, digit) ->
        List(size) { digit }
    }

    private fun compactBlocks(blockMap: List<Int?>): List<Int?> {
        val compactedFileMap = blockMap.toMutableList()
        var freeIndex = 0
        for (blockIndex in compactedFileMap.indices.reversed()) {
            while (freeIndex < blockIndex && compactedFileMap[freeIndex] != null) {
                freeIndex++
            }
            if (freeIndex < blockIndex) {
                compactedFileMap[freeIndex] = compactedFileMap[blockIndex]
                compactedFileMap[blockIndex] = null
            } else {
                break
            }
        }
        return compactedFileMap
    }

    private fun compactFiles(fileMap: List<Pair<Int, Int?>>): List<Pair<Int, Int?>> {
        val compactedFileMap = fileMap.toMutableList()
        var blockIndex = compactedFileMap.lastIndex
        while (blockIndex > 0) {
            if (compactedFileMap[blockIndex].second == null) {
                blockIndex--
                continue
            }

            val freeIndex = compactedFileMap.indexOfFirst { (size, digit) ->
                digit == null && size >= compactedFileMap[blockIndex].first
            }
            if (freeIndex !in 0..blockIndex) {
                blockIndex--
                continue
            }

            val freeBlocks = compactedFileMap[freeIndex].copy()
            val fileBlocks = compactedFileMap[blockIndex].copy()

            compactedFileMap[freeIndex] = fileBlocks
            compactedFileMap[blockIndex] = fileBlocks.first to null
            val freeLeftOverSize = freeBlocks.first - fileBlocks.first
            if (freeLeftOverSize > 0) {
                compactedFileMap.add(freeIndex + 1, freeLeftOverSize to null)
            } else {
                blockIndex--
            }
        }
        return compactedFileMap
    }

    private fun checksum(diskBlockMap: List<Int?>) =
        diskBlockMap.mapIndexed { index, file -> index.toLong() * (file ?: 0) }.sum()

    private fun readInput(inputPath: String) = File(inputPath).readText().map { it.digitToInt() }
}

fun main() {
    val day9 = Day9("resources/day_9_input.txt")
    println("Part one: ${day9.computePartOne()}")
    println("Part two: ${day9.computePartTwo()}")
}