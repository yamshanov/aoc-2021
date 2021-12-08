package day08

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day08KtTest {

    private val input = listOf(
        "    be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb |             fdgacbe cefdb cefbgd gcbe",
        "        edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec |    fcgedb cgb dgebacf gc",
        "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef |    cg cg fdcagb cbg",
        "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega |    efabcd cedba gadfec cb",
        "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga |    gecf egdcabf bgf bfgea",
        "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf |    gebdcfa ecba ca fadegcb",
        "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf |    cefg dcbef fcge gbcadfe",
        "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd |    ed bcgafe cdgba cbgef",
        "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg |    gbdfcae bgc cg cgb",
        "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc |    fgae cfgab fg bagce",
    )
    private val line = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"

    @Test
    fun parse() {
        val entry = parse(line)
        assertEquals(
            listOf("acedgfb", "cdfbe", "gcdfa", "fbcad", "dab", "cefabd", "cdfgeb", "eafb", "cagedb", "ab"),
            entry.patterns
        )
        assertEquals(
            listOf(
                setOf('c', 'd', 'f', 'e', 'b'),
                setOf('f', 'c', 'a', 'd', 'b'),
                setOf('c', 'd', 'f', 'e', 'b'),
                setOf('c', 'd', 'b', 'a', 'f')
            ), entry.outputSegments
        )

        val entries = input.map(::parse)
        assertEquals(10, entries.size)
    }

    @Test
    fun deduceDigits() {
        val patterns = listOf("acedgfb", "cdfbe", "gcdfa", "fbcad", "dab", "cefabd", "cdfgeb", "eafb", "cagedb", "ab")
        val result = deduceDigits(patterns)

        assertEquals(setOf('a', 'c', 'e', 'd', 'g', 'f', 'b'), result[8])
        assertEquals(setOf('c', 'd', 'f', 'b', 'e'), result[5])
        assertEquals(setOf('g', 'c', 'd', 'f', 'a'), result[2])
        assertEquals(setOf('f', 'b', 'c', 'a', 'd'), result[3])
        assertEquals(setOf('d', 'a', 'b'), result[7])
        assertEquals(setOf('c', 'e', 'f', 'a', 'b', 'd'), result[9])
        assertEquals(setOf('c', 'd', 'f', 'g', 'e', 'b'), result[6])
        assertEquals(setOf('e', 'a', 'f', 'b'), result[4])
        assertEquals(setOf('c', 'a', 'g', 'e', 'd', 'b'), result[0])
        assertEquals(setOf('a', 'b'), result[1])
    }

    @Test
    fun part1() {
        val entries = input.map(::parse)
        val result = part1(entries)

        assertEquals(26, result)
    }

    @Test
    fun decodeOutput() {
        val entry1 = parse(line)
        assertEquals(5353, entry1.outputNumber())

        val entry2 = parse("cfag cadgfb cf cfd cdbgf gaebdc bgfde cgbfead befacd cadbg | gcbda fcd bfged dcf")
        assertEquals(5727, entry2.outputNumber())

        val output = input.map(::parse).map { it.outputNumber() }
        assertEquals(listOf(8394, 9781, 1197, 9361, 4873, 8418, 4548, 1625, 8717, 4315), output)
        assertEquals(61229, output.sum())
    }
}
