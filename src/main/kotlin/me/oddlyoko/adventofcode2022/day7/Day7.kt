package me.oddlyoko.adventofcode2022.day7

fun main() {
    Day7()
}

interface File {
    fun getAllFiles(): ArrayList<File>
    fun getDirectories(): List<Directory> = getAllFiles().filter { !it.isFile() }.map { it as Directory }
    fun getFile(name: String): File?
    fun getName(): String
    fun getSize(): Int
    fun getParent(): File?
    fun isFile(): Boolean
}

class AFile(
    private val name: String,
    private val size: Int,
    private val parent: File,
): File {
    override fun getAllFiles(): ArrayList<File> = arrayListOf()
    override fun getFile(name: String): File? = null
    override fun getName(): String = name
    override fun getSize(): Int = size
    override fun getParent(): File = parent
    override fun isFile(): Boolean = true
    override fun toString(): String = "File($name)"
}

class Directory(
    private val name: String,
    private val parent: File?,
    private val files: ArrayList<File> = arrayListOf(),
): File {
    override fun getAllFiles(): ArrayList<File> = files
    override fun getFile(name: String): File? = files.firstOrNull { it.getName() == name }
    override fun getName(): String = name
    override fun getSize(): Int = files.sumOf { it.getSize() }
    override fun getParent(): File? = parent
    override fun isFile(): Boolean = false
    override fun toString(): String = "Directory($name)"
}

class Day7 {
    init {
        val data = readFile()
        val rootDirectory = Directory("/", null)
        var currentDirectory: File = rootDirectory

        data.forEach {
            if (it.startsWith("$ ")) {
                // Command
                if (it.startsWith("$ cd ")) {
                    val name = it.split(" ")[2]
                    currentDirectory = when (name) {
                        "/" -> rootDirectory
                        ".." -> currentDirectory.getParent()!!
                        else -> currentDirectory.getFile(name)!!
                    }
                }
            } else {
                if (it.startsWith("dir ")) {
                    val dirName = it.split(" ")[1]
                    currentDirectory.getAllFiles().add(Directory(dirName, currentDirectory))
                } else {
                    val split = it.split(" ")
                    currentDirectory.getAllFiles().add(AFile(split[1], split[0].toInt(), currentDirectory))
                }
            }
        }
        // Get files
        val directories = getDirectories(rootDirectory)
        val result1 = directories.filter { it.getSize() <= 100000 }.sumOf { it.getSize() }
        println("Part 1: $result1")

        val minNeededSpace = rootDirectory.getSize() - 40000000
        val result2 = directories.filter { it.getSize() >= minNeededSpace }.minOfOrNull { it.getSize() }
        println("Part 2: $result2")
    }

    private fun getDirectories(directory: Directory): List<Directory> {
        val result = arrayListOf<Directory>()
        val directories = directory.getDirectories()
        result.addAll(directories)
        result.addAll(directories.flatMap { getDirectories(it) })
        return result
    }

    private fun readFile(): List<String> =
        this::class.java.getResourceAsStream("day7.txt")?.bufferedReader()?.readLines() ?: listOf()
}
