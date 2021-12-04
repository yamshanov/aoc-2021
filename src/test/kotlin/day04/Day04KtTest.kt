package day04

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class Day04KtTest {

    private val input = """
        7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

        22 13 17 11  0
         8  2 23  4 24
        21  9 14 16  7
         6 10  3 18  5
         1 12 20 15 19
        
         3 15  0  2 22
         9 18 13 17  5
        19  8  7 25 23
        20 11 10 24  4
        14 21 16 12  6
        
        14 21 17 24  4
        10 16 15  9 19
        18  8 23 26 20
        22 11 13  6  5
         2  0 12  3  7
    """.trimIndent().split("\n")
    private val boardLines = listOf(
        "3 15  0  2 22 ",
        "9 18 13 17  5 ",
        "19  8  7 25 23",
        "20 11 10 24  4",
        "14 21 16 12  6"
    )

    @Test
    fun parseBoard() {
        val board = parseBoard(boardLines)

        assertEquals(BOARD_SIZE, board.size)
        board.forEach {
            assertEquals(BOARD_SIZE, it.size)
        }
        assertEquals(5, board[1][4])
        assertEquals(6, board[4][4])
    }

    @Test
    fun `Create Board`() {
        val board = Board(parseBoard(boardLines))

        assertEquals(324, board.sumOfUnmarked())
    }

    @Test
    fun parse() {
        val (numbers, boards) = parse(input)

        assertEquals(27, numbers.size)
        assertEquals(3, boards.size)
    }

    @Test
    fun `Board mark number`() {
        val board = Board(parseBoard(boardLines))
        val number = 10
        val sumBefore = board.sumOfUnmarked()
        val win = board.mark(number)
        val sumAfter = board.sumOfUnmarked()

        assertFalse(win)
        assertEquals(sumBefore - number, sumAfter)
    }

    @Test
    fun `Board mark, win horizontal`() {
        val board = Board(parseBoard(boardLines))
        board.mark(10)
        board.mark(4)
        board.mark(11)
        board.mark(20)
        val win = board.mark(24)

        assertTrue(win)
    }

    @Test
    fun `Board mark, win vertical`() {
        val board = Board(parseBoard(boardLines))
        board.mark(11)
        board.mark(21)
        board.mark(8)
        board.mark(15)
        val win = board.mark(18)

        assertTrue(win)
    }

    @Test
    fun part1() {
        val (numbers, boards) = parse(input)
        val result = part1(numbers, boards)

        assertEquals(4512, result)
    }

    @Test
    fun part2() {
        val (numbers, boards) = parse(input)
        val result = part2(numbers, boards)

        assertEquals(1924, result)
    }
}
