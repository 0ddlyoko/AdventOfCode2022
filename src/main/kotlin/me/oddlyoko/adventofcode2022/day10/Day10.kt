package me.oddlyoko.adventofcode2022.day10

fun main() {
    Day10()
}

class Day10 {

    init {
        val data = readFile()
        val memory = arrayListOf(1)
        var lastMemory = 1

        data.forEach {
            if (it == "noop") {
                // Skip one
                memory.add(lastMemory)
                return@forEach
            }
            // addx
            val toAdd = it.split(" ")[1].toInt()
            memory.add(lastMemory)
            lastMemory += toAdd
            memory.add(lastMemory)
        }

        var sum = 0
        for (i in 20 .. memory.size step 40) {
            sum += (i * memory[i - 1])
        }
        println("Part 1: $sum")

        memory.forEachIndexed { index, i ->
            if (index % 40 == 0)
                println("")
            if (index % 40 in (i - 1) .. (i + 1))
                print("#")
            else
                print(".")
        }
    }

    private fun readFile(): List<String> =
        this::class.java.getResourceAsStream("day10.txt")?.bufferedReader()?.readLines() ?: listOf()
}
