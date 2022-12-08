package me.oddlyoko.adventofcode2022.day8

fun main() {
    Day8()
}

class Day8 {
    init {
        val data = readFile()
        val map = data.map {
            it.map { char -> char.code - '0'.code }
        }

        var result = 0
        map.forEachIndexed { row, ints ->
            if (row == 0 || row == map.size - 1) {
                result += ints.size
                return@forEachIndexed
            }
            ints.forEachIndexed { col, nbr ->
                if (col == 0 || col == ints.size - 1) {
                    result++
                    return@forEachIndexed
                }
                // Check on left
                run checkLeft@ {
                    for (i in col - 1 downTo 0)
                        if (map[row][i] >= nbr)
                            return@checkLeft
                    // There is place on left, add a point and continue for next
                    result++
                    return@forEachIndexed
                }
                // Check on right
                run checkRight@ {
                    for (i in col + 1 until ints.size)
                        if (map[row][i] >= nbr)
                            return@checkRight
                    // There is place on left, add a point and continue for next
                    result++
                    return@forEachIndexed
                }
                // Check on top
                run checkTop@ {
                    for (i in row - 1 downTo 0)
                        if (map[i][col] >= nbr)
                            return@checkTop
                    // There is place on left, add a point and continue for next
                    result++
                    return@forEachIndexed
                }
                // Check on bottom
                run checkTop@ {
                    for (i in row + 1 until data.size)
                        if (map[i][col] >= nbr)
                            return@checkTop
                    // There is place on left, add a point and continue for next
                    result++
                    return@forEachIndexed
                }
            }
        }
        println("Part 1: $result")

        var maxScore = 0
        var currentScore: Int
        var localScore: Int
        map.forEachIndexed { row, ints ->
            if (row == 0 || row == map.size - 1)
                return@forEachIndexed
            ints.forEachIndexed { col, nbr ->
                if (col == 0 || col == ints.size - 1)
                    return@forEachIndexed
                currentScore = 1
                localScore = 0
                // Check on left
                run checkLeft@ {
                    for (i in col - 1 downTo 0) {
                        localScore++
                        if (map[row][i] >= nbr)
                            return@checkLeft
                    }
                }
                currentScore *= localScore
                localScore = 0
                // Check on right
                run checkRight@ {
                    for (i in col + 1 until ints.size) {
                        localScore++
                        if (map[row][i] >= nbr)
                            return@checkRight
                    }
                }
                currentScore *= localScore
                localScore = 0
                // Check on top
                run checkTop@ {
                    for (i in row - 1 downTo 0) {
                        localScore++
                        if (map[i][col] >= nbr)
                            return@checkTop
                    }
                }
                currentScore *= localScore
                localScore = 0
                // Check on bottom
                run checkTop@ {
                    for (i in row + 1 until data.size) {
                        localScore++
                        if (map[i][col] >= nbr)
                            return@checkTop
                    }
                }
                currentScore *= localScore
                if (currentScore > maxScore)
                    maxScore = currentScore
            }
        }
        println("Part 2: $maxScore")
    }

    private fun readFile(): List<String> =
        this::class.java.getResourceAsStream("day8.txt")?.bufferedReader()?.readLines() ?: listOf()
}
