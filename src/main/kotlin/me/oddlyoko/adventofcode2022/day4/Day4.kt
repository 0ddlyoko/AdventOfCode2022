package me.oddlyoko.adventofcode2022.day4

fun main() {
    Day4()
}

class Day4 {
    init {
        val data = readFile()
        var result = 0
        var result2 = 0
        data.forEach {
            val d = it.split(",")
            val rangeFirst = d[0].split("-").toList().map { nbr -> nbr.toInt() }
            val rangeSecond = d[1].split("-").toList().map { nbr -> nbr.toInt() }
            if (rangeFirst[0] in rangeSecond[0]..rangeSecond[1] && rangeFirst[1] in rangeSecond[0]..rangeSecond[1])
                result++
            else if (rangeSecond[0] in rangeFirst[0]..rangeFirst[1] && rangeSecond[1] in rangeFirst[0]..rangeFirst[1])
                result++
            // Part 2
            if (rangeFirst[0] in rangeSecond[0]..rangeSecond[1] || rangeFirst[1] in rangeSecond[0]..rangeSecond[1])
                result2++
            else if (rangeSecond[0] in rangeFirst[0]..rangeFirst[1] || rangeSecond[1] in rangeFirst[0]..rangeFirst[1])
                result2++
        }
        println("Part 1: $result")
        println("Part 2: $result2")
    }

    private fun readFile(): List<String> =
        this::class.java.getResourceAsStream("day4.txt")?.bufferedReader()?.readLines() ?: listOf()
}
