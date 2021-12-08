package day08

import readInput
import spacesRegex

class Entry(val patterns: List<String>, output: List<String>) {
    private val segmentsToDigit: Map<Set<Char>, Int> =
        deduceDigits(patterns).mapIndexed { index, segments -> segments to index }.associate { it }
    val outputSegments: List<Set<Char>> = output.map { it.asIterable().toSet() }

    fun outputNumber(): Int = outputSegments.fold(0) { acc, segments ->
        acc * 10 + (segmentsToDigit[segments]
            ?: throw IllegalStateException("No digit for $segments. Patterns $patterns"))
    }
}

fun deduceDigits(patterns: List<String>): Array<Set<Char>> {
    val digits = Array<Set<Char>>(10) { emptySet() }
    val ps = patterns.map { it.asIterable().toSet() }.groupBy { it.size }

    ps[2]?.firstOrNull()?.let { digits[1] = it }
    ps[3]?.firstOrNull()?.let { digits[7] = it }
    ps[4]?.firstOrNull()?.let { digits[4] = it }
    ps[7]?.firstOrNull()?.let { digits[8] = it }

    val fiveSegments = ps[5]?.toSet() ?: emptySet()
    fiveSegments.firstOrNull { (it - digits[1]).size == 3 }?.let { digits[3] = it }
    fiveSegments.firstOrNull { (it - digits[4]).size == 3 }?.let { digits[2] = it }
    fiveSegments.minusElement(digits[3]).minusElement(digits[2]).firstOrNull()?.let { digits[5] = it }

    val sixSegments = ps[6]?.toSet() ?: emptySet()
    sixSegments.firstOrNull { (it - digits[1]).size == 5 }?.let { digits[6] = it }
    sixSegments.firstOrNull { (it - digits[4]).size == 2 }?.let { digits[9] = it }
    sixSegments.minusElement(digits[6]).minusElement(digits[9]).firstOrNull()?.let { digits[0] = it }

    check(digits.all { it.isNotEmpty() }) { "Could not deduce $patterns" }
    return digits
}

fun parse(line: String): Entry {
    val (patternsStr, outputStr) = line.trim().split("|")
    val patterns = patternsStr.trim().split(spacesRegex)
    val output = outputStr.trim().split(spacesRegex)

    check(patterns.size == 10)
    check(output.size == 4)

    return Entry(patterns, output)
}

fun part1(entries: List<Entry>): Int = entries.flatMap { it.outputSegments }.count { it.size != 5 && it.size != 6 }

fun part2(entries: List<Entry>): Int = entries.sumOf { it.outputNumber() }

fun main() {
    val input = readInput("day08/input")
    val entries = input.map(::parse)

    println(part1(entries))
    println(part2(entries))
}
