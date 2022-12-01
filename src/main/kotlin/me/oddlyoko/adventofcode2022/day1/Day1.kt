package me.oddlyoko.adventofcode2022.day1

fun main() {
    Day1()
}

class Day1 {
    init {
        val data = readFile()
        val sum: ArrayList<Int> = arrayListOf()
        var curr = 0
        data.forEach {
            if (it.isEmpty()) {
                sum.add(curr)
                curr = 0
            } else
                curr += it.toInt()
        }
        sum.add(curr)
        sum.sortDescending()
        println("Part 1: ${sum.first()}")

        println("Part 2: ${sum.take(3).sum()}")
    }

    private fun readFile(): List<String> =
        this::class.java.getResourceAsStream("day1.txt")?.bufferedReader()?.readLines() ?: listOf()
}
