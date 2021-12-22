package day22

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class Day22KtTest {

    @Test
    fun parseCuboid() {
        val line1 = "on x=-49..1,y=-3..46,z=-24..28"
        val cuboid1 = parseCuboid(line1)
        assertTrue(cuboid1.on)
        assertEquals(-49, cuboid1.x1)
        assertEquals(1, cuboid1.x2)
        assertEquals(-3, cuboid1.y1)
        assertEquals(46, cuboid1.y2)
        assertEquals(-24, cuboid1.z1)
        assertEquals(28, cuboid1.z2)

        val line2 = "off x=-54112..-39298,y=-85059..-49293,z=-27449..7877"
        val cuboid2 = parseCuboid(line2)
        assertFalse(cuboid2.on)
        assertEquals(-54112, cuboid2.x1)
        assertEquals(-39298, cuboid2.x2)
        assertEquals(-85059, cuboid2.y1)
        assertEquals(-49293, cuboid2.y2)
        assertEquals(-27449, cuboid2.z1)
        assertEquals(7877, cuboid2.z2)
    }
}
