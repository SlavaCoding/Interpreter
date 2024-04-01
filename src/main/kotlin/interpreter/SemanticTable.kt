package interpreter

import interpreter.operators.BinaryAction
import interpreter.operators.Operator
import interpreter.operators.UnaryAction

class SemanticTable{
    var unaryOpTable = hashMapOf(
        Pair("+", Type.Int)    to Pair(Type.Int,    UnaryAction{a -> a as Int}),
        Pair("+", Type.Double) to Pair(Type.Double, UnaryAction{a -> a as Double}),
        Pair("-", Type.Int)    to Pair(Type.Int,    UnaryAction{a -> -(a as Int)}),
        Pair("-", Type.Double) to Pair(Type.Double, UnaryAction{a -> -(a as Double)})
    )
    var binaryOpTable = hashMapOf(
        Triple("+", Type.Int,   Type.Int)    to Pair(Type.Int,    BinaryAction{ a, b -> (a as Int)+(b as Int) }),
        Triple("+", Type.Double,Type.Int)    to Pair(Type.Double, BinaryAction{ a, b -> (a as Double)+(b as Int) }),
        Triple("+", Type.Int,   Type.Double) to Pair(Type.Double, BinaryAction{ a, b -> (a as Int)+(b as Double) }),
        Triple("+", Type.Double,Type.Double) to Pair(Type.Double, BinaryAction{ a, b -> (a as Double)+(b as Double) }),

        Triple("-", Type.Int,   Type.Int)    to Pair(Type.Int,    BinaryAction{ a, b -> (a as Int)-(b as Int) }),
        Triple("-", Type.Double,Type.Int)    to Pair(Type.Double, BinaryAction{ a, b -> (a as Double)-(b as Int) }),
        Triple("-", Type.Int,   Type.Double) to Pair(Type.Double, BinaryAction{ a, b -> (a as Int)-(b as Double) }),
        Triple("-", Type.Double,Type.Double) to Pair(Type.Double, BinaryAction{ a, b -> (a as Double)-(b as Double) })
    )

    fun getBinaryAction(op: String, leftOp: Operator, rightOp: Operator): Pair<Type, BinaryAction> {Int
        val leftType = leftOp.returnType
        val rightType = rightOp.returnType
        val action = binaryOpTable[Triple(op, leftType, rightType)]
        action?.let {
            return it
        }
        throw RuntimeException("Семантическая ошибка. Оператор \"$op\" не определен для типов \"$leftType\" и \"$rightType\".")
    }

    fun getUnaryAction(op: String, unOp: Operator): Pair<Type, UnaryAction> {
        val type = unOp.returnType
        val action = unaryOpTable[Pair(op, type)]
        action?.let {
            return it
        }
        throw RuntimeException("Семантическая ошибка. Оператор \"$op\" не определен для типа \"$type\"\".")
    }
}