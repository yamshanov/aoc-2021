package day02

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

internal class Day02Test {

    private val input = listOf(
        "forward 5",
        "down 5",
        "forward 8",
        "up 3",
        "down 8",
        "forward 2"
    ).map(::Command)

    @Test
    fun `Create forward command`() {
        val command = Command("forward 1")

        assertEquals(Direction.forward, command.direction)
        assertEquals(1, command.units)
    }

    @Test
    fun `Create down command`() {
        val command = Command("down 1")

        assertEquals(Direction.down, command.direction)
        assertEquals(1, command.units)
    }

    @Test
    fun `Create up command`() {
        val command = Command("up 1")

        assertEquals(Direction.up, command.direction)
        assertEquals(1, command.units)
    }

    @Test
    fun `Create command from a line with additional spaces`() {
        val command = Command("   forward\t   777    ")

        assertEquals(Direction.forward, command.direction)
        assertEquals(777, command.units)
    }

    @Test
    fun `Create illegal command`() {
        assertThrows<IllegalArgumentException> {
            Command("backward 1")
        }
    }

    @Test
    fun `Create command with non-numeric units`() {
        assertThrows<IllegalArgumentException> {
            Command("forward one")
        }
    }

    @Test
    fun `Create command without units`() {
        assertThrows<IndexOutOfBoundsException> {
            Command("forward")
        }
    }

    @Test
    fun `Position move forward`() {
        val cmd = Command("forward 2")
        val position = Position(5, 5).toNewPosition(cmd)

        assertEquals(7, position.x)
        assertEquals(5, position.y)
    }

    @Test
    fun `Position move down`() {
        val cmd = Command("down 2")
        val position = Position(5, 5).toNewPosition(cmd)

        assertEquals(5, position.x)
        assertEquals(7, position.y)
    }

    @Test
    fun `Position move up`() {
        val cmd = Command("up 2")
        val position = Position(5, 5).toNewPosition(cmd)

        assertEquals(5, position.x)
        assertEquals(3, position.y)
    }

    @Test
    fun `Aimed Position move forward`() {
        val cmd = Command("forward 2")
        val position = AimedPosition(10, 10, 3).toNewPosition(cmd)

        assertEquals(12, position.x)
        assertEquals(16, position.y)
        assertEquals(3, position.aim)
    }

    @Test
    fun `Aimed Position move down`() {
        val cmd = Command("down 2")
        val position = AimedPosition(10, 10, 3).toNewPosition(cmd)

        assertEquals(10, position.x)
        assertEquals(10, position.y)
        assertEquals(5, position.aim)
    }

    @Test
    fun `Aimed Position move up`() {
        val cmd = Command("up 2")
        val position = AimedPosition(10, 10, 3).toNewPosition(cmd)

        assertEquals(10, position.x)
        assertEquals(10, position.y)
        assertEquals(1, position.aim)
    }

    @Test
    fun part1() {
        val result = part1(input)

        assertEquals(150, result)
    }

    @Test
    fun part2() {
        val result = part2(input)

        assertEquals(900, result)
    }
}
