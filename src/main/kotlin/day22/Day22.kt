package day22

import readInput
import spacesRegex

data class Cuboid(val on: Boolean, val x1: Int, val x2: Int, val y1: Int, val y2: Int, val z1: Int, val z2: Int) {

    fun isInside(other: Cuboid): Boolean =
        x1 >= other.x1 && x2 <= other.x2 && y1 >= other.y1 && y2 <= other.y2 && z1 >= other.z1 && z2 <= other.z2

    fun countOn(): Long = if (on) (x2 - x1 + 1L) * (y2 - y1 + 1L) * (z2 - z1 + 1L) else 0

    fun remove(other: Cuboid): List<Cuboid> {
        var parts = listOf(this)
        parts = parts.flatMap { it.cutX(other.x1, false) }
        parts = parts.flatMap { it.cutX(other.x2, true) }
        parts = parts.flatMap { it.cutY(other.y1, false) }
        parts = parts.flatMap { it.cutY(other.y2, true) }
        parts = parts.flatMap { it.cutZ(other.z1, false) }
        parts = parts.flatMap { it.cutZ(other.z2, true) }

        return parts.filterNot { it.isInside(other) }
    }

    private fun cutX(x: Int, includeInFirst: Boolean): List<Cuboid> = if (x !in x1..x2) {
        listOf(this)
    } else if (includeInFirst) {
        if (x == x2) listOf(this) else listOf(this.copy(x2 = x), this.copy(x1 = x + 1))
    } else {
        if (x == x1) listOf(this) else listOf(this.copy(x2 = x - 1), this.copy(x1 = x))
    }

    private fun cutY(y: Int, includeInFirst: Boolean): List<Cuboid> = if (y !in y1..y2) {
        listOf(this)
    } else if (includeInFirst) {
        if (y == y2) listOf(this) else listOf(this.copy(y2 = y), this.copy(y1 = y + 1))
    } else {
        if (y == y1) listOf(this) else listOf(this.copy(y2 = y - 1), this.copy(y1 = y))
    }

    private fun cutZ(z: Int, includeInFirst: Boolean): List<Cuboid> = if (z !in z1..z2) {
        listOf(this)
    } else if (includeInFirst) {
        if (z == z2) listOf(this) else listOf(this.copy(z2 = z), this.copy(z1 = z + 1))
    } else {
        if (z == z1) listOf(this) else listOf(this.copy(z2 = z - 1), this.copy(z1 = z))
    }
}

fun main() {
    val input = readInput("day22/input")
    val cuboids = input.map { parseCuboid(it) }

    println(part1(cuboids))
    println(countOn(cuboids))
}

fun parseCuboid(line: String): Cuboid {
    val (on, rest) = line.split(spacesRegex)
    val ranges = rest.split(",")
    val xs = ranges[0].removePrefix("x=").split("..")
    val ys = ranges[1].removePrefix("y=").split("..")
    val zs = ranges[2].removePrefix("z=").split("..")

    return Cuboid(on == "on", xs[0].toInt(), xs[1].toInt(), ys[0].toInt(), ys[1].toInt(), zs[0].toInt(), zs[1].toInt())
}

fun part1(cuboids: List<Cuboid>): Long {
    val initArea = Cuboid(false, -50, 50, -50, 50, -50, 50)
    val initAreaCuboids = cuboids.filter { it.isInside(initArea) }

    return countOn(initAreaCuboids)
}

fun countOn(cuboids: List<Cuboid>): Long = combine(cuboids).sumOf { it.countOn() }

fun combine(cuboids: List<Cuboid>): List<Cuboid> {
    var combined = listOf<Cuboid>()
    for ((line, cuboid) in cuboids.withIndex()) {
        combined = add(cuboid, combined)
        println("Processed line $line. Cuboids count = ${combined.size}")
    }

    return combined
}

fun add(newCuboid: Cuboid, cuboids: List<Cuboid>): List<Cuboid> {
    val result = cuboids.asSequence()
        .filterNot { it.isInside(newCuboid) }
        .flatMap { it.remove(newCuboid) }
        .toMutableList()

    if (newCuboid.on) result += newCuboid

    return result
}
