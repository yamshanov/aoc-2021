package day03

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day03KtTest {

    private val input = listOf(
        "00100",
        "11110",
        "10110",
        "10111",
        "10101",
        "01111",
        "00111",
        "11100",
        "10000",
        "11001",
        "00010",
        "01010",
    ).map { parse(it) }

    private val mostCommon = { zeroes: Int, ones: Int -> zeroes > ones }
    private val leastCommon = { zeroes: Int, ones: Int -> zeroes <= ones }

    @Test
    fun parse() {
        val result = parse("0101")

        assertEquals(4, result.size)
        assertEquals(0u, result[0])
        assertEquals(1u, result[1])
        assertEquals(0u, result[2])
        assertEquals(1u, result[3])
    }

    @Test
    fun part1() {
        val result = part1(input)

        assertEquals(198u, result)
    }

    @Test
    fun `filterByBitCriteria select for oxygen 1`() {
        val result = filterByBitCriteria(input, 0, mostCommon)

        assertEquals(7, result.size)
        assertEquals(listOf(1u, 1u, 1u, 1u, 0u), result[0])
        assertEquals(listOf(1u, 0u, 1u, 1u, 0u), result[1])
        assertEquals(listOf(1u, 0u, 1u, 1u, 1u), result[2])
        assertEquals(listOf(1u, 0u, 1u, 0u, 1u), result[3])
        assertEquals(listOf(1u, 1u, 1u, 0u, 0u), result[4])
        assertEquals(listOf(1u, 0u, 0u, 0u, 0u), result[5])
        assertEquals(listOf(1u, 1u, 0u, 0u, 1u), result[6])
    }

    @Test
    fun `filterByBitCriteria select for oxygen 2`() {
        val filtered1 = filterByBitCriteria(input, 0, mostCommon)
        val result = filterByBitCriteria(filtered1, 1, mostCommon)

        assertEquals(4, result.size)
        assertEquals(listOf(1u, 0u, 1u, 1u, 0u), result[0])
        assertEquals(listOf(1u, 0u, 1u, 1u, 1u), result[1])
        assertEquals(listOf(1u, 0u, 1u, 0u, 1u), result[2])
        assertEquals(listOf(1u, 0u, 0u, 0u, 0u), result[3])
    }

    @Test
    fun `filterByBitCriteria select for oxygen 5`() {
        val filtered1 = filterByBitCriteria(input, 0, mostCommon)
        val filtered2 = filterByBitCriteria(filtered1, 1, mostCommon)
        val filtered3 = filterByBitCriteria(filtered2, 2, mostCommon)
        val filtered4 = filterByBitCriteria(filtered3, 3, mostCommon)
        val result = filterByBitCriteria(filtered4, 4, mostCommon)

        assertEquals(1, result.size)
        assertEquals(listOf(1u, 0u, 1u, 1u, 1u), result[0])
    }

    @Test
    fun `filterByBitCriteria select for CO2 1`() {
        val result = filterByBitCriteria(input, 0, leastCommon)

        assertEquals(5, result.size)
        assertEquals(listOf(0u, 0u, 1u, 0u, 0u), result[0])
        assertEquals(listOf(0u, 1u, 1u, 1u, 1u), result[1])
        assertEquals(listOf(0u, 0u, 1u, 1u, 1u), result[2])
        assertEquals(listOf(0u, 0u, 0u, 1u, 0u), result[3])
        assertEquals(listOf(0u, 1u, 0u, 1u, 0u), result[4])
    }

    @Test
    fun `filterByBitCriteria select for CO2 2`() {
        val filtered1 = filterByBitCriteria(input, 0, leastCommon)
        val result = filterByBitCriteria(filtered1, 1, leastCommon)

        assertEquals(2, result.size)
        assertEquals(listOf(0u, 1u, 1u, 1u, 1u), result[0])
        assertEquals(listOf(0u, 1u, 0u, 1u, 0u), result[1])
    }

    @Test
    fun `filterByBitCriteria select for CO2 3`() {
        val filtered1 = filterByBitCriteria(input, 0, leastCommon)
        val filtered2 = filterByBitCriteria(filtered1, 1, leastCommon)
        val result = filterByBitCriteria(filtered2, 2, leastCommon)

        assertEquals(1, result.size)
        assertEquals(listOf(0u, 1u, 0u, 1u, 0u), result[0])
    }

    @Test
    fun `Oxygen rating`() {
        val result = rating(input, mostCommon)

        assertEquals(23u, result)
    }

    @Test
    fun `CO2 rating`() {
        val result = rating(input, leastCommon)

        assertEquals(10u, result)
    }
}
