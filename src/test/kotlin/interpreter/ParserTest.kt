package interpreter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.math.sin

internal class ParserTest {
    private var parser: Parser = Parser()

    @Test
    fun testValues() {
        assertEquals(3, parser.eval("3").result)
        assertEquals(3.0, parser.eval("3.0").result)
        assertEquals(-3.0, parser.eval("-3.0").result)
    }
    @Test
    fun testSum() {
        assertEquals(5, parser.eval("2+3").result)
        assertEquals(1.0, parser.eval("3.0-2").result)
        assertEquals(-1.0, parser.eval("2+3-6.0").result)
    }

    @Test
    fun testMul() {
        assertEquals(6, parser.eval("2*3").result)
        assertEquals(1, parser.eval("3/2").result)
        assertEquals(1.5, parser.eval("3.0/2.0").result)
        assertEquals(7.5, parser.eval("3.0/2*5").result)
    }

    @Test
    fun testPow() {
        assertEquals(8, parser.eval("2^3").result)
        assertEquals(3.0, parser.eval("9^0.5").result)
    }

    @Test
    fun testUnMinus() {
        assertEquals(-6, parser.eval("2*-3").result)
        assertEquals(-6, parser.eval("--2*-3").result)
    }

    @Test
    fun testBrackets() {
        assertEquals(20, parser.eval("(2+3)*4").result)
        assertEquals(3.0, parser.eval("9^(1/2.0)").result)
        try {
            assertEquals(20, parser.eval("(2+3(*4").result)
            fail()
        } catch (ignored: RuntimeException) {
        }
        //assertEquals(20.0, parser.eval(")2+3(*4").getResult());
    }

    @Test
    fun testFunctions() {
        assertEquals(2, parser.eval("abs(-2)").result)
        assertEquals(sin(Math.toRadians(30.0)), parser.eval("sin(rad(30))").result)
        assertEquals(sin(Math.toRadians(30.0)), parser.eval("sin(rad(30))").result)
    }

    @Test
    fun testCompare() {
        assertTrue(parser.eval("2 < 3").result as Boolean)
        assertFalse(parser.eval("2 > 3").result as Boolean)
        assertTrue(parser.eval("3 <= 3").result as Boolean)
        assertTrue(parser.eval("3 >= 3.0").result as Boolean)
        assertTrue(parser.eval("3.0 == 3.0").result as Boolean)
        assertFalse(parser.eval("2 == 3").result as Boolean)
        assertTrue(parser.eval("2.0 != 3").result as Boolean)
        assertFalse(parser.eval("3.0 != 3").result as Boolean)
        assertTrue(parser.eval("1 < 2 < 3").result as Boolean)
        assertFalse(parser.eval("1 < 2 > 3").result as Boolean)
    }
    @Test
    fun testTrueFalse(){
        assertTrue(parser.eval("true || false").result as Boolean)
        assertTrue(parser.eval("false || true").result as Boolean)
        assertFalse(parser.eval("true && false").result as Boolean)
        assertFalse(parser.eval("false && true").result as Boolean)
        assertTrue(parser.eval("true || true").result as Boolean)
        assertFalse(parser.eval("false || false").result as Boolean)
        assertTrue(parser.eval("true && true").result as Boolean)
        assertFalse(parser.eval("false && false").result as Boolean)
    }
    @Test
    fun testLogic(){
        assertTrue(parser.eval("! 2 > 3").result as Boolean)
        assertTrue(parser.eval("! 2 > 3").result as Boolean)
        assertTrue(parser.eval("2 < 3 || 2 > 3").result as Boolean)
        assertFalse(parser.eval("2 < 3 && 2 > 3").result as Boolean)
        assertTrue(parser.eval("2 < 3 && !(2 > 3)").result as Boolean)
    }
    @Test
    fun testVariable(){
        assertEquals(5, parser.eval("2+a", hashMapOf("a" to Pair(3, Type.Int))).result)
        assertEquals(12, parser.eval("b+b*b", hashMapOf("b" to Pair(3, Type.Int))).result)
        val expression = parser.eval("a+a*b", hashMapOf("a" to Pair(2, Type.Int),
                                                             "b" to Pair(3.0, Type.Double)))
        assertEquals(8.0, expression.result)
        expression.globalVariableTable.setVariable("a", 4)
        assertEquals(16.0, expression.result)
    }
}