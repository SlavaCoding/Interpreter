package interpreter

import interpreter.operators.BinaryAction
import interpreter.operators.Operator
import interpreter.operators.UnaryAction
import kotlin.math.pow

class SemanticTable{
    private var unaryOpTable = hashMapOf(
        Pair("+", Type.Int)     to Pair(Type.Int,     UnaryAction{a -> a as Int}),
        Pair("+", Type.Double)  to Pair(Type.Double,  UnaryAction{a -> a as Double}),
        Pair("-", Type.Int)     to Pair(Type.Int,     UnaryAction{a -> -(a as Int)}),
        Pair("-", Type.Double)  to Pair(Type.Double,  UnaryAction{a -> -(a as Double)}),
        Pair("!", Type.Boolean) to Pair(Type.Boolean, UnaryAction{a -> !(a as Boolean)})
    )
    private var binaryOpTable = hashMapOf(
        Triple("+",  Type.Int,    Type.Int)     to Pair(Type.Int,     BinaryAction{ a, b -> (a as Int)+(b as Int) }),
        Triple("+",  Type.Double, Type.Double)  to Pair(Type.Double,  BinaryAction{ a, b -> (a as Number).toDouble()+(b as Number).toDouble() }),

        Triple("-",  Type.Int,    Type.Int)     to Pair(Type.Int,     BinaryAction{ a, b -> (a as Int)-(b as Int) }),
        Triple("-",  Type.Double, Type.Double)  to Pair(Type.Double,  BinaryAction{ a, b -> (a as Number).toDouble()-(b as Number).toDouble() }),

        Triple("*",  Type.Int,    Type.Int)     to Pair(Type.Int,     BinaryAction{ a, b -> (a as Int)*(b as Int) }),
        Triple("*",  Type.Double, Type.Double)  to Pair(Type.Double,  BinaryAction{ a, b -> (a as Number).toDouble()*(b as Number).toDouble() }),

        Triple("/",  Type.Int,    Type.Int)     to Pair(Type.Int,     BinaryAction{ a, b -> (a as Int)/(b as Int) }),
        Triple("/",  Type.Double, Type.Double)  to Pair(Type.Double,  BinaryAction{ a, b -> (a as Number).toDouble()/(b as Number).toDouble() }),

        Triple("^",  Type.Int,    Type.Int)     to Pair(Type.Int,     BinaryAction{ a, b -> (a as Int).toDouble().pow((b as Int).toDouble()).toInt() }),
        Triple("^",  Type.Double, Type.Double)  to Pair(Type.Double,  BinaryAction{ a, b -> (a as Number).toDouble().pow((b as Number).toDouble()) }),

        Triple("%",  Type.Int,    Type.Int)     to Pair(Type.Int,     BinaryAction{ a, b -> (a as Int)%(b as Int) }),
        Triple("%",  Type.Double, Type.Double)  to Pair(Type.Double,  BinaryAction{ a, b -> (a as Number).toDouble()%(b as Number).toDouble() }),

        Triple(">",  Type.Int,    Type.Int)     to Pair(Type.Boolean, BinaryAction{ a, b -> (a as Int)>(b as Int) }),
        Triple(">",  Type.Double, Type.Double)  to Pair(Type.Boolean, BinaryAction{ a, b -> (a as Number).toDouble()>(b as Number).toDouble() }),

        Triple("<",  Type.Int,    Type.Int)     to Pair(Type.Boolean, BinaryAction{ a, b -> (a as Int)<(b as Int) }),
        Triple("<",  Type.Double, Type.Double)  to Pair(Type.Boolean, BinaryAction{ a, b -> (a as Number).toDouble()<(b as Number).toDouble() }),

        Triple(">=", Type.Int,    Type.Int)     to Pair(Type.Boolean, BinaryAction{ a, b -> (a as Int)>=(b as Int) }),
        Triple(">=", Type.Double, Type.Double)  to Pair(Type.Boolean, BinaryAction{ a, b -> (a as Number).toDouble()>=(b as Number).toDouble() }),

        Triple("<=", Type.Int,    Type.Int)     to Pair(Type.Boolean, BinaryAction{ a, b -> (a as Int)<=(b as Int) }),
        Triple("<=", Type.Double, Type.Double)  to Pair(Type.Boolean, BinaryAction{ a, b -> (a as Number).toDouble()<=(b as Number).toDouble() }),

        Triple("==", Type.Int,    Type.Int)     to Pair(Type.Boolean, BinaryAction{ a, b -> (a as Int)==(b as Int) }),
        Triple("==", Type.Double, Type.Double)  to Pair(Type.Boolean, BinaryAction{ a, b -> (a as Number).toDouble()==(b as Number).toDouble() }),

        Triple("!=", Type.Int,    Type.Int)     to Pair(Type.Boolean, BinaryAction{ a, b -> (a as Int)!=(b as Int) }),
        Triple("!=", Type.Double, Type.Double)  to Pair(Type.Boolean, BinaryAction{ a, b -> (a as Number).toDouble()!=(b as Number).toDouble() }),

        Triple("&&", Type.Boolean,Type.Boolean) to Pair(Type.Boolean, BinaryAction{ a, b -> (a as Boolean) && (b as Boolean) }),
        Triple("||", Type.Boolean,Type.Boolean) to Pair(Type.Boolean, BinaryAction{ a, b -> (a as Boolean) || (b as Boolean) })
    )

    fun getBinaryAction(op: String, leftOp: Operator, rightOp: Operator): Pair<Type, BinaryAction> {Int
        var leftType = leftOp.returnType
        var rightType = rightOp.returnType
        // Приведение типа Int к Double если один из операндов Double
        if (leftType == Type.Double || rightType == Type.Double){
            leftType = Type.Double
            rightType = Type.Double
        }
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