package day01

import readInts

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInts("day01/test-input")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInts("day01/input")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<Int>): Int {
    return input
        .windowed(2)
        .count { it[0] < it[1] }
}

fun part2(input: List<Int>): Int {
    return input
        .windowed(4)
        .count { it.subList(0, 3).sum() < it.subList(1, 4).sum() }
}
