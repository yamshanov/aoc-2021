package day20

import readInput

class Image(val pixels: Array<IntArray>, private val enhancement: IntArray, private val outsidePixel: Int = 0) {
    private val n: Int
    private val m: Int
    private val directions = listOf(
        -1 to -1,
        -1 to 0,
        -1 to 1,
        0 to -1,
        0 to 0,
        0 to 1,
        1 to -1,
        1 to 0,
        1 to 1
    )

    init {
        check(pixels.isNotEmpty())
        check(pixels.all { it.isNotEmpty() })
        n = pixels.size - 1
        m = pixels[0].size - 1
    }

    fun enhance(): Image {
        val newPixels = Array(pixels.size + 2) { IntArray(pixels[0].size + 2) }
        for (i in newPixels.indices) {
            for (j in newPixels[i].indices) {
                newPixels[i][j] = enhancement[enhancementIndex(i - 1, j - 1)]
            }
        }
        val newOutside = if (outsidePixel == 0) enhancement[0] else enhancement[511]

        return Image(newPixels, enhancement, newOutside)
    }

    fun enhance(times: Int): Image {
        check(times > 0)
        var image = this
        repeat(times) {
            image = image.enhance()
        }

        return image
    }

    fun litPixels(): Int = pixels.sumOf { it.sum() }

    private fun get(i: Int, j: Int): Int = if ((i in 0..n) && (j in 0..m)) pixels[i][j] else outsidePixel
    private fun enhancementIndex(i: Int, j: Int): Int = directions
        .map { (di, dj) -> get(i + di, j + dj) }
        .fold(0) { acc, p -> (acc shl 1) + p }
}

fun main() {
    val input = readInput("day20/input")
    val enhancement = toIntArray(input[0])
    val pixels = toPixels(input.subList(2, input.size))
    val image = Image(pixels, enhancement)

    println(image.enhance(2).litPixels())
    println(image.enhance(50).litPixels())
}

fun toIntArray(line: String): IntArray = line.asIterable()
    .map { if (it == '#') 1 else 0 }
    .toIntArray()

fun toPixels(lines: List<String>): Array<IntArray> = lines.map(::toIntArray).toTypedArray()
