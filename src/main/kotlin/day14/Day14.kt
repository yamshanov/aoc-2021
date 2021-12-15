package day14

import readInput

class Polymer(private val template: List<Char>, private val rules: Map<Pair<Char, Char>, Char>) {
    private var cache = HashMap<Triple<Char, Char, Int>, Map<Char, Long>>()

    fun grow(steps: Int): Map<Char, Long> {
        cache = HashMap()
        val count = template
            .windowed(2)
            .map { (e1, e2) -> grow2Elements(e1, e2, steps) }
            .reduce { m1, m2 -> merge(m1, m2) }
            .toMutableMap()

        template.subList(1, template.size - 1).forEach { count[it] = count.getValue(it) - 1 }
        return count
    }

    fun grow2Elements(e1: Char, e2: Char, step: Int): Map<Char, Long> {
        val cacheKey = Triple(e1, e2, step)
        if (cache.containsKey(cacheKey)) return cache.getValue(cacheKey)

        if (step == 0) {
            val result = if (e1 != e2) mapOf(e1 to 1L, e2 to 1L) else mapOf(e1 to 2L)
            cache[cacheKey] = result

            return result
        }

        val between = rules.getValue(e1 to e2)
        val left = grow2Elements(e1, between, step - 1)
        val right = grow2Elements(between, e2, step - 1)
        val result = merge(left, right, between)
        cache[cacheKey] = result

        return result
    }

    private fun merge(m1: Map<Char, Long>, m2: Map<Char, Long>): MutableMap<Char, Long> {
        val result = m1.toMutableMap().withDefault { 0 }
        m2.forEach { (k, v) -> result.merge(k, v) { c1, c2 -> c1 + c2 } }

        return result
    }

    private fun merge(m1: Map<Char, Long>, m2: Map<Char, Long>, dup: Char): Map<Char, Long> {
        val result = merge(m1, m2)
        result[dup] = result.getValue(dup) - 1

        return result
    }
}

private fun parseRule(line: String): Pair<Pair<Char, Char>, Char> = line
    .split(" -> ")
    .let { (it[0][0] to it[0][1]) to it[1][0] }

fun part1(polymer: Polymer): Long {
    val elementCounts = polymer.grow(10)
    return elementCounts.maxOf { it.value } - elementCounts.minOf { it.value }
}

fun part2(polymer: Polymer): Long {
    val elementCounts = polymer.grow(40)
    return elementCounts.maxOf { it.value } - elementCounts.minOf { it.value }
}

fun main() {
    val input = readInput("day14/input")
    val template: List<Char> = input[0].asIterable().toList()
    val rules = input.subList(2, input.size)
        .map { parseRule(it) }.associate { it }

    val polymer = Polymer(template, rules)

    println(part1(polymer))
    println(part2(polymer))
}
