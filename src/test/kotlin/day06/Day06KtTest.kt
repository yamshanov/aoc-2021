package day06

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day06KtTest {

    private val input = listOf(3, 4, 3, 1, 2)

    @Test
    fun `countFish 18 days`() {
        val result = countFish(input, 18)

        assertEquals(26, result)
    }

    @Test
    fun `countFish 80 days`() {
        val result = countFish(input, 80)

        assertEquals(5934, result)
    }

    @Test
    fun `countFish 256 days`() {
        val result = countFish(input, 256)

        assertEquals(26_984_457_539, result)
    }
}
