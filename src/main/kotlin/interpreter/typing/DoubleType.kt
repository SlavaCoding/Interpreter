package interpreter.typing

import interpreter.operators.BinaryAction
import interpreter.operators.UnaryAction
import kotlin.math.pow

class DoubleType : Type(BaseType.Double){
    override fun getUnaryAction(op: String): Pair<Type, UnaryAction> {
        return when (op) {
            "-" -> Pair(DoubleType(), UnaryAction{ a -> -(a as Double)})
            "+" -> Pair(DoubleType(), UnaryAction{ a -> a as Double})
            else -> super.getUnaryAction(op)
        }
    }

    fun Any.toDouble() : Double = if (this is Number) this.toDouble()
        else throw NumberFormatException("Невозможно привести к Double")

    override fun getBinaryAction(op: String, other: Type): Pair<Type, BinaryAction> {
        if (other.kind == BaseType.Int || other.kind == BaseType.Double)
            return when (op) {
                "+" -> Pair(DoubleType(),   BinaryAction{ a, b -> a.toDouble()+b.toDouble() })
                "-" -> Pair(DoubleType(),   BinaryAction{ a, b -> a.toDouble()-b.toDouble() })
                "*" -> Pair(DoubleType(),   BinaryAction{ a, b -> a.toDouble()*b.toDouble() })
                "/" -> Pair(DoubleType(),   BinaryAction{ a, b -> a.toDouble()/b.toDouble() })
                "^" -> Pair(DoubleType(),   BinaryAction{ a, b -> a.toDouble().pow(b.toDouble()) })
                "%" -> Pair(DoubleType(),   BinaryAction{ a, b -> a.toDouble()%b.toDouble() })
                ">" -> Pair(BooleanType(),  BinaryAction{ a, b -> a.toDouble()>b.toDouble() })
                "<" -> Pair(BooleanType(),  BinaryAction{ a, b -> a.toDouble()<b.toDouble() })
                ">=" -> Pair(BooleanType(), BinaryAction{ a, b -> a.toDouble()>=b.toDouble() })
                "<=" -> Pair(BooleanType(), BinaryAction{ a, b -> a.toDouble()<=b.toDouble() })
                "==" -> Pair(BooleanType(), BinaryAction{ a, b -> a.toDouble()==b.toDouble() })
                "!=" -> Pair(BooleanType(), BinaryAction{ a, b -> a.toDouble()!=b.toDouble() })
                else -> super.getBinaryAction(op, other)
            }
        return super.getBinaryAction(op, other)
    }

}