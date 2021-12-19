package day18

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day18KtTest {

    @Test
    fun `parseSnailfishNumber parses regular number`() {
        val (number, rest) = parseSnailfishNumber("123")
        assertEquals(RegularNumber::class, number::class)
        assertEquals("", rest)

        val regularNumber = number as RegularNumber
        assertEquals(123, regularNumber.value)
    }

    @Test
    fun `parseSnailfishNumber parses simple snailfish number`() {
        val (number, rest) = parseSnailfishNumber("[1,2]")
        assertEquals(NumberPair::class, number::class)
        assertEquals("", rest)

        val pair = number as NumberPair
        assertEquals(RegularNumber::class, pair.left::class)
        assertEquals(RegularNumber::class, pair.right::class)
        assertEquals(1, (pair.left as RegularNumber).value)
        assertEquals(2, (pair.right as RegularNumber).value)
    }

    @Test
    fun `parseSnailfishNumber parses complex snailfish number`() {
        val (number, rest) =
            parseSnailfishNumber("[[[[1,3],[5,3]],[[1,3],[8,7]]],[[[4,9],[6,9]],[[8,2],[7,3]]]]")
        assertEquals(NumberPair::class, number::class)
        assertEquals("", rest)

        val pair = number as NumberPair
        assertEquals(NumberPair::class, pair.left::class)
        assertEquals(NumberPair::class, pair.right::class)
    }

    @Test
    fun explode1() {
        val (number, _) = parseSnailfishNumber("[[[[[9,8],1],2],3],4]")
        val (exploded, _, _) = number.explode(0)

        val (expected, _) = parseSnailfishNumber("[[[[0,9],2],3],4]")
        assertEquals(expected, exploded)
    }

    @Test
    fun explode2() {
        val (number, _) = parseSnailfishNumber("[7,[6,[5,[4,[3,2]]]]]")
        val (exploded, _, _) = number.explode(0)

        val (expected, _) = parseSnailfishNumber("[7,[6,[5,[7,0]]]]")
        assertEquals(expected, exploded)
    }

    @Test
    fun explode3() {
        val (number, _) = parseSnailfishNumber("[[6,[5,[4,[3,2]]]],1]")
        val (exploded, _, _) = number.explode(0)

        val (expected, _) = parseSnailfishNumber("[[6,[5,[7,0]]],3]")
        assertEquals(expected, exploded)
    }

    @Test
    fun explode4() {
        val (number, _) = parseSnailfishNumber("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]")
        val (exploded, _, _) = number.explode(0)

        val (expected, _) = parseSnailfishNumber("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]")
        assertEquals(expected, exploded)
    }

    @Test
    fun explode5() {
        val (number, _) = parseSnailfishNumber("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]")
        val (exploded, _, _) = number.explode(0)

        val (expected, _) = parseSnailfishNumber("[[3,[2,[8,0]]],[9,[5,[7,0]]]]")
        assertEquals(expected, exploded)
    }

    @Test
    fun explodeNothing() {
        val (number, _) = parseSnailfishNumber("[[3,[2,[8,0]]],[9,[5,[4,5]]]]")
        val (exploded, _, _) = number.explode(0)

        assertEquals(number, exploded)
    }

    @Test
    fun splitRegular() {
        val number1 = RegularNumber(10)
        val split1 = number1.split()
        assertEquals(NumberPair(RegularNumber(5), RegularNumber(5)), split1)

        val number2 = RegularNumber(11)
        val split2 = number2.split()
        assertEquals(NumberPair(RegularNumber(5), RegularNumber(6)), split2)
    }

    @Test
    fun splitComplex() {
        val (number, _) = parseSnailfishNumber("[[[[0,7],4],[15,[0,13]]],[1,1]]")
        val split = number.split()

        val (expected, _) = parseSnailfishNumber("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]")
        assertEquals(expected, split)
    }

    @Test
    fun reduce() {
        val (number, _) = parseSnailfishNumber("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]")
        val result = number.reduce()

        val (expected, _) = parseSnailfishNumber("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]")
        assertEquals(expected, result)
    }

    @Test
    fun sum1() {
        val numbers = listOf(
            "[1,1]",
            "[2,2]",
            "[3,3]",
            "[4,4]",
            "[5,5]",
            "[6,6]",
        ).map { parseSnailfishNumber(it).first }
        val sum = sum(numbers)

        val (expected, _) = parseSnailfishNumber("[[[[5,0],[7,4]],[5,5]],[6,6]]")
        assertEquals(expected, sum)
    }

    @Test
    fun sum2() {
        val numbers = listOf(
            "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
            "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]",
            "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]",
            "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]",
            "[7,[5,[[3,8],[1,4]]]]",
            "[[2,[2,2]],[8,[8,1]]]",
            "[2,9]",
            "[1,[[[9,3],9],[[9,0],[0,7]]]]",
            "[[[5,[7,4]],7],1]",
            "[[[[4,2],2],6],[8,7]]"
        ).map { parseSnailfishNumber(it).first }
        val sum = sum(numbers)

        val (expected, _) = parseSnailfishNumber("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]")
        assertEquals(expected, sum)
    }

    @Test
    fun magnitude() {
        val (number, _) = parseSnailfishNumber("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]")
        val magnitude = number.magnitude()

        assertEquals(3488, magnitude)
    }
}
