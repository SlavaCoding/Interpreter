package interpreter.typing

import interpreter.operators.BinaryAction
import interpreter.operators.UnaryAction
import kotlin.math.pow

class BooleanType : Type(BaseType.Boolean) {
    override fun getUnaryAction(op: String): Pair<Type, UnaryAction> {
        return when (op) {
            "!" -> Pair(BooleanType(), UnaryAction{ a -> !(a as Boolean)})
            else -> super.getUnaryAction(op)
        }
    }

    override fun getBinaryAction(op: String, other: Type): Pair<Type, BinaryAction> {
        if (other.kind == BaseType.Boolean)
            return when (op) {
                "&&" -> Pair(BooleanType(), BinaryAction{ a, b -> (a as Boolean) && (b as Boolean) })
                "||" -> Pair(BooleanType(), BinaryAction{ a, b -> (a as Boolean) || (b as Boolean) })
                else -> super.getBinaryAction(op, other)
            }
        return super.getBinaryAction(op, other)
    }

}