package day16

import readInput

const val SUM_TYPE = 0
const val PRODUCT_TYPE = 1
const val MIN_TYPE = 2
const val MAX_TYPE = 3
const val VALUE_TYPE = 4
const val GT_TYPE = 5
const val LT_TYPE = 6
const val EQ_TYPE = 7

interface Packet {
    fun versionSum(): Int
    fun calculate(): Long
}

class Value(val version: Int, val number: Long) : Packet {
    override fun versionSum() = version
    override fun calculate(): Long = number
}

abstract class Operator(open val version: Int, open val subPackets: List<Packet>) : Packet {
    override fun versionSum(): Int = subPackets.sumOf { it.versionSum() } + version
}

class Sum(override val version: Int, override val subPackets: List<Packet>) : Operator(version, subPackets) {
    override fun calculate(): Long = subPackets.sumOf { it.calculate() }
}

class Product(override val version: Int, override val subPackets: List<Packet>) : Operator(version, subPackets) {
    override fun calculate(): Long = subPackets.fold(1) { acc, p -> acc * p.calculate() }
}

class Min(override val version: Int, override val subPackets: List<Packet>) : Operator(version, subPackets) {
    override fun calculate(): Long = subPackets.minOf { it.calculate() }
}

class Max(override val version: Int, override val subPackets: List<Packet>) : Operator(version, subPackets) {
    override fun calculate(): Long = subPackets.maxOf { it.calculate() }
}

class GreaterThan(override val version: Int, override val subPackets: List<Packet>) : Operator(version, subPackets) {
    override fun calculate(): Long = if (subPackets[0].calculate() > subPackets[1].calculate()) 1 else 0
}

class LessThan(override val version: Int, override val subPackets: List<Packet>) : Operator(version, subPackets) {
    override fun calculate(): Long = if (subPackets[0].calculate() < subPackets[1].calculate()) 1 else 0
}

class Equal(override val version: Int, override val subPackets: List<Packet>) : Operator(version, subPackets) {
    override fun calculate(): Long = if (subPackets[0].calculate() == subPackets[1].calculate()) 1 else 0
}

fun main() {
    val input = readInput("day16/input")[0]
    val (packet, _) = decodePacket(toBitString(input))

    println(packet.versionSum())
    println(packet.calculate())
}

fun toBitString(hexString: String): String = buildString(hexString.length * 4) {
    hexString.asIterable()
        .map { it.digitToInt(16) }
        .map { it.toString(2).padStart(4, '0') }
        .forEach { append(it) }
}

fun decodePacket(bits: String): Pair<Packet, String> {
    val version = bits.substring(0, 3).toInt(2)
    val typeId = bits.substring(3, 6).toInt(2)
    val rest = bits.substring(6)

    return if (typeId == VALUE_TYPE) decodeValue(rest, version) else decodeOperator(rest, version, typeId)
}

private fun decodeValue(bits: String, version: Int): Pair<Packet, String> {
    var index = 0
    val numString = buildString {
        do {
            val groupMarker = bits.substring(index, index + 1)
            append(bits.substring(index + 1, index + 5))
            index += 5
        } while (groupMarker != "0")
    }

    return Value(version, numString.toLong(2)) to bits.substring(index)
}

fun createOperator(typeId: Int, version: Int, subPackets: List<Packet>): Operator = when (typeId) {
    SUM_TYPE -> Sum(version, subPackets)
    PRODUCT_TYPE -> Product(version, subPackets)
    MIN_TYPE -> Min(version, subPackets)
    MAX_TYPE -> Max(version, subPackets)
    GT_TYPE -> GreaterThan(version, subPackets)
    LT_TYPE -> LessThan(version, subPackets)
    EQ_TYPE -> Equal(version, subPackets)
    else -> error("Unknown operator type")
}

private fun decodeBitLength(bits: String, version: Int, typeId: Int): Pair<Packet, String> {
    val length = bits.substring(0, 15).toInt(2)
    val rest = bits.substring(15 + length)
    var subBits = bits.substring(15, 15 + length)
    val packets = mutableListOf<Packet>()

    while (subBits.isNotEmpty()) {
        val (packet, r) = decodePacket(subBits)
        packets.add(packet)
        subBits = r
    }

    return createOperator(typeId, version, packets) to rest
}

private fun decodePacketsNumber(bits: String, version: Int, typeId: Int): Pair<Packet, String> {
    val packetsNumber = bits.substring(0, 11).toInt(2)
    var rest = bits.substring(11)
    val packets = mutableListOf<Packet>()

    repeat(packetsNumber) {
        val (packet, r) = decodePacket(rest)
        packets.add(packet)
        rest = r
    }

    return createOperator(typeId, version, packets) to rest
}

private fun decodeOperator(bits: String, version: Int, typeId: Int): Pair<Packet, String> {
    val lengthTypeId = bits.substring(0, 1)
    return if (lengthTypeId == "0") {
        decodeBitLength(bits.substring(1), version, typeId)
    } else {
        decodePacketsNumber(bits.substring(1), version, typeId)
    }
}
