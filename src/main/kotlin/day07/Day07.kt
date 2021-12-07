package day07

import readInput
import kotlin.math.abs
import kotlin.math.min

fun totalFuel(positions: List<Int>, destination: Int): Int = positions.sumOf { abs(it - destination) }

fun totalFuel2(positions: List<Int>, destination: Int): Int = positions.sumOf { fuel2(abs(it - destination)) }

fun fuel2(steps: Int): Int = steps * (steps + 1) / 2

fun findMin(positions: List<Int>, fuelFn: (List<Int>, Int) -> Int): Int {
    check(positions.size >= 2)
    var pLeft = positions.minOrNull()!!
    var pRight = positions.maxOrNull()!!
    var fLeft = fuelFn(positions, pLeft)
    var fRight = fuelFn(positions, pRight)

    while (pRight - pLeft > 1) {
        val p = pLeft + (pRight - pLeft) / 2
        val f = fuelFn(positions, p)
        if (fLeft < fRight) {
            pRight = p
            fRight = f
        } else {
            pLeft = p
            fLeft = f
        }
    }

    return min(fLeft, fRight)
}

fun part1(input: List<Int>): Int {
    return findMin(input, ::totalFuel)
}

fun part2(input: List<Int>): Int {
    return findMin(input, ::totalFuel2)
}

fun main() {
    val input = readInput("day07/input").first().split(",").map { it.toInt() }

    println(part1(input))
    println(part2(input))
}
