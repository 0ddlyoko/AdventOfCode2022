package me.oddlyoko.adventofcode2022.day15

import kotlin.math.abs

fun main() {
    Day15()
}

private data class Sensor(val x: Int, val y: Int, val beacon: Beacon) {
    val d: Int
        get() = abs(x - beacon.x) + abs(y - beacon.y)
}
private data class Beacon(val x: Int, val y: Int)

class Day15 {
    private val data: List<String>
    private val sensors: ArrayList<Sensor>
    private val beacons: ArrayList<Beacon>
    private val line: HashSet<Int>

    init {
        data = readFile()
        sensors = ArrayList()
        beacons = ArrayList()
        line = HashSet()
        // To resolve this problem, we don't need to create the full map. Only calculus work :D
        // First, we need to read the file, and transform it into usable data
        data.forEach {
            val coords = it.removePrefix("Sensor at ").split(": closest beacon is at ")
                .map { it.replace("x=", "").replace(" y=", "") }
                .map { it.split(",").map { it.toInt() } }
            val beacon = Beacon(coords[1][0], coords[1][1])
            val sensor = Sensor(coords[0][0], coords[0][1], beacon)
            beacons.add(beacon)
            sensors.add(sensor)

            // Get the distance between this sensor and the line
            val distanceToY = abs(sensor.y - Y)
            if (distanceToY > 0) {
                val distanceToSplit = sensor.d - distanceToY
                IntRange(-distanceToSplit, distanceToSplit).forEach {
                    line.add(sensor.x + it)
                }
            }
        }
        // Remove sensors & beacons from this line
        sensors.forEach {
            if (it.y == Y)
                line.remove(it.x)
        }
        beacons.forEach {
            if (it.y == Y)
               line.remove(it.x)
        }
        // Now that we have data, we can calculate the distance between sensors and line
        var result = line.size
        println("Part 1: $result")

        // Part 2
        var result2 = 0
        println("Part 2: $result2")
    }

    private fun readFile(): List<String> =
        this::class.java.getResourceAsStream("day15.txt")?.bufferedReader()?.readLines() ?: listOf()

    companion object {
        val Y = 2000000
    }
}
