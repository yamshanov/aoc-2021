package day03

import readInput

fun main() {
    val input = readInput("day03/input").map { parse(it) }
    println(part1(input))
    println(part2(input))
}

fun part1(numbers: List<List<UInt>>): UInt {
    val ones = numbers.reduce(::add)
    val gamma = gamma(ones, (numbers.size / 2).toUInt())
    val mask = UInt.MAX_VALUE.shl(ones.size).inv()
    val epsilon = gamma.inv() and mask

    return gamma * epsilon
}

fun part2(numbers: List<List<UInt>>): UInt {
    val oxygenRating = rating(numbers) { zeroes, ones -> zeroes > ones }
    val co2Rating = rating(numbers) { zeroes, ones -> zeroes <= ones }
    return oxygenRating * co2Rating
}

fun parse(line: String): List<UInt> = line.toCharArray().map { it.digitToInt().toUInt() }

fun add(n1: List<UInt>, n2: List<UInt>): List<UInt> = n1.zip(n2).map { (d1, d2) -> d1 + d2 }

fun gamma(ones: List<UInt>, threshold: UInt): UInt = ones.fold(0u) { acc, n ->
    var result = acc shl 1
    if (n > threshold) result += 1u
    result
}

fun filterByBitCriteria(
    numbers: List<List<UInt>>,
    position: Int,
    predicate: (Int, Int) -> Boolean
): List<List<UInt>> {
    val (zeros, ones) = numbers.partition { it[position] == 0u }
    return if (predicate(zeros.size, ones.size)) zeros else ones
}

fun toUInt(number: List<UInt>): UInt = number.fold(0u) { acc, b -> (acc shl 1) + b }

fun rating(numbers: List<List<UInt>>, predicate: (Int, Int) -> Boolean): UInt {
    val bits = numbers[0].size
    var position = 0
    var ns = numbers

    while (position < bits && ns.size > 1) {
        ns = filterByBitCriteria(ns, position, predicate)
        position++
    }

    return toUInt(ns.first())
}
