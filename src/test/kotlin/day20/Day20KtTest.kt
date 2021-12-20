package day20

import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

internal class Day20KtTest {

    private val enhancement = toIntArray(
        "..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#"
    )
    private val pixels = toPixels(
        listOf(
            "#..#.",
            "#....",
            "##..#",
            "..#..",
            "..###"
        )
    )

    @Test
    fun enhance() {
        val image = Image(pixels, enhancement)

        val enhanced1 = image.enhance()
        assertContentEquals(intArrayOf(0, 1, 1, 0, 1, 1, 0), enhanced1.pixels[0])
        assertContentEquals(intArrayOf(1, 0, 0, 1, 0, 1, 0), enhanced1.pixels[1])
        assertContentEquals(intArrayOf(1, 1, 0, 1, 0, 0, 1), enhanced1.pixels[2])
        assertContentEquals(intArrayOf(1, 1, 1, 1, 0, 0, 1), enhanced1.pixels[3])
        assertContentEquals(intArrayOf(0, 1, 0, 0, 1, 1, 0), enhanced1.pixels[4])
        assertContentEquals(intArrayOf(0, 0, 1, 1, 0, 0, 1), enhanced1.pixels[5])
        assertContentEquals(intArrayOf(0, 0, 0, 1, 0, 1, 0), enhanced1.pixels[6])

        val enhanced2 = enhanced1.enhance()
        assertContentEquals(intArrayOf(0, 0, 0, 0, 0, 0, 0, 1, 0), enhanced2.pixels[0])
        assertContentEquals(intArrayOf(0, 1, 0, 0, 1, 0, 1, 0, 0), enhanced2.pixels[1])
        assertContentEquals(intArrayOf(1, 0, 1, 0, 0, 0, 1, 1, 1), enhanced2.pixels[2])
        assertContentEquals(intArrayOf(1, 0, 0, 0, 1, 1, 0, 1, 0), enhanced2.pixels[3])
        assertContentEquals(intArrayOf(1, 0, 0, 0, 0, 0, 1, 0, 1), enhanced2.pixels[4])
        assertContentEquals(intArrayOf(0, 1, 0, 1, 1, 1, 1, 1, 0), enhanced2.pixels[5])
        assertContentEquals(intArrayOf(0, 0, 1, 0, 1, 1, 1, 1, 1), enhanced2.pixels[6])
        assertContentEquals(intArrayOf(0, 0, 0, 1, 1, 0, 1, 1, 0), enhanced2.pixels[7])
        assertContentEquals(intArrayOf(0, 0, 0, 0, 1, 1, 1, 0, 0), enhanced2.pixels[8])
    }

    @Test
    fun part1() {
        val image = Image(pixels, enhancement)
        val result = image.enhance(2).litPixels()
        assertEquals(35, result)
    }

    @Test
    fun part2() {
        val image = Image(pixels, enhancement)
        val result = image.enhance(50).litPixels()
        assertEquals(3351, result)
    }
}
