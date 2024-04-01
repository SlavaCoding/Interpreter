package interpreter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.math.sin

internal class ParserTest {
    var parser: Parser = Parser()

    @Test
    fun testValues() {
        assertEquals(3, parser.eval("3").result)
        assertEquals(3.0, parser.eval("3.0").result)
        assertEquals(-3.0, parser.eval("-3.0").result)
    }
    @Test
    fun testSum() {
        assertEquals(5.0, parser.eval("2+3").result)
        assertEquals(1.0, parser.eval("3-2").result)
        assertEquals(-1.0, parser.eval("2+3-6").result)
    }

    @Test
    fun testMul() {
        assertEquals(6.0, parser.eval("2*3").result)
        assertEquals(1.5, parser.eval("3/2").result)
        assertEquals(7.5, parser.eval("3/2*5").result)
    }

    @Test
    fun testPow() {
        assertEquals(8.0, parser.eval("2^3").result)
        assertEquals(3.0, parser.eval("9^0.5").result)
    }

    @Test
    fun testUnMinus() {
        assertEquals(-6.0, parser.eval("2*-3").result)
        assertEquals(-6.0, parser.eval("--2*-3").result)
    }

    @Test
    fun testBrackets() {
        assertEquals(20.0, parser.eval("(2+3)*4").result)
        assertEquals(3.0, parser.eval("9^(1/2)").result)
        try {
            assertEquals(20.0, parser.eval("(2+3(*4").result)
            fail()
        } catch (ignored: RuntimeException) {
        }
        //assertEquals(20.0, parser.eval(")2+3(*4").getResult());
    }

    @Test
    fun testFunctions() {
        assertEquals(2.0, parser.eval("abs(-2)").result)
        assertEquals(sin(Math.toRadians(30.0)), parser.eval("sin(rad(30))").result)
        assertEquals(sin(Math.toRadians(30.0)), parser.eval("sin(rad(30))").result)
    }

    @Test
    @Disabled
    fun testCompare() {
        val expr = parser.eval("2<3")
        assertTrue(expr.result as Boolean)
    }

}