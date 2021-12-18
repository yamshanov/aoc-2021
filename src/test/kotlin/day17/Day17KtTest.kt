package day17

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class Day17KtTest {

    private val target = Target(20, 30, -10, -5)
    private val launcher = Launcher(target)

    @Test
    fun calculateTrajectory() {
        val t72 = launcher.calculateTrajectory(7, 2)
        assertTrue(t72.hitsTarget)
        assertEquals(3, t72.height)
        println(t72)

        val t63 = launcher.calculateTrajectory(6, 3)
        assertTrue(t63.hitsTarget)
        assertEquals(6, t63.height)

        val t90 = launcher.calculateTrajectory(9, 0)
        assertTrue(t90.hitsTarget)
        assertEquals(0, t90.height)

        val t17m4 = launcher.calculateTrajectory(17, -4)
        assertFalse(t17m4.hitsTarget)

        val t69 = launcher.calculateTrajectory(6, 9)
        assertTrue(t69.hitsTarget)
        assertEquals(45, t69.height)
    }

    @Test
    fun part1() {
        val result = part1(launcher)
        assertEquals(45, result)
    }

    @Test
    fun part2() {
        val result = part2(launcher)
        assertEquals(112, result)
    }
}
