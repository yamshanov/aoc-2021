package day07

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day07KtTest {

    private val input = listOf(16, 1, 2, 0, 4, 2, 7, 1, 2, 14)

    @Test
    fun totalFuel() {
        val to2 = totalFuel(input, 2)
        assertEquals(37, to2)

        val to1 = totalFuel(input, 1)
        assertEquals(41, to1)

        val to3 = totalFuel(input, 3)
        assertEquals(39, to3)

        val to10 = totalFuel(input, 10)
        assertEquals(71, to10)
    }

    @Test
    fun totalFuel2() {
        val to2 = totalFuel2(input, 2)
        assertEquals(206, to2)

        val to5 = totalFuel2(input, 5)
        assertEquals(168, to5)
    }

    @Test
    fun part1() {
        val result = part1(input)
        assertEquals(37, result)
    }

    @Test
    fun part2() {
        val result = part2(input)
        assertEquals(168, result)
    }
}
