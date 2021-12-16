package day16

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day16KtTest {

    @Test
    fun toBitString() {
        val input = "0Ac"
        val bits = toBitString(input)

        assertEquals("000010101100", bits)
    }

    @Test
    fun decodeValuePacket() {
        val input = "110100101111111000101000"

        val (packet, rest) = decodePacket(input)
        assertEquals(Value::class, packet::class)
        assertEquals("000", rest)

        val value = packet as Value
        assertEquals(6, value.version)
        assertEquals(2021, value.number)
    }

    @Test
    fun decodeBitLengthOperator() {
        val input = "00111000000000000110111101000101001010010001001000000000"

        val (packet, rest) = decodePacket(input)
        assertEquals(LessThan::class, packet::class)
        assertEquals("0000000", rest)

        val op = packet as Operator
        assertEquals(1, op.version)
        assertEquals(2, op.subPackets.size)

        val a = op.subPackets[0] as Value
        assertEquals(10, a.number)

        val b = op.subPackets[1] as Value
        assertEquals(20, b.number)
    }

    @Test
    fun decodePacketsNumberOperator() {
        val input = "11101110000000001101010000001100100000100011000001100000"

        val (packet, rest) = decodePacket(input)
        assertEquals(Max::class, packet::class)
        assertEquals("00000", rest)

        val op = packet as Operator
        assertEquals(7, op.version)
        assertEquals(3, op.subPackets.size)

        val a = op.subPackets[0] as Value
        assertEquals(1, a.number)

        val b = op.subPackets[1] as Value
        assertEquals(2, b.number)

        val c = op.subPackets[2] as Value
        assertEquals(3, c.number)
    }

    @Test
    fun part1() {
        val (packet1, _) = decodePacket(toBitString("8A004A801A8002F478"))
        assertEquals(16, packet1.versionSum())

        val (packet2, _) = decodePacket(toBitString("620080001611562C8802118E34"))
        assertEquals(12, packet2.versionSum())

        val (packet3, _) = decodePacket(toBitString("C0015000016115A2E0802F182340"))
        assertEquals(23, packet3.versionSum())

        val (packet4, _) = decodePacket(toBitString("A0016C880162017C3686B18A3D4780"))
        assertEquals(31, packet4.versionSum())
    }

    @Test
    fun part2() {
        // sum
        val (packet1, _) = decodePacket(toBitString("C200B40A82"))
        assertEquals(3, packet1.calculate())

        // product
        val (packet2, _) = decodePacket(toBitString("04005AC33890"))
        assertEquals(54, packet2.calculate())

        // min
        val (packet3, _) = decodePacket(toBitString("880086C3E88112"))
        assertEquals(7, packet3.calculate())

        // max
        val (packet4, _) = decodePacket(toBitString("CE00C43D881120"))
        assertEquals(9, packet4.calculate())

        // less than
        val (packet5, _) = decodePacket(toBitString("D8005AC2A8F0"))
        assertEquals(1, packet5.calculate())

        // greater than
        val (packet6, _) = decodePacket(toBitString("F600BC2D8F"))
        assertEquals(0, packet6.calculate())

        // equal
        val (packet7, _) = decodePacket(toBitString("9C005AC2F8F0"))
        assertEquals(0, packet7.calculate())

        // 1 + 3 = 2 * 2
        val (packet8, _) = decodePacket(toBitString("9C0141080250320F1802104A08"))
        assertEquals(1, packet8.calculate())
    }
}
