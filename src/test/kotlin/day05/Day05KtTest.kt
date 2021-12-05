package day05

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day05KtTest {

    private val input = listOf(
        "0,9 -> 5,9",
        "8,0 -> 0,8",
        "9,4 -> 3,4",
        "2,2 -> 2,1",
        "7,0 -> 7,4",
        "6,4 -> 2,0",
        "0,9 -> 2,9",
        "3,4 -> 1,4",
        "0,0 -> 8,8",
        "5,5 -> 8,2"
    )

    @Test
    fun parseLine() {
        val (start, end) = parseLine("0,9 -> 5,9")

        assertEquals(0, start.first)
        assertEquals(9, start.second)
        assertEquals(5, end.first)
        assertEquals(9, start.second)
    }

    @Test
    fun lineToPoints1() {
        val horizontal = lineToPoints1(0 to 9, 2 to 9)
        assertEquals(listOf(0 to 9, 1 to 9, 2 to 9), horizontal)

        val vertical = lineToPoints1(3 to 9, 3 to 8)
        assertEquals(listOf(3 to 9, 3 to 8), vertical)

        val diagonal = lineToPoints1(9 to 9, 3 to 3)
        assertEquals(emptyList(), diagonal)

        val point = lineToPoints1(2 to 2, 2 to 2)
        assertEquals(listOf(2 to 2), point)
    }

    @Test
    fun lineToPoints2() {
        val diagonal1 = lineToPoints2(1 to 1, 3 to 3)
        assertEquals(listOf(1 to 1, 2 to 2, 3 to 3), diagonal1)

        val diagonal2 = lineToPoints2(9 to 7, 7 to 9)
        assertEquals(listOf(9 to 7, 8 to 8, 7 to 9), diagonal2)
    }

    @Test
    fun part1() {
        val result = part1(input.map(::parseLine))

        assertEquals(5, result)
    }

    @Test
    fun part2() {
        val result = part2(input.map(::parseLine))

        assertEquals(12, result)
    }
}
