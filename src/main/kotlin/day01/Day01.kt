package day01

import readInts

fun main() {
    val input = readInts("day01/input")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<Int>): Int {
    return input.asSequence()
        .windowed(2)
        .count { it[0] < it[1] }
}

fun part2(input: List<Int>): Int {
    return input.asSequence()
        .windowed(3)
        .map { it.sum() }
        .windowed(2)
        .count { it[0] < it[1] }
}
