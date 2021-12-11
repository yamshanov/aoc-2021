package day11

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day11KtTest {

    private val testData = arrayOf(
        intArrayOf(5, 4, 8, 3, 1, 4, 3, 2, 2, 3),
        intArrayOf(2, 7, 4, 5, 8, 5, 4, 7, 1, 1),
        intArrayOf(5, 2, 6, 4, 5, 5, 6, 1, 7, 3),
        intArrayOf(6, 1, 4, 1, 3, 3, 6, 1, 4, 6),
        intArrayOf(6, 3, 5, 7, 3, 8, 5, 4, 7, 8),
        intArrayOf(4, 1, 6, 7, 5, 2, 4, 6, 4, 5),
        intArrayOf(2, 1, 7, 6, 8, 4, 1, 7, 2, 1),
        intArrayOf(6, 8, 8, 2, 8, 8, 1, 1, 3, 4),
        intArrayOf(4, 8, 4, 6, 8, 4, 8, 5, 5, 4),
        intArrayOf(5, 2, 8, 3, 7, 5, 1, 5, 2, 6)
    )

    @Test
    fun parse() {
        val input = listOf("01", "23")
        val result = parse(input)

        assertEquals(0, result[0][0])
        assertEquals(1, result[0][1])
        assertEquals(2, result[1][0])
        assertEquals(3, result[1][1])
    }

    @Test
    fun smallGridStep() {
        val gValues = arrayOf(
            intArrayOf(1, 1, 1, 1, 1),
            intArrayOf(1, 9, 9, 9, 1),
            intArrayOf(1, 9, 1, 9, 1),
            intArrayOf(1, 9, 9, 9, 1),
            intArrayOf(1, 1, 1, 1, 1)
        )
        val grid = Grid(gValues)

        val result = grid.step()

        assertEquals(9, result)
        assertArrayEquals(intArrayOf(3, 4, 5, 4, 3), gValues[0])
        assertArrayEquals(intArrayOf(4, 0, 0, 0, 4), gValues[1])
        assertArrayEquals(intArrayOf(5, 0, 0, 0, 5), gValues[2])
        assertArrayEquals(intArrayOf(4, 0, 0, 0, 4), gValues[3])
        assertArrayEquals(intArrayOf(3, 4, 5, 4, 3), gValues[4])
    }

    @Test
    fun bigGridStep() {
        val grid = Grid(testData)

        val after1 = grid.step()
        assertEquals(0, after1)

        val after2 = grid.step()
        assertEquals(35, after2)
    }

    @Test
    fun part1() {
        val grid = Grid(testData)
        val result = part1(grid)

        assertEquals(1656, result)
    }

    @Test
    fun part2() {
        val grid = Grid(testData)
        val result = part2(grid)

        assertEquals(195, result)
    }
}
