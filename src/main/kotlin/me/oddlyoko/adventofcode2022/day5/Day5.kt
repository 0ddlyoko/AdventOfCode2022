package me.oddlyoko.adventofcode2022.day5

import kotlin.collections.ArrayList

fun main() {
    Day5()
}

class Day5 {
    init {
        val data = readFile()
        println("Part 1: ${doExercice(data, true)}")
        println("Part 2: ${doExercice(data, false)}")
    }

    private fun doExercice(data: List<String>, reverse: Boolean): String {
        // First, we need the number of columns
        // We can easily calculate it using the number of characters on the first line
        val numberOfColumns = (data[0].length + 1) / 4
        // Now, we can create the array
        val columns = Array(numberOfColumns) { ArrayList<Char>() }
        // We can now fill the column
        var index = 0
        data.takeWhile { !it.startsWith(" 1 ") }.forEach {
            for (i in 0 until numberOfColumns) {
                val letter = it[i * 4 + 1]
                if (letter != ' ')
                    columns[i].add(letter)
            }
            index++
        }
        columns.forEach {
            it.reverse()
        }
        data.takeLast(data.size - index - 2).forEach {
            val array = it.split("move ", " from ", " to ")
            val instruction = array.takeLast(array.size - 1).map { it.toInt() }
            val toMove = columns[instruction[1] - 1].takeLast(instruction[0])
            if (reverse)
                columns[instruction[2] - 1].addAll(toMove.reversed())
            else
                columns[instruction[2] - 1].addAll(toMove)

            // Now, remove it from the original list
            repeat(instruction[0]) {
                columns[instruction[1] - 1].removeLast()
            }
        }
        return columns.map { it.last() }.joinToString("")
    }

    private fun readFile(): List<String> =
        this::class.java.getResourceAsStream("day5.txt")?.bufferedReader()?.readLines() ?: listOf()
}
