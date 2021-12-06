package day06

import readInput
import java.util.LinkedList

const val CYCLE_DAYS = 7
const val INITIAL = 8

data class Fish(val initial: Int, val startDay: Int) {
    fun simulate(days: Int): List<Fish> =
        ((startDay + initial + 1)..days step CYCLE_DAYS).map { Fish(INITIAL, it) }
}

fun parse(input: String): List<Fish> = input.split(",").map { it.toInt() }.map { Fish(it, 0) }

fun part1(fish: List<Fish>, days: Int): Int {
    val fishPool = LinkedList(fish)
    var fishCount = 0

    while (!fishPool.isEmpty()) {
        val f = fishPool.remove()
        fishCount++
        fishPool.addAll(f.simulate(days))
    }

    return fishCount
}

fun main() {
    val input = readInput("day06/input").first()
    val fish = parse(input)

    println(part1(fish, 80))
}
