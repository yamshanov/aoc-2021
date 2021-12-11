package day11

import readInput

class Grid(private val grid: Array<IntArray>) {
    val size: Int
    private val n: Int
    private val m: Int
    private val flashLevel = 9
    private val directions = listOf(
        -1 to -1,
        0 to -1,
        1 to -1,
        -1 to 0,
        1 to 0,
        -1 to 1,
        0 to 1,
        1 to 1
    )

    init {
        check(grid.isNotEmpty())
        check(grid.all { it.size == grid[0].size })
        n = grid.size - 1
        m = grid[0].size - 1
        size = (n + 1) * (m + 1)
    }

    fun step(): Int {
        incAll()

        val visited = HashSet<Pair<Int, Int>>()
        forAll { i, j -> if (grid[i][j] > flashLevel) incAround(i, j, visited) }

        return countAndReset()
    }

    private fun forAll(action: (Int, Int) -> Unit) {
        for (i in 0..n) {
            for (j in 0..m) {
                action(i, j)
            }
        }
    }

    private fun incAll() = forAll { i, j -> ++grid[i][j] }

    private fun inc(i: Int, j: Int): Int = if (i in 0..n && j in 0..m) ++grid[i][j] else 0

    private fun incAround(i: Int, j: Int, visited: MutableSet<Pair<Int, Int>>) {
        val p = i to j
        if (p in visited) return
        visited += p

        directions.map { (di, dj) -> i + di to j + dj }.forEach { (i, j) ->
            if (inc(i, j) > flashLevel) incAround(i, j, visited)
        }
    }

    private fun countAndReset(): Int {
        var count = 0
        forAll { i, j ->
            if (grid[i][j] > flashLevel) {
                grid[i][j] = 0
                ++count
            }
        }

        return count
    }
}

private fun parseLine(line: String): IntArray = IntArray(line.length) { line[it].digitToInt() }

fun parse(lines: List<String>): Array<IntArray> = lines.map { it.trim() }.map { parseLine(it) }.toTypedArray()

fun part1(grid: Grid): Int = (1..100).sumOf { grid.step() }

fun part2(grid: Grid): Int = generateSequence(1) { it + 1 }.dropWhile { grid.step() != grid.size }.first()

fun main() {
    val input = readInput("day11/input")

    println(part1(Grid(parse(input))))
    println(part2(Grid(parse(input))))
}
