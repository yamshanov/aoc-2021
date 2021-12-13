package day13

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day13KtTest {

    private val input = listOf(
        "6,10",
        "0,14",
        "9,10",
        "0,3",
        "10,4",
        "4,11",
        "6,0",
        "6,12",
        "4,1",
        "0,13",
        "10,12",
        "3,4",
        "3,0",
        "8,4",
        "1,10",
        "2,14",
        "8,10",
        "9,0",
        "",
        "fold along y=7",
        "fold along x=5"
    )

    @Test
    fun parseDots() {
        val dots = input.takeWhile { it.isNotBlank() }.map { parseDot(it) }

        assertEquals(18, dots.size)
        assertEquals(Dot(6, 10), dots[0])
        assertEquals(Dot(9, 0), dots[17])
    }

    @Test
    fun parseInstructions() {
        val instructions = input.filter { it.startsWith("fold") }.map { parseInstruction(it) }

        assertEquals(2, instructions.size)
        assertEquals(FoldInstruction("y", 7), instructions[0])
        assertEquals(FoldInstruction("x", 5), instructions[1])
    }

    @Test
    fun fold1() {
        val dots = input.takeWhile { it.isNotBlank() }.map { parseDot(it) }
        val paper = Paper(dots)
        val instruction = FoldInstruction("y", 7)

        paper.fold(instruction)

        assertEquals(17, paper.countDots())
    }

    @Test
    fun part2() {
        val dots = input.takeWhile { it.isNotBlank() }.map { parseDot(it) }
        val paper = Paper(dots)

        paper.fold(FoldInstruction("y", 7))
        paper.fold(FoldInstruction("x", 5))
        val image = paper.image()

        assertEquals("#####", image[0])
        assertEquals("#...#", image[1])
        assertEquals("#...#", image[2])
        assertEquals("#...#", image[3])
        assertEquals("#####", image[4])
    }
}
