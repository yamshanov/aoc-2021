package day06

import readInput

const val CYCLE_DAYS = 7
const val FIRST_CYCLE_DAYS = CYCLE_DAYS + 2

fun countFish(initialFishAges: List<Int>, days: Int): Long {
    val fishCount = LongArray(days) { initialFishAges.size.toLong() }
    val newFish = LongArray(days)
    for (age in initialFishAges) {
        for (d in age until days step CYCLE_DAYS) newFish[d]++
    }

    for (day in 0 until days) {
        for (i in (day + FIRST_CYCLE_DAYS) until days step CYCLE_DAYS) newFish[i] += newFish[day]
        for (i in day until days) fishCount[i] += newFish[day]
    }

    return fishCount[days - 1]
}

fun main() {
    val input = readInput("day06/input").first().split(",").map { it.toInt() }

    println(countFish(input, 80))  // part 1
    println(countFish(input, 256)) // part 2
}
