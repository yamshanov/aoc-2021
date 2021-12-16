package day15

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day15KtTest {

    private val input = listOf(
        "1163751742",
        "1381373672",
        "2136511328",
        "3694931569",
        "7463417111",
        "1319128137",
        "1359912421",
        "3125421639",
        "1293138521",
        "2311944581",
    )

    @Test
    fun part1() {
        val risks = parse(input)
        val cave = Cave(risks)
        val path = cave.findLowestRiskPath()

        assertEquals(risks.size - 1, path.i)
        assertEquals(risks[0].size - 1, path.j)
        assertEquals(40, path.risk)
    }

    @Test
    fun part2() {
        val risks = parse(input)
        val scaled = scale(risks)
        val cave = Cave(scaled)
        val path = cave.findLowestRiskPath()

        assertEquals(scaled.size - 1, path.i)
        assertEquals(scaled[0].size - 1, path.j)
        assertEquals(315, path.risk)
    }

    @Test
    fun goLeft() {
        val input = listOf(
            "111199",
            "999199",
            "911199",
            "919999",
            "911111"
        )
        val risks = parse(input)
        val cave = Cave(risks)
        val path = cave.findLowestRiskPath()

        assertEquals(risks.size - 1, path.i)
        assertEquals(risks[0].size - 1, path.j)
        assertEquals(13, path.risk)
    }

    @Test
    fun goUp() {
        val input = listOf(
            "199999",
            "191119",
            "111919",
            "999919",
            "999911"
        )
        val risks = parse(input)
        val cave = Cave(risks)
        val path = cave.findLowestRiskPath()

        assertEquals(risks.size - 1, path.i)
        assertEquals(risks[0].size - 1, path.j)
        assertEquals(11, path.risk)
    }
}
