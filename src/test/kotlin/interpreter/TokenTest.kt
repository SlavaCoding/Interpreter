package interpreter

import interpreter.tokens.Token
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


internal class TokenTest {
    @Test
    fun testCharToken() {
        val token = Token("+", null)
        assertTrue(token.isType("+"))
    }

    @Test
    fun testNumsToken() {
        val token = Token("double", 2.5)
        assertTrue(token.isType("double"))
        assertEquals(2.5, token.value)
    }

    @Test
    fun testIdToken() {
        val token = Token("id", "sin")
        assertTrue(token.isType("id"))
        assertEquals("sin", token.value)
    }
}