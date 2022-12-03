package me.oddlyoko.adventofcode2022.day3

fun main() {
    Day3()
}

class Day3 {
    init {
        val data = readFile()
        var result = 0
        data.forEach {
            val firstPart = it.take(it.length / 2)
            val lastPart = it.takeLast(it.length / 2)
            val char = firstPart.first { char -> lastPart.contains(char) }
            result += charToInt(char)
        }
        println("Part 1: $result")

        // Part 2:
        var result2 = 0
        data.chunked(3)
            .forEach {it ->
                val first = it[0]
                val second = it[1]
                val third = it[2]
                val char = first.first { char -> second.contains(char) && third.contains(char) }
                result2 += charToInt(char)
            }
        println("Part 2: $result2")
    }

    private fun charToInt(char: Char): Int = if (char in 'a'..'z') char - 'a' + 1 else char - 'A' + 27

    private fun readFile(): List<String> =
        this::class.java.getResourceAsStream("day3.txt")?.bufferedReader()?.readLines() ?: listOf()
}
