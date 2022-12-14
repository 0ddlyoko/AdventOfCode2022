package me.oddlyoko.adventofcode2022.day14

fun main() {
    Day14()
}

enum class Type {
    AIR,
    SAND,
    ROCK,
}

class Day14 {
    private val data: List<String>
    private val map: Array<Array<Type>>
    private var minY = -1

    init {
        data = readFile()
        map = Array(1000) { Array(1000) { Type.AIR } }
        data.forEach { line ->
            val coords = line.split(" -> ")
            var oldCoordinate = coords[0].split(",").map { it.toInt() }
            coords.forEach {
                val newCoordinate = it.split(",").map { it.toInt() }
                if (oldCoordinate[0] == newCoordinate[0]) {
                    // Y has changed
                    val minCoordinate = oldCoordinate[1].coerceAtMost(newCoordinate[1])
                    val maxCoordinate = oldCoordinate[1].coerceAtLeast(newCoordinate[1])
                    for (i in minCoordinate..maxCoordinate)
                        map[i][newCoordinate[0]] = Type.ROCK
                } else {
                    // X has changed
                    // Y has changed
                    val minCoordinate = oldCoordinate[0].coerceAtMost(newCoordinate[0])
                    val maxCoordinate = oldCoordinate[0].coerceAtLeast(newCoordinate[0])
                    for (i in minCoordinate..maxCoordinate)
                        map[newCoordinate[1]][i] = Type.ROCK
                }
                oldCoordinate = newCoordinate
                if (newCoordinate[1] > minY)
                    minY = newCoordinate[1]
            }
        }

        var finalSandPosition = getFinalSandPosition(arrayOf(SAND_SPAWN[0], SAND_SPAWN[1]))
        var result = 0
        while (finalSandPosition != null) {
            map[finalSandPosition[1]][finalSandPosition[0]] = Type.SAND
            finalSandPosition = getFinalSandPosition(arrayOf(SAND_SPAWN[0], SAND_SPAWN[1]))
            result++
        }

        println("Part 1: $result")
        // Part 2

        var result2 = result
        minY += 2
        // In fact, we can continue the flow
        finalSandPosition = getFinalSandPosition(arrayOf(SAND_SPAWN[0], SAND_SPAWN[1]), minY)
        while (finalSandPosition != null && !finalSandPosition.contentEquals(SAND_SPAWN)) {
            map[finalSandPosition[1]][finalSandPosition[0]] = Type.SAND
            finalSandPosition = getFinalSandPosition(arrayOf(SAND_SPAWN[0], SAND_SPAWN[1]), minY)
            result2++
        }
        result2++
        println("Part 2: $result2")
    }

    private fun getFinalSandPosition(sand: Array<Int>, simulateFloorY: Int = -1): Array<Int>? {
        var currentPosition: Array<Int>? = arrayOf(sand[0], sand[1])
        while (currentPosition != null) {
            val nextPosition = getNextSandPosition(currentPosition)
            if (currentPosition.contentEquals(nextPosition))
                return nextPosition
            if (nextPosition != null && nextPosition[1] == simulateFloorY)
                return currentPosition
            currentPosition = nextPosition
        }
        return null
    }

    private fun getNextSandPosition(sand: Array<Int>): Array<Int>? {
        // Down
        val newPosition = arrayOf(sand[0], sand[1] + 1)
        // Check position
        if (!isInside(newPosition))
            return null
        if (map[newPosition[1]][newPosition[0]] == Type.AIR)
            return newPosition
        // Down left
        newPosition[0]--
        // Check position
        if (!isInside(newPosition))
            return null
        if (map[newPosition[1]][newPosition[0]] == Type.AIR)
            return newPosition
        // Down right
        newPosition[0] += 2
        // Check position
        if (!isInside(newPosition))
            return null
        if (map[newPosition[1]][newPosition[0]] == Type.AIR)
            return newPosition
        return sand
    }

    private fun isInside(sand: Array<Int>): Boolean = sand[0] in 0 until map[0].size && sand[1] in map.indices

    private fun readFile(): List<String> =
        this::class.java.getResourceAsStream("day14.txt")?.bufferedReader()?.readLines() ?: listOf()

    companion object {
        val SAND_SPAWN = arrayOf(500, 0)
    }
}
