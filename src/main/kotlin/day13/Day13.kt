package day13

import readInput

data class Dot(val x: Int, val y: Int) {
    fun foldX(x: Int) = Dot(x - (this.x - x), this.y)
    fun foldY(y: Int) = Dot(this.x, y - (this.y - y))
}

data class FoldInstruction(val axis: String, val value: Int)

class Paper(dots: Collection<Dot>) {
    private val dots = HashSet(dots)

    fun fold(instruction: FoldInstruction) {
        when (instruction.axis) {
            "x" -> foldX(instruction.value)
            "y" -> foldY(instruction.value)
        }
    }

    fun countDots() = dots.size

    fun image(): List<String> {
        val width = dots.maxOf { it.x } + 1
        val height = dots.maxOf { it.y } + 1
        val paper = Array(height) { CharArray(width) { '.' } }
        for (d in dots) paper[d.y][d.x] = '#'

        return paper.map { String(it) }
    }

    private fun foldX(x: Int) {
        val right = dots.asSequence().filter { it.x > x }.toSet()
        dots.removeAll(right)
        val folded = right.map { it.foldX(x) }
        dots.addAll(folded)
    }

    private fun foldY(y: Int) {
        val below = dots.asSequence().filter { it.y > y }.toSet()
        dots.removeAll(below)
        val folded = below.map { it.foldY(y) }
        dots.addAll(folded)
    }
}

fun parseDot(line: String): Dot = line.split(",").map { it.toInt() }.let { Dot(it[0], it[1]) }

fun parseInstruction(line: String): FoldInstruction = line
    .removePrefix("fold along ")
    .split("=")
    .let { FoldInstruction(it[0], it[1].toInt()) }

fun part1(paper: Paper, instructions: List<FoldInstruction>): Int {
    paper.fold(instructions[0])

    return paper.countDots()
}

fun part2(paper: Paper, instructions: List<FoldInstruction>) {
    instructions.forEach { paper.fold(it) }
    paper.image().forEach { println(it) }
}

fun main() {
    val input = readInput("day13/input")
    val dots = input.takeWhile { it.isNotBlank() }.map { parseDot(it) }
    val instructions = input.filter { it.startsWith("fold") }.map { parseInstruction(it) }

    println(part1(Paper(dots), instructions))
    part2(Paper(dots), instructions)
}
