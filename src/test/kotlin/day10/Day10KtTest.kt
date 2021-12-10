package day10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertNull

internal class Day10KtTest {

    private val input = listOf(
        "[({(<(())[]>[[{[]{<()<>>",
        "[(()[<>])]({[<{<<[]>>(",
        "{([(<{}[<>[]}>{[]{[(<()>",
        "(((({<>}<{<{<>}{[]{[]{}",
        "[[<[([]))<([[{}[[()]]]",
        "[{[{({}]{}}([{[{{{}}([]",
        "{<[[]]>}<{[{[{[]{()[[[]",
        "[<(<(<(<{}))><([]([]()",
        "<{([([[(<>()){}]>(<<{{",
        "<{([{{}}[<[[[<>{}]]]>[]]",
    )

    @Test
    fun checkCorrupted() {
        val (c1, _) = check(strToQueue("{([(<{}[<>[]}>{[]{[(<()>"))
        assertEquals('}', c1)

        val (c2, _) = check(strToQueue("[[<[([]))<([[{}[[()]]]"))
        assertEquals(')', c2)

        val (c3, _) = check(strToQueue("[{[{({}]{}}([{[{{{}}([]"))
        assertEquals(']', c3)

        val (c4, _) = check(strToQueue("[<(<(<(<{}))><([]([]()"))
        assertEquals(')', c4)

        val (c5, _) = check(strToQueue("<{([([[(<>()){}]>(<<{{"))
        assertEquals('>', c5)

        val (c6, _) = check(strToQueue("[({(<(())[]>[[{[]{<()<>>"))
        assertNull(c6)
    }

    @Test
    fun checkIncomplete() {
        val (_, cs1) = check(strToQueue("[({(<(())[]>[[{[]{<()<>>"))
        assertEquals(listOf('{', '{', '[', '[', '(', '{', '(', '['), cs1)

        val (_, cs2) = check(strToQueue("[(()[<>])]({[<{<<[]>>("))
        assertEquals(listOf('(', '{', '<', '[', '{', '('), cs2)

        val (_, cs3) = check(strToQueue("(((({<>}<{<{<>}{[]{[]{}"))
        assertEquals(listOf('{', '{', '<', '{', '<', '(', '(', '(', '('), cs3)

        val (_, cs4) = check(strToQueue("{<[[]]>}<{[{[{[]{()[[[]"))
        assertEquals(listOf('[', '[', '{', '{', '[', '{', '[', '{', '<'), cs4)

        val (_, cs5) = check(strToQueue("<{([{{}}[<[[[<>{}]]]>[]]"))
        assertEquals(listOf('[', '(', '{', '<'), cs5)
    }

    @Test
    fun score2() {
        val s1 = score2(listOf('}', '}', ']', ']', ')', '}', ')', ']'))
        assertEquals(288957, s1)

        val s2 = score2(listOf('}', '}', '>', '}', '>', ')', ')', ')', ')'))
        assertEquals(1480781, s2)
    }

    @Test
    fun part1score() {
        val result = part1(listOf('}', ')', ']', ')', '>'))

        assertEquals(26397, result)
    }

    @Test
    fun part1() {
        val (inpPt1, _) = processInput(input)
        val result = part1(inpPt1)

        assertEquals(26397, result)
    }

    @Test
    fun part2() {
        val (_, inpPt2) = processInput(input)
        val result = part2(inpPt2)

        assertEquals(288957, result)
    }
}
