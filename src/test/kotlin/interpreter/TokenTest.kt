package interpreter

import interpreter.tokens.Token
import interpreter.tokens.Token.TokenType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


internal class TokenTest {
    @Test
    fun testCharToken() {
        val token = Token(TokenType.Symbol, "+")
        assertEquals(TokenType.Symbol, token.type)
    }

    @Test
    fun testNumsToken() {
        val token = Token(TokenType.Double, 2.5)
        assertEquals(TokenType.Double, token.type)
        assertEquals(2.5, token.value)
    }

    @Test
    fun testIdToken() {
        val token = Token(TokenType.Id, "sin")
        assertEquals(TokenType.Id, token.type)
        assertEquals("sin", token.value)
    }
}