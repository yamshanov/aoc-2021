package day12

import readInput
import java.util.LinkedList

const val START = "start"
const val END = "end"

interface Path {
    val caves: List<String>
    val last: String
    fun go(cave: String): Path?
}

class Path1(
    override val caves: List<String> = listOf(START),
    private val visited: Set<String> = setOf(START)
) : Path {
    override val last = caves.last()
    private val isFinished = last == END

    override fun go(cave: String): Path? = if (!isFinished && cave !in visited) {
        val updatedVisited = if (cave[0].isUpperCase()) visited else visited + cave

        Path1(caves + cave, updatedVisited)
    } else {
        null
    }
}

class Path2(
    override val caves: List<String> = listOf(START),
    private val visited: Set<String> = setOf(START),
    private val twiceFlag: Boolean = false
) : Path {
    override val last = caves.last()
    private val isFinished = last == END
    private var nextTwice = false

    override fun go(cave: String): Path? = if (!isFinished && !isVisited(cave)) {
        val updatedVisited = if (cave[0].isUpperCase()) visited else visited + cave
        val twice = nextTwice || twiceFlag
        nextTwice = false

        Path2(caves + cave, updatedVisited, twice)
    } else {
        null
    }

    private fun isVisited(cave: String): Boolean {
        if (cave in visited) {
            if (twiceFlag || cave == START) return true
            nextTwice = true
        }

        return false
    }
}

fun parse(lines: List<String>): Map<String, List<String>> = lines
    .map { it.trim() }
    .map { it.split("-") }
    .flatMap { listOf(it[0] to it[1], it[1] to it[0]) }
    .groupBy({ it.first }, { it.second })

fun paths(graph: Map<String, List<String>>, start: Path): List<List<String>> {
    val paths = mutableListOf<Path>()
    val queue = LinkedList<Path>().apply { add(start) }

    while (queue.isNotEmpty()) {
        val path = queue.remove()
        for (next in graph.getOrDefault(path.last, emptyList())) {
            path.go(next)?.let {
                if (next == END) paths.add(it) else queue.add(it)
            }
        }
    }

    return paths.map { it.caves }
}

fun part1(graph: Map<String, List<String>>): Int = paths(graph, Path1()).size

fun part2(graph: Map<String, List<String>>): Int = paths(graph, Path2()).size

fun main() {
    val input = readInput("day12/input")
    val graph = parse(input)

    println(part1(graph))
    println(part2(graph))
}
