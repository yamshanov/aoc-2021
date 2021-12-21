package day21

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day21KtTest {

    private val player1 = Player(4, true)
    private val player2 = Player(8, false)

    @Test
    fun rollAndMove() {
        val die = DeterministicDie()
        var player1 = player1
        var player2 = player2

        repeat(3) { player1 = player1.roll(die.first()) }
        assertEquals(10, player1.score)

        repeat(3) { player2 = player2.roll(die.first()) }
        assertEquals(3, player2.score)

        repeat(3) { player1 = player1.roll(die.first()) }
        assertEquals(14, player1.score)

        repeat(3) { player2 = player2.roll(die.first()) }
        assertEquals(9, player2.score)

        repeat(3) { player1 = player1.roll(die.first()) }
        assertEquals(20, player1.score)

        repeat(3) { player2 = player2.roll(die.first()) }
        assertEquals(16, player2.score)

        repeat(3) { player1 = player1.roll(die.first()) }
        assertEquals(26, player1.score)

        repeat(3) { player2 = player2.roll(die.first()) }
        assertEquals(22, player2.score)
    }

    @Test
    fun part1() {
        val game = Game1(player1, player2)

        val result = game.play()
        assertEquals(1000, game.player1.score)
        assertEquals(745, game.player2.score)
        assertEquals(993, game.die.rolledTimes)
        assertEquals(739785, result)
    }

    @Test
    fun part2() {
        val game = Game2(player1, player2)

        val result = game.play()
        assertEquals(444356092776315, result)
    }
}
