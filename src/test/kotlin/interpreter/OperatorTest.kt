package interpreter

import interpreter.Operators.BinaryAction
import interpreter.Operators.BinaryOperator
import interpreter.Operators.Operator
import interpreter.Operators.UnaryAction
import interpreter.Operators.UnaryOperator
import interpreter.Operators.ValueOperator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class OperatorTest {
    @Test
    fun testValueOperator() {
        val op1: Operator<Double> = ValueOperator<Double>(1.5)
        assertEquals(1.5, op1.getValue())
        val op2: Operator<Boolean> = ValueOperator<Boolean>(true)
        assertTrue(op2.getValue())
    }

    @Test
    fun testUnaryMinus() {
        val num: Operator<Int> = ValueOperator<Int>(2)
        val unOp: Operator<Int> = UnaryOperator<Int, Int>(
            UnaryAction<Int, Int> { a: Int? -> -a!! }, num
        )
        assertEquals(-2, unOp.getValue())
    }

    @Test
    fun testFunctionOdd() {
        val num: Operator<Int> = ValueOperator<Int>(2)
        val unOp: Operator<Boolean> = UnaryOperator<Int, Boolean>(
            UnaryAction<Int, Boolean> { a: Int -> a % 2 == 1 }, num
        )
        assertFalse(unOp.getValue())
    }

    @Test
    fun testBinaryPlus() {
        val two: Operator<Int> = ValueOperator<Int>(2)
        val three: Operator<Int> = ValueOperator<Int>(3)
        val binOp: Operator<Int> = BinaryOperator<Int, Int, Int>(
            BinaryAction<Int, Int, Int> { a: Int, b: Int -> a + b }, two, three
        )
        assertEquals(5, binOp.getValue())
    }
}