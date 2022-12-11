package me.oddlyoko.adventofcode2022.day11

fun main() {
    Day11()
}

class Day11 {

    init {
        val data = readFile()
        println("Part 1: ${calculateSolution(data, 20, 3)}")
        println("Part 2: ${calculateSolution(data, 10000, 1)}")
    }

    private fun calculateSolution(data: List<String>, numberOfRun: Int, diviseBy: Int): Long {
        val numberOfMonkey = (data.size + 1) / 7

        val items = arrayListOf<ArrayList<Long>>()
        val numberOfInspectedItems = arrayListOf<Long>()

        // In order to avoid having large numbers, we need to modulo the whole solution by the multiplication of all test divisible.
        val modulo = (0 until numberOfMonkey).map {
            val startingMonkeyIdx = it * 7 + 3
            data[startingMonkeyIdx].replace("  Test: divisible by ", "").toInt()
        }.reduce { acc, i -> acc * i }

        // Initialize all
        for (i in 0 until numberOfMonkey) {
            items.add(arrayListOf())
            numberOfInspectedItems.add(0)
            val startingItemIdx = i * 7 + 1
            data[startingItemIdx].replace("  Starting items: ", "").split(", ").forEach {
                items[i].add(it.toLong())
            }
        }

        // Run it x times
        for (a in 0 until numberOfRun) {
            // Run for each monkey
            for (i in 0 until numberOfMonkey) {
                val itemIdx = i * 7
                val divisibleBy = data[itemIdx + 3].replace("  Test: divisible by ", "").toInt()
                val trueMonkey = data[itemIdx + 4].replace("    If true: throw to monkey ", "").toInt()
                val falseMonkey = data[itemIdx + 5].replace("    If false: throw to monkey ", "").toInt()
                // Run for each item
                for (worry in items[i]) {
                    // Operation
                    val operation = data[itemIdx + 2]
                        .replace("  Operation: new = ", "")
                        .replace("old", worry.toString())
                        .split(" ")
                    val result = (when (operation[1]) {
                        "+" -> operation[0].toLong() + operation[2].toLong()
                        "-" -> operation[0].toLong() - operation[2].toLong()
                        "*" -> operation[0].toLong() * operation[2].toLong()
                        "/" -> operation[0].toLong() / operation[2].toLong()
                        else -> operation[0].toLong() + operation[2].toLong()
                    } / diviseBy)% modulo
                    // Now, check where we add this number
                    if (result % divisibleBy == 0L)
                    // Add to true monkey
                        items[trueMonkey].add(result)
                    else
                    // Add to false monkey
                        items[falseMonkey].add(result)
                    // Add inspected item
                    numberOfInspectedItems[i]++
                }
                // Clear this list, as we have parsed it (We assume a monkey doesn't keep the item)
                items[i].clear()
            }
        }

        // Get two max items
        numberOfInspectedItems.sortDescending()
        return numberOfInspectedItems[0] * numberOfInspectedItems[1]
    }

    private fun readFile(): List<String> =
        this::class.java.getResourceAsStream("day11.txt")?.bufferedReader()?.readLines() ?: listOf()
}
