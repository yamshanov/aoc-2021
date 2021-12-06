package day06

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day06KtTest {

    private val input = "3,4,3,1,2"

    @Test
    fun parse() {
        val result = parse(input)
        assertEquals(
            listOf(
                Fish(3, 0),
                Fish(4, 0),
                Fish(3, 0),
                Fish(1, 0),
                Fish(2, 0)
            ), result
        )
    }

    @Test
    fun `simulate fish`() {
        val fish1 = Fish(3, 0)
        val result1 = fish1.simulate(18)
        assertEquals(listOf(Fish(8, 4), Fish(8, 11), Fish(8, 18)), result1)

        val fish2 = Fish(8, 9)
        val result2 = fish2.simulate(18)
        assertEquals(listOf(Fish(8, 18)), result2)

        val fish3 = Fish(8, 18)
        val result3 = fish3.simulate(18)
        assertEquals(emptyList(), result3)
    }

    @Test
    fun part1() {
        val fish = parse(input)

        val after18days = part1(fish, 18)
        assertEquals(26, after18days)

        val after80days = part1(fish, 80)
        assertEquals(5934, after80days)
    }
}
