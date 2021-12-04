package day04

import readInput
import spacesRegex

const val BOARD_SIZE = 5

class Board(numbers: List<List<Int>>) {
    private val board = HashMap<Int, Pair<Int, Int>>()
    private val rowMarkCount = IntArray(5)
    private val colMarkCount = IntArray(5)

    init {
        check(numbers.size == BOARD_SIZE)
        for ((i, row) in numbers.withIndex()) {
            check(row.size == BOARD_SIZE)
            for ((j, n) in row.withIndex()) {
                board[n] = j to i
            }
        }
    }

    fun mark(number: Int): Boolean = board[number]?.let { (i, j) ->
        colMarkCount[i]++
        rowMarkCount[j]++
        board.remove(number)

        colMarkCount[i] == BOARD_SIZE || rowMarkCount[j] == BOARD_SIZE
    } ?: false

    fun sumOfUnmarked(): Int = board.keys.sum()
}

fun parse(lines: List<String>): Pair<List<Int>, List<Board>> {
    val numbers = lines[0].split(",").map { it.toInt() }
    val boards = lines.subList(1, lines.size)
        .filter { it.isNotBlank() }
        .windowed(BOARD_SIZE, BOARD_SIZE)
        .map(::parseBoard)
        .map(::Board)

    return numbers to boards
}

fun parseBoard(lines: List<String>): List<List<Int>> = lines.map { line ->
    line.trim().split(spacesRegex).map { it.toInt() }
}

fun markAllBoards(number: Int, boards: Collection<Board>): Set<Board> {
    val winners = mutableSetOf<Board>()
    for (b in boards) {
        val win = b.mark(number)
        if (win) winners += b
    }

    return winners
}

fun part1(numbers: List<Int>, boards: List<Board>): Int {
    for (n in numbers) {
        val winners = markAllBoards(n, boards)
        if (winners.isNotEmpty()) return winners.first().sumOfUnmarked() * n
    }

    return 0
}

fun part2(numbers: List<Int>, boards: List<Board>): Int {
    val boards = boards.toMutableSet()
    var lastWinNumber = 0
    var lastWinner: Board? = null
    for (n in numbers) {
        val winners = markAllBoards(n, boards)
        if (winners.isNotEmpty()) {
            lastWinner = winners.first()
            lastWinNumber = n
            boards.removeAll(winners)
        }
    }

    return lastWinNumber * (lastWinner?.sumOfUnmarked() ?: 0)
}

fun main() {
    val input = readInput("day04/input")
    val (numbers, boards) = parse(input)

    println(part1(numbers, boards))
    println(part2(numbers, boards))
}
