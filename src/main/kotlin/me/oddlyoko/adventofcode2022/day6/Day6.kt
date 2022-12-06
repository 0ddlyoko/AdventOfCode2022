package me.oddlyoko.adventofcode2022.day6

fun main() {
    Day6()
}

class Day6 {
    init {
        val data = readFile()

        println("Part 1: ${getAnswer(data, 4)}")
        println("Part 2: ${getAnswer(data, 14)}")
    }

    private fun getAnswer(data: List<String>, size: Int): Int {
        var result = 0
        for (i in size .. data[0].length) {
            // Check if 2 chars are the same in the group of size
            if (data[0].toCharArray(i - size, i).toSet().size == size) {
                result = i
                break
            }
        }
        return result
    }

    private fun readFile(): List<String> =
        this::class.java.getResourceAsStream("day6.txt")?.bufferedReader()?.readLines() ?: listOf()
}
