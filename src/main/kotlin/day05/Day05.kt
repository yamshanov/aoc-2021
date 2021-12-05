package day05

import readInput

typealias Point = Pair<Int, Int>

private fun parsePoint(p: String): Point {
    val (x, y) = p.trim().split(",").map { it.toInt() }
    return x to y
}

fun parseLine(line: String): Pair<Point, Point> {
    val (start, end) = line.trim().split("->").map(::parsePoint)
    return start to end
}

private fun range(n1: Int, n2: Int): IntProgression = if (n1 > n2) n1 downTo n2 else n1..n2

fun lineToPoints1(start: Point, end: Point): List<Point> =
    if (start.first == end.first) range(start.second, end.second).map { start.first to it }
    else if (start.second == end.second) range(start.first, end.first).map { it to start.second }
    else emptyList()

private fun diagonal(start: Point, end: Point): List<Point> =
    range(start.first, end.first).zip(range(start.second, end.second)).toList()

fun lineToPoints2(start: Point, end: Point): List<Point> = lineToPoints1(start, end).ifEmpty { diagonal(start, end) }

fun part1(lines: List<Pair<Point, Point>>): Int {
    val pointCount = lines.flatMap { lineToPoints1(it.first, it.second) }.groupingBy { it }.eachCount()

    return pointCount.values.count { it > 1 }
}

fun part2(lines: List<Pair<Point, Point>>): Int {
    val pointCount = lines.flatMap { lineToPoints2(it.first, it.second) }.groupingBy { it }.eachCount()

    return pointCount.values.count { it > 1 }
}

fun main() {
    val input = readInput("day05/input")
    val lines = input.map(::parseLine)

    println(part1(lines))
    println(part2(lines))
}
