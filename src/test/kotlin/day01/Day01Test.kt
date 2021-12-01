package day01

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day01Test {

    private val input = listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)

    @Test
    fun part1() {
        val result = part1(input)

        assertEquals(7, result)
    }

    @Test
    fun part2() {
        val result = part2(input)

        assertEquals(5, result)
    }
}
