package me.oddlyoko.adventofcode2022.day2

fun main() {
    Day2()
}

class Day2 {
    init {
        val mapToLoose = mapOf(Pair('A', 'Z'), Pair('B', 'X'), Pair('C', 'Y'))
        val mapToWin = mapOf(Pair('A', 'Y'), Pair('B', 'Z'), Pair('C', 'X'))
        val data = readFile()
        var totalPoints = 0
        var totalPoints2 = 0
        data.forEach {
            val line: List<String> = it.split(" ")
            val opponent = line[0][0]
            val me = line[1][0]
            // Add score of the shape we choose
            totalPoints += getPoint(me, opponent)
            var toChoose = 'X'
            when (me) {
                // Loose
                'X' -> toChoose = mapToLoose[opponent]!!
                // Draw
                'Y' -> toChoose = opponent + ('X' - 'A')
                // Win
                'Z' -> toChoose = mapToWin[opponent]!!
            }
            totalPoints2 += getPoint(toChoose, opponent)
        }
        println("Part 1: $totalPoints")
        println("Part 2: $totalPoints2")
    }

    private fun getPoint(me: Char, opponent: Char): Int {
        var score = me - 'W'
        // Check if we won. Won = 6, Draw = 3
        if (opponent + ('X' - 'A') == me)
            // Draw
            score += 3
        else if (opponent == 'A' && me == 'Y' || opponent == 'B' && me == 'Z' || opponent == 'C' && me == 'X')
            // Won
            score += 6
        return score
    }

    private fun readFile(): List<String> =
        this::class.java.getResourceAsStream("day2.txt")?.bufferedReader()?.readLines() ?: listOf()
}
