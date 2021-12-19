package day18

import readInput
import java.lang.Integer.max

interface SnailfishNumber {
    fun reduce(): SnailfishNumber
    fun explode(nestLevel: Int): Triple<SnailfishNumber, Int?, Int?>
    fun split(): SnailfishNumber
    fun addLeft(number: Int): SnailfishNumber
    fun addRight(number: Int): SnailfishNumber
    fun magnitude(): Int
    fun add(other: SnailfishNumber): SnailfishNumber = NumberPair(this, other).reduce()
}

data class RegularNumber(val value: Int) : SnailfishNumber {
    override fun reduce(): SnailfishNumber = this
    override fun explode(nestLevel: Int): Triple<SnailfishNumber, Int?, Int?> = Triple(this, null, null)

    override fun split(): SnailfishNumber = if (value >= 10) {
        NumberPair(RegularNumber(value / 2), RegularNumber(value / 2 + value % 2))
    } else {
        this
    }

    override fun addLeft(number: Int): SnailfishNumber = RegularNumber(value + number)
    override fun addRight(number: Int): SnailfishNumber = RegularNumber(value + number)
    override fun magnitude(): Int = value
    override fun toString(): String = value.toString()
}

data class NumberPair(val left: SnailfishNumber, val right: SnailfishNumber) : SnailfishNumber {
    override fun reduce(): SnailfishNumber {
        var prev: SnailfishNumber? = null
        var current: SnailfishNumber = this
        while (prev != current) {
            prev = current
            val exploded = current.explode(0).first
            current = if (exploded != current) exploded else exploded.split()
        }

        return current
    }

    override fun explode(nestLevel: Int): Triple<SnailfishNumber, Int?, Int?> {
        if (nestLevel == 4) {
            val lValue = (left as? RegularNumber)?.value ?: error("$left is not regular number")
            val rValue = (right as? RegularNumber)?.value ?: error("$right is not regular number")

            return Triple(RegularNumber(0), lValue, rValue)
        }

        val (newLeft, addToLeft, addToRight) = left.explode(nestLevel + 1)
        if (newLeft != left) {
            val newRight = addToRight?.let { right.addLeft(it) } ?: right

            return Triple(NumberPair(newLeft, newRight), addToLeft, null)
        } else {
            val (newRight, addToLeft, addToRight) = right.explode(nestLevel + 1)
            if (newRight != right) {
                val newLeft = addToLeft?.let { left.addRight(it) } ?: left

                return Triple(NumberPair(newLeft, newRight), null, addToRight)
            }
        }

        return Triple(this, null, null)
    }

    override fun split(): SnailfishNumber {
        val newLeft = left.split()
        if (newLeft != left) return NumberPair(newLeft, right)

        val newRight = right.split()
        if (newRight != right) return NumberPair(left, newRight)

        return this
    }

    override fun addLeft(number: Int): SnailfishNumber = NumberPair(left.addLeft(number), right)
    override fun addRight(number: Int): SnailfishNumber = NumberPair(left, right.addRight(number))
    override fun magnitude(): Int = 3 * left.magnitude() + 2 * right.magnitude()
    override fun toString(): String = "[$left,$right]"
}

fun main() {
    val input = readInput("day18/input")
    val numbers = input.map { parseSnailfishNumber(it).first }

    println(sum(numbers).magnitude())
    println(part2(numbers))
}

fun parseSnailfishNumber(numberStr: String): Pair<SnailfishNumber, String> {
    val c = numberStr.first()
    if (c.isDigit()) {
        val number = numberStr.takeWhile { it.isDigit() }

        return RegularNumber(number.toInt()) to numberStr.substring(number.length)
    } else if (c == '[') {
        val (left, restRight) = parseSnailfishNumber(numberStr.substring(1))
        if (restRight.first() != ',') error("',' is expected in '$restRight'")
        val (right, rest) = parseSnailfishNumber(restRight.substring(1))
        if (rest.first() != ']') error("']' is expected in '$rest'")

        return NumberPair(left, right) to rest.substring(1)
    }

    error("Unexpected first character in '$numberStr'")
}

fun sum(numbers: List<SnailfishNumber>): SnailfishNumber = numbers.reduce { acc, n -> acc.add(n) }

fun part2(numbers: List<SnailfishNumber>): Int {
    var maxMagnitude = 0
    for (n1 in numbers) {
        for (n2 in numbers) {
            if (n1 == n2) continue
            maxMagnitude = max(n1.add(n2).magnitude(), maxMagnitude)
        }
    }

    return maxMagnitude
}
