package me.oddlyoko.adventofcode2022.day9

fun main() {
    Day9()
}

data class Position(val x: Int, val y: Int) {
    fun addX() = Position(x + 1, y)
    fun removeX() = Position(x - 1, y)
    fun addY() = Position(x, y + 1)
    fun removeY() = Position(x, y - 1)
    fun isNextTo(position: Position): Boolean = x in (position.x - 1) .. (position.x + 1) && y in (position.y - 1) .. (position.y + 1)
}

class Day9 {

    init {
        val SIZE = 9
        val data = readFile()

        val all: Array<Position> = arrayOf(Position(0, 0), Position(0, 0), Position(0, 0), Position(0, 0), Position(0, 0), Position(0, 0), Position(0, 0), Position(0, 0), Position(0, 0), Position(0, 0))
        val queueSet: Array<HashSet<Position>> = arrayOf(hashSetOf(), hashSetOf(), hashSetOf(), hashSetOf(), hashSetOf(), hashSetOf(), hashSetOf(), hashSetOf(), hashSetOf(), hashSetOf())
        data.forEach {
            val split = it.split(" ")
            for (i in 0 until split[1].toInt()) {
                val head = all[0]
                all[0] = when (split[0]) {
                    "R" -> head.addX()
                    "U" -> head.addY()
                    "L" -> head.removeX()
                    "D" -> head.removeY()
                    else -> head
                }
                // Add head
                queueSet[0].add(all[0])
                for (queueI in 1 .. SIZE) {
                    all[queueI] = moveNextTo(all[queueI - 1], all[queueI])
                    queueSet[queueI].add(all[queueI])
                }
            }
        }
        println("Part 1: ${queueSet[1].size}")
        println("Part 2: ${queueSet[SIZE].size}")
    }

    /**
     * Check if queue is next to head, or move queue
     */
    fun moveNextTo(head: Position, queue: Position): Position {
        // Don't move if next to
        if (head.isNextTo(queue))
            return queue
        // Top / Down
        if (head.x == queue.x) {
            if (head.y > queue.y)
                return queue.addY()
            return queue.removeY()
        }
        if (head.y == queue.y) {
            if (head.x > queue.x)
                return queue.addX()
            return queue.removeX()
        }
        // Top left/right
        if (head.y > queue.y) {
            // Top right
            if (head.x > queue.x)
                return queue.addY().addX()
            // Top left
            return queue.addY().removeX()
        }
        // Bot left/right
        if (head.x > queue.x)
            return queue.removeY().addX()
        return queue.removeY().removeX()
    }

    private fun readFile(): List<String> =
        this::class.java.getResourceAsStream("day9.txt")?.bufferedReader()?.readLines() ?: listOf()
}
