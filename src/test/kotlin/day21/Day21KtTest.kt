package day21

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day21KtTest {

    @Test
    fun rollAndMove() {
        val die = DeterministicDie()
        val player1 = Player(4)
        val player2 = Player(8)

        player1.rollAndMove(die)
        assertEquals(10, player1.score)

        player2.rollAndMove(die)
        assertEquals(3, player2.score)

        player1.rollAndMove(die)
        assertEquals(14, player1.score)

        player2.rollAndMove(die)
        assertEquals(9, player2.score)

        player1.rollAndMove(die)
        assertEquals(20, player1.score)

        player2.rollAndMove(die)
        assertEquals(16, player2.score)

        player1.rollAndMove(die)
        assertEquals(26, player1.score)

        player2.rollAndMove(die)
        assertEquals(22, player2.score)
    }

    @Test
    fun part1() {
        val die = DeterministicDie()
        val player1 = Player(4)
        val player2 = Player(8)

        val result = part1(player1, player2, die)
        assertEquals(1000, player1.score)
        assertEquals(745, player2.score)
        assertEquals(993, die.rolledTimes)
        assertEquals(739785, result)
    }
}
