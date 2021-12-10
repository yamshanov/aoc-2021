package day10

import readInput
import java.util.LinkedList
import java.util.Queue

private val openToClose = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
private val closeToOpen = openToClose.entries.associate { (k, v) -> v to k }
private val closeChars = openToClose.values.toSet()

private val scoresPt1 = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
private val scoresPt2 = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

fun check(line: Queue<Char>): Pair<Char?, List<Char>> {
    val openStack = LinkedList<Char>()

    while (line.isNotEmpty()) {
        val c = line.remove()
        if (c in closeChars) {
            val openChar = openStack.remove()
            if (openChar != closeToOpen[c]) return c to openStack
        } else {
            openStack.push(c)
        }
    }

    return null to openStack
}

fun strToQueue(line: String): Queue<Char> {
    val queue = LinkedList<Char>()
    line.toCollection(queue)

    return queue
}

fun score2(closes: List<Char>): Long =
    closes.fold(0L) { acc, c -> acc * 5 + scoresPt2.getValue(c) }

fun processInput(input: List<String>): Pair<List<Char?>, List<List<Char>>> {
    val (corruptedPairs, incompletePairs) = input
        .map { strToQueue(it) }
        .map { check(it) }
        .partition { (c, _) -> c != null }
    val corrupted = corruptedPairs.map { (c, _) -> c }
    val incompleteOpen = incompletePairs.map { (_, i) -> i }

    return corrupted to incompleteOpen
}

fun part1(chars: List<Char?>): Int = chars.filterNotNull().sumOf { scoresPt1.getValue(it) }

fun part2(incompleteOpen: List<List<Char>>): Long {
    val scores = incompleteOpen
        .map { line -> line.map { openToClose.getValue(it) } }
        .map { score2(it) }
        .sorted()

    return scores[scores.size / 2]
}

fun main() {
    val (corrupted, incompleteOpen) = processInput(readInput("day10/input"))

    println(part1(corrupted))
    println(part2(incompleteOpen))
}
