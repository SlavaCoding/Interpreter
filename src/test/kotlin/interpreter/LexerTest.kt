package interpreter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

public class LexerTest {
    @Test
    fun testEmptyLexer() {
        val lexer = Lexer("")
        assertTrue(lexer.nextToken().isType("EOF"))
    }

    @Test
    fun testNumber() {
        val lexer = Lexer("1")
        val result = lexer.nextToken()
        assertTrue(result.isType("int"))
        assertEquals(1, result.value)
    }

    @Test
    fun testDoubleNumber() {
        val lexer = Lexer("1.25")
        val result = lexer.nextToken()
        assertTrue(result.isType("double"))
        assertEquals(1.25, result.value)
    }

    @Test
    fun testFunction() {
        val lexer = Lexer("sin")
        val result = lexer.nextToken()
        assertTrue(result.isType("id"))
        assertEquals("sin", result.value)
    }

    @Test
    fun testSymbolPlus() {
        val lexer = Lexer("+")
        assertTrue(lexer.nextToken().isType("+"))
    }

    @Test
    fun testSymbols() {
        val lexer = Lexer("+-*/")
        assertTrue(lexer.nextToken().isType("+"))
        assertTrue(lexer.nextToken().isType("-"))
        assertTrue(lexer.nextToken().isType("*"))
        assertTrue(lexer.nextToken().isType("/"))
    }

    @Test
    fun testSimpleExpr() {
        val lexer = Lexer("2+3")
        assertEquals(2, lexer.nextToken().value)
        assertTrue(lexer.nextToken().isType("+"))
        assertEquals(3, lexer.nextToken().value)
    }

    @Test
    fun testSimpleExprWithSpace() {
        val lexer = Lexer("2 + 3")
        assertEquals(2, lexer.nextToken().value)
        assertTrue(lexer.nextToken().isType("+"))
        assertEquals(3, lexer.nextToken().value)
    }

    @Test
    fun testFunctionExpr() {
        val lexer = Lexer("sin(0.6)*2+3.0")
        assertEquals("sin", lexer.nextToken().value)
        assertTrue(lexer.nextToken().isType("("))
        assertEquals(0.6, lexer.nextToken().value)
        assertTrue(lexer.nextToken().isType(")"))
        assertTrue(lexer.nextToken().isType("*"))
        assertEquals(2, lexer.nextToken().value)
        assertTrue(lexer.nextToken().isType("+"))
        assertEquals(3.0, lexer.nextToken().value)
    }

    @Test
    fun testErrorNumber() {
        assertThrows<NumberFormatException>{
            val lexer = Lexer("1.2.3")
        }
    }

    @Test
    fun testLogicSymbols() {
        val lexer = Lexer(">= <= != & |")
        assertEquals(">=", lexer.nextToken().value)
        assertEquals("<=", lexer.nextToken().value)
        assertEquals("!=", lexer.nextToken().value)
        assertEquals("&", lexer.nextToken().value)
        assertEquals("|", lexer.nextToken().value)
    }
}