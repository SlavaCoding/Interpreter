package interpreter.typing

import interpreter.operators.BinaryAction
import interpreter.operators.UnaryAction

abstract class Type (val kind: BaseType) {
    enum class BaseType {
        Int, Double, Boolean
    }

    open fun getUnaryAction(op: String): Pair<Type, UnaryAction> {
        throw Exception("Семантическая ошибка. Тип ${this.kind} не реализовал унарную операцию $op")
    }

    open fun getBinaryAction(op: String, other: Type) : Pair<Type, BinaryAction> {
        throw Exception("Семантическая ошибка. Тип ${this.kind} не реализовал бинарную операцию $op с типом ${other.kind}")
    }
}