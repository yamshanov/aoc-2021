package day09

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day09KtTest {

    private val map = listOf(
        listOf(2, 1, 9, 9, 9, 4, 3, 2, 1, 0),
        listOf(3, 9, 8, 7, 8, 9, 4, 9, 2, 1),
        listOf(9, 8, 5, 6, 7, 8, 9, 8, 9, 2),
        listOf(8, 7, 6, 7, 8, 9, 6, 7, 8, 9),
        listOf(9, 8, 9, 9, 9, 6, 5, 6, 7, 8)
    )

    @Test
    fun parse() {
        val lines = listOf("12", "34")
        val result = parse(lines)

        assertEquals(1, result[0][0])
        assertEquals(2, result[0][1])
        assertEquals(3, result[1][0])
        assertEquals(4, result[1][1])
    }

    @Test
    fun part1() {
        val result = part1(HeightMap(map))
        assertEquals(15, result)
    }

    @Test
    fun basinOf() {
        val hm = HeightMap(map)

        val basin1 = hm.basinOf(0 to 1)
        assertEquals(setOf(0 to 1, 0 to 0, 1 to 0), basin1)

        val basin2 = hm.basinOf(0 to 9)
        assertEquals(9, basin2.size)

        val basin3 = hm.basinOf(2 to 2)
        assertEquals(14, basin3.size)

        val basin4 = hm.basinOf(4 to 6)
        assertEquals(9, basin4.size)
    }

    @Test
    fun part2() {
        val hm = HeightMap(map)

        val result = part2(hm)
        assertEquals(1134, result)
    }
}
