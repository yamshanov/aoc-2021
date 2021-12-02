package day02

import readInput
import spacesRegex

enum class Direction { forward, down, up }

class Command(line: String) {
    val direction: Direction
    val units: Int

    init {
        val (d, u) = line.trim().split(spacesRegex)
        direction = Direction.valueOf(d)
        units = u.toInt()
    }
}

data class Position(val x: Int, val y: Int) {
    fun toNewPosition(cmd: Command): Position = when (cmd.direction) {
        Direction.forward -> Position(x + cmd.units, y)
        Direction.down -> Position(x, y + cmd.units)
        Direction.up -> Position(x, y - cmd.units)
    }
}

data class AimedPosition(val x: Int, val y: Int, val aim: Int) {
    fun toNewPosition(cmd: Command): AimedPosition = when (cmd.direction) {
        Direction.forward -> AimedPosition(x + cmd.units, y + aim * cmd.units, aim)
        Direction.down -> AimedPosition(x, y, aim + cmd.units)
        Direction.up -> AimedPosition(x, y, aim - cmd.units)
    }
}

fun main() {
    val input = readInput("day02/input").map(::Command)
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<Command>): Int {
    val p = input.fold(Position(0, 0)) { p, c -> p.toNewPosition(c) }
    return p.x * p.y
}

fun part2(input: List<Command>): Int {
    val p = input.fold(AimedPosition(0, 0, 0)) { p, c -> p.toNewPosition(c) }
    return p.x * p.y
}
