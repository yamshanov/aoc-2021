package day15

import readInput
import java.util.PriorityQueue

data class Path(val i: Int, val j: Int, val risk: Int, val distance: Int)

class Cave(private val risks: Array<IntArray>) {
    private val n: Int
    private val m: Int
    private val pathComparator = { p1: Path, p2: Path -> (p1.risk + p1.distance) - (p2.risk + p2.distance) }
    private val directions = listOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1)

    init {
        check(risks.isNotEmpty())
        check(risks.all { it.size == risks[0].size })
        n = risks.size - 1
        m = risks[0].size - 1
    }

    fun findLowestRiskPath(): Path {
        val start = Path(0, 0, 0, distance(0, 0))
        var positionCount = 0
        val queue = PriorityQueue(pathComparator).apply { add(start) }
        val visited = HashSet<Pair<Int, Int>>()

        while (queue.isNotEmpty()) {
            val p = queue.remove()
            if (p.i == n && p.j == m) {
                println("Checked $positionCount positions")
                return p
            }

            val position = p.i to p.j
            if (position in visited) continue
            visited.add(p.i to p.j)
            positionCount += 1

            next(p.i, p.j).forEach { (i, j) -> queue.add(Path(i, j, p.risk + risks[i][j], distance(i, j))) }
        }

        error("Unreachable")
    }

    private fun next(i: Int, j: Int): List<Pair<Int, Int>> = directions
        .map { (di, dj) -> (i + di) to (j + dj) }
        .filter { (i, j) -> (i in 0..n) && (j in 0..m) }

    private fun distance(i: Int, j: Int): Int = (n - i) + (m - j)
}

fun main() {
    val input = readInput("day15/input")
    val risks = parse(input)
    val scaledRisks = scale(risks)

    val cave = Cave(risks)
    val bigCave = Cave(scaledRisks)
    println(cave.findLowestRiskPath().risk)
    println(bigCave.findLowestRiskPath().risk)
}

fun parse(lines: List<String>): Array<IntArray> = lines.map { it.trim() }.map { parseLine(it) }.toTypedArray()

fun scale(risks: Array<IntArray>): Array<IntArray> {
    val scaled = Array(risks.size * 5) { IntArray(risks[0].size * 5) }
    val n = risks.size
    val m = risks[0].size

    for (i in scaled.indices) {
        for (j in scaled[i].indices) {
            val originalI = i % n
            val originalJ = j % m
            val newRisk = risks[originalI][originalJ] + i / n + j / m

            scaled[i][j] = if (newRisk > 9) newRisk - 9 else newRisk
        }
    }

    return scaled
}

fun dumpRisks(risks: Array<IntArray>) {
    for (i in risks.indices) {
        for (j in risks[i].indices) print("${risks[i][j]}\t")
        println()
    }
}

private fun parseLine(line: String): IntArray = IntArray(line.length) { line[it].digitToInt() }
