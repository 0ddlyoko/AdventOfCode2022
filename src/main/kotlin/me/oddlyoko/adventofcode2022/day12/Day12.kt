package me.oddlyoko.adventofcode2022.day12

import java.util.*

fun main() {
    Day12()
}

data class Position(val x: Int, val y: Int, val h: Int) {
    var positionToEnd: Position? = null
    var numberToEnd: Int = 0
}

class Day12 {
    private val data: List<String>
    private val map: List<List<Position>>
    private lateinit var startPosition: Position
    private lateinit var endPosition: Position

    init {
        data = readFile()
        map = arrayListOf()
        data.forEachIndexed { y, it ->
            val list = arrayListOf<Position>()
            repeat(it.length) { x ->
                val height = when (it[x]) {
                    'S' -> 0
                    'E' -> 26
                    else -> data[y][x] - 'a'
                }
                list.add(Position(x, y, height))
                if (it[x] == 'S')
                    startPosition = list.last()
                else if (it[x] == 'E') {
                    endPosition = list.last()
                    endPosition.positionToEnd = endPosition
                }
            }
            map.add(list)
        }

        val queueToCheck = LinkedList<Position>()
        queueToCheck.add(endPosition)
        var currentPosition: Position?
        do {
            currentPosition = queueToCheck.poll()
            val rangeAcceptableHeight = (currentPosition.h - 1)..26
            val neighbors = getNeighbors(currentPosition)
            neighbors.filter { it.positionToEnd == null && it.h in rangeAcceptableHeight }.forEach {
                queueToCheck.add(it)
                // Add link to this neighbor
                it.positionToEnd = currentPosition
                it.numberToEnd = currentPosition.numberToEnd + 1
            }
        } while (queueToCheck.isNotEmpty())

        println("Part 1: ${startPosition.numberToEnd}")
        val solution2 = map.flatten().filter { it.h == 0 && it.numberToEnd != 0 }.minOf { it.numberToEnd }
        println("Part 2: $solution2")
    }

    fun getNeighbors(position: Position): List<Position> {
        val result: ArrayList<Position> = arrayListOf()
        // Left
        if (position.x > 0)
            result += map[position.y][position.x - 1]
        // Right
        if (position.x < data[0].length - 1)
            result += map[position.y][position.x + 1]
        // Top
        if (position.y > 0)
            result += map[position.y - 1][position.x]
        // Down
        if (position.y < data.size - 1)
            result += map[position.y + 1][position.x]
        return result
    }

    private fun readFile(): List<String> =
        this::class.java.getResourceAsStream("day12.txt")?.bufferedReader()?.readLines() ?: listOf()
}
