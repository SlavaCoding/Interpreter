package interpreter.typing

import interpreter.operators.BinaryAction
import interpreter.operators.UnaryAction
import kotlin.math.pow

class IntType : Type(BaseType.Int) {
    override fun getUnaryAction(op: String): Pair<Type, UnaryAction> {
        return when (op) {
            "-" -> Pair(IntType(), UnaryAction{ a -> -(a as Int)})
            "+" -> Pair(IntType(), UnaryAction{ a -> a as Int})
            else -> super.getUnaryAction(op)
        }
    }

    override fun getBinaryAction(op: String, other: Type): Pair<Type, BinaryAction> {
        if (other.kind == BaseType.Double) return DoubleType().getBinaryAction(op, other)
        if (other.kind == BaseType.Int)
            return when (op) {
                "+" -> Pair(IntType(),      BinaryAction{ a, b -> (a as Int)+(b as Int) })
                "-" -> Pair(IntType(),      BinaryAction{ a, b -> (a as Int)-(b as Int) })
                "*" -> Pair(IntType(),      BinaryAction{ a, b -> (a as Int)*(b as Int) })
                "/" -> Pair(IntType(),      BinaryAction{ a, b -> (a as Int)/(b as Int) })
                "^" -> Pair(IntType(),      BinaryAction{ a, b -> (a as Int).toDouble().pow((b as Int).toDouble()).toInt() })
                "%" -> Pair(IntType(),      BinaryAction{ a, b -> (a as Int)%(b as Int) })
                ">" -> Pair(BooleanType(),  BinaryAction{ a, b -> (a as Int)>(b as Int) })
                "<" -> Pair(BooleanType(),  BinaryAction{ a, b -> (a as Int)<(b as Int) })
                ">=" -> Pair(BooleanType(), BinaryAction{ a, b -> (a as Int)>=(b as Int) })
                "<=" -> Pair(BooleanType(), BinaryAction{ a, b -> (a as Int)<=(b as Int) })
                "==" -> Pair(BooleanType(), BinaryAction{ a, b -> (a as Int)==(b as Int) })
                "!=" -> Pair(BooleanType(), BinaryAction{ a, b -> (a as Int)!=(b as Int) })
                else -> super.getBinaryAction(op, other)
            }
        return super.getBinaryAction(op, other)
    }

}