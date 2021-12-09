package day09

import readInput
import java.util.LinkedList

typealias Point = Pair<Int, Int>

class HeightMap(private val map: List<List<Int>>) {
    private val n: Int
    private val m: Int

    init {
        check(map.isNotEmpty())
        check(map.all { it.isNotEmpty() && it.size == map[0].size })
        n = map.size - 1
        m = map[0].size - 1
    }

    fun get(p: Point) = map[p.first][p.second]

    fun lowPoints(): List<Point> = map.indices.flatMap { i ->
        map[i].indices.filter { j -> isLowPoint(i, j) }.map { i to it }
    }

    fun basinOf(lowPoint: Point): Set<Point> {
        val basin = mutableSetOf<Point>()
        val queue = LinkedList<Point>()
        queue.add(lowPoint)

        while (queue.isNotEmpty()) {
            val p = queue.remove()
            basin += p
            val ns = neighbors(p.first, p.second)
                .filterNot { get(it) == 9 }
                .filterNot { it in basin }
            queue += ns
        }

        return basin
    }

    fun basins(): List<Set<Point>> = lowPoints().map { basinOf(it) }

    private fun neighbors(i: Int, j: Int): List<Point> {
        val ns = ArrayList<Point>(4)
        if (i != 0) ns.add((i - 1) to j)
        if (i != n) ns.add((i + 1) to j)
        if (j != 0) ns.add(i to (j - 1))
        if (j != m) ns.add(i to (j + 1))

        return ns
    }

    private fun isLowPoint(i: Int, j: Int): Boolean =
        neighbors(i, j).all { map[i][j] < get(it) }
}

fun parse(lines: List<String>): List<List<Int>> = lines.map { line -> line.map { it.digitToInt() } }

fun part1(heightMap: HeightMap): Int = heightMap.lowPoints().sumOf { heightMap.get(it) + 1 }

fun part2(heightMap: HeightMap): Int = heightMap.basins()
    .map { it.size }
    .sortedDescending()
    .take(3)
    .reduce { acc, e -> acc * e }

fun main() {
    val input = readInput("day09/input")
    val heightMap = HeightMap(parse(input))

    println(part1(heightMap))
    println(part2(heightMap))
}
