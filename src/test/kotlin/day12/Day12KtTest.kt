package day12

import org.junit.jupiter.api.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class Day12KtTest {

    private val input1 = listOf(
        "start-A",
        "start-b",
        "A-c    ",
        "A-b    ",
        "b-d    ",
        "A-end  ",
        "b-end  "
    )
    private val input2 = listOf(
        "dc-end",
        "HN-start",
        "start-kj",
        "dc-start",
        "dc-HN",
        "LN-dc",
        "HN-end",
        "kj-sa",
        "kj-HN",
        "kj-dc",
    )
    private val input3 = listOf(
        "fs-end",
        "he-DX",
        "fs-he",
        "start-DX",
        "pj-DX",
        "end-zg",
        "zg-sl",
        "zg-pj",
        "pj-he",
        "RW-he",
        "fs-DX",
        "pj-RW",
        "zg-RW",
        "start-pj",
        "he-WI",
        "zg-he",
        "pj-fs",
        "start-RW",
    )

    @Test
    fun parse() {
        val result = parse(input1)

        assertEquals(setOf("A", "b"), result["start"]!!.toSet())
        assertEquals(setOf("start", "c", "b", "end"), result["A"]!!.toSet())
        assertEquals(setOf("start", "A", "d", "end"), result["b"]!!.toSet())
        assertEquals(setOf("A"), result["c"]!!.toSet())
        assertEquals(setOf("b"), result["d"]!!.toSet())
        assertEquals(setOf("A", "b"), result["end"]!!.toSet())
    }

    @Test
    fun part1Paths1() {
        val graph = parse(input1)
        val paths = paths(graph, Path1())

        assertEquals(10, paths.size)
        assertContains(paths, listOf("start", "A", "b", "A", "c", "A", "end"))
        assertContains(paths, listOf("start", "A", "b", "A", "end"))
        assertContains(paths, listOf("start", "A", "b", "end"))
        assertContains(paths, listOf("start", "A", "c", "A", "b", "A", "end"))
        assertContains(paths, listOf("start", "A", "c", "A", "b", "end"))
        assertContains(paths, listOf("start", "A", "c", "A", "end"))
        assertContains(paths, listOf("start", "A", "end"))
        assertContains(paths, listOf("start", "b", "A", "c", "A", "end"))
        assertContains(paths, listOf("start", "b", "A", "end"))
        assertContains(paths, listOf("start", "b", "end"))
    }

    @Test
    fun part1Input2() {
        val graph = parse(input2)
        val paths = part1(graph)

        assertEquals(19, paths)
    }

    @Test
    fun part1Input3() {
        val graph = parse(input3)
        val paths = part1(graph)

        assertEquals(226, paths)
    }

    @Test
    fun part2Input1() {
        val graph = parse(input1)
        val paths = part2(graph)

        assertEquals(36, paths)
    }

    @Test
    fun part2Input2() {
        val graph = parse(input2)
        val paths = part2(graph)

        assertEquals(103, paths)
    }

    @Test
    fun part2Input3() {
        val graph = parse(input3)
        val paths = part2(graph)

        assertEquals(3509, paths)
    }
}
