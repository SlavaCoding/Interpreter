package interpreter

import interpreter.operators.BinaryOperator
import interpreter.operators.Operator
import interpreter.operators.UnaryOperator
import interpreter.operators.ValueOperator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class OperatorTest {
    val semanticTable = SemanticTable()
    @Test
    fun testValueOperator() {
        val op1 = ValueOperator(1.5, Type.Double)
        assertEquals(1.5, op1.getValue())
        val op2 = ValueOperator(true, Type.Boolean)
        assertTrue(op2.getValue() as Boolean)
    }

    @Test
    fun testUnaryMinus() {
        val num = ValueOperator(2, Type.Int)
        val (returnType, action) = semanticTable.getUnaryAction("-", num)
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
        val two = ValueOperator(2, Type.Int)
        val three = ValueOperator(3, Type.Int)
        val (returnType, action) = semanticTable.getBinaryAction("+", two, three)
        val binOp = BinaryOperator(action, two, three, returnType)
        assertEquals(5, binOp.getValue())
    }
}