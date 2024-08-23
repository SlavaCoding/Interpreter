package interpreter

import interpreter.operators.BinaryOperator
import interpreter.operators.UnaryOperator
import interpreter.operators.ValueOperator
import interpreter.typing.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class OperatorTest {
    @Test
    fun testValueOperator() {
        val op1 = ValueOperator(1.5, DoubleType())
        assertEquals(1.5, op1.getValue())
        val op2 = ValueOperator(true, BooleanType())
        assertTrue(op2.getValue() as Boolean)
    }

    @Test
    fun testUnaryMinus() {
        val num = ValueOperator(2, IntType())
        val (returnType, action) = num.returnType.getUnaryAction("-")
        val unOp = UnaryOperator(action, num, returnType)
        assertEquals(-2, unOp.getValue())
    }

//    @Test
//    fun testFunctionOdd() {
//        val num: Operator = ValueOperator(2, Type.Int)
//        val unOp: Operator = UnaryOperator({ a: Int -> a % 2 == 1 }, num)
//        assertFalse(unOp.getValue())
//    }

    @Test
    fun testBinaryPlus() {
        val two = ValueOperator(2, IntType())
        val three = ValueOperator(3, IntType())
        val (returnType, action) = two.returnType.getBinaryAction("+", three.returnType)
        val binOp = BinaryOperator(action, two, three, returnType)
        assertEquals(5, binOp.getValue())
    }
}