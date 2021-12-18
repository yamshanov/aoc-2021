package day17

import kotlin.math.abs

data class Point(val x: Int, val y: Int)

class Target(val x1: Int, val x2: Int, val y1: Int, val y2: Int) {
    fun isHit(x: Int, y: Int): Boolean = (x in x1..x2) && (y in y1..y2)
    fun isMissed(x: Int, y: Int): Boolean = x > x2 || y < y1
}

data class Trajectory(val vx0: Int, val vy0: Int, val points: List<Point>, val hitsTarget: Boolean) {
    val height by lazy { points.maxOf { it.y } }
}

class Launcher(private val target: Target) {

    fun minXVelocity(): Int {
        var v = 1
        var d = 0
        while (d < target.x1) {
            d += v
            v += 1
        }
        return v - 1
    }

    fun maxXVelocity(): Int = target.x2
    fun minYVelocity(): Int = target.y1
    fun maxYVelocity(): Int = abs(target.y1) - 1

    fun calculateTrajectory(vx0: Int, vy0: Int): Trajectory {
        val points = buildList {
            var vx = vx0
            var vy = vy0
            var point = Point(0, 0)
            add(point)

            while (!target.isMissed(point.x, point.y) && !target.isHit(point.x, point.y)) {
                point = Point(point.x + vx, point.y + vy)
                add(point)
                vx = if (vx > 0) vx - 1 else 0
                vy -= 1
            }
        }

        val last = points.last()
        return Trajectory(vx0, vy0, points, target.isHit(last.x, last.y))
    }

    fun allSuccessfulTrajectories(): List<Trajectory> = buildList {
        for (xv in minXVelocity()..maxXVelocity()) {
            for (yv in minYVelocity()..maxYVelocity()) {
                val t = calculateTrajectory(xv, yv)
                if (t.hitsTarget) add(t)
            }
        }
    }
}

fun main() {
    // Doesn't make sense to parse the input
    val target = Target(253, 280, -73, -46)
    val launcher = Launcher(target)

    println(part1(launcher))
    println(part2(launcher))
}

fun part1(launcher: Launcher): Int =
    launcher.calculateTrajectory(launcher.minXVelocity(), launcher.maxYVelocity()).height

fun part2(launcher: Launcher): Int = launcher.allSuccessfulTrajectories().size
