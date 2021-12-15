package day14

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day14KtTest {

    private val template = listOf('N', 'N', 'C', 'B')
    private val rules = mapOf(
        ('C' to 'H') to 'B',
        ('H' to 'H') to 'N',
        ('C' to 'B') to 'H',
        ('N' to 'H') to 'C',
        ('H' to 'B') to 'C',
        ('H' to 'C') to 'B',
        ('H' to 'N') to 'C',
        ('N' to 'N') to 'C',
        ('B' to 'H') to 'H',
        ('N' to 'C') to 'B',
        ('N' to 'B') to 'B',
        ('B' to 'N') to 'B',
        ('B' to 'B') to 'N',
        ('B' to 'C') to 'B',
        ('C' to 'C') to 'N',
        ('C' to 'N') to 'C',
    )

    @Test
    fun grow2Elements() {
        val polymer = Polymer(template, rules)
        val counts = polymer.grow2Elements('N', 'N', 3)

        assertEquals(3, counts['N'])
        assertEquals(3, counts['C'])
        assertEquals(3, counts['B'])
    }

    @Test
    fun grow1Step() {
        val polymer = Polymer(template, rules)
        val counts = polymer.grow(1)

        assertEquals(2, counts['N'])
        assertEquals(2, counts['C'])
        assertEquals(2, counts['B'])
        assertEquals(1, counts['H'])
    }

    @Test
    fun grow2Steps() {
        val polymer = Polymer(template, rules)
        val counts = polymer.grow(2)

        assertEquals(2, counts['N'])
        assertEquals(4, counts['C'])
        assertEquals(6, counts['B'])
        assertEquals(1, counts['H'])
    }

    @Test
    fun grow3Steps() {
        val polymer = Polymer(template, rules)
        val counts = polymer.grow(3)

        assertEquals(5, counts['N'])
        assertEquals(5, counts['C'])
        assertEquals(11, counts['B'])
        assertEquals(4, counts['H'])
    }

    @Test
    fun part1() {
        val polymer = Polymer(template, rules)
        val result = part1(polymer)

        assertEquals(1588, result)
    }

    @Test
    fun part2() {
        val polymer = Polymer(template, rules)
        val result = part2(polymer)

        assertEquals(2_188_189_693_529, result)
    }
}
