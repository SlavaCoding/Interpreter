package interpreter.operators

import interpreter.typing.Type

/** Хранит операнд типа T, возвращает значение типа R  */
class UnaryOperator
/**
 * @param action Действие, производимое над операндом
 * @param operand Хранимый в операторе операнд
 */(var action: UnaryAction, var operand: Operator, returnType: Type) : Operator(returnType) {
    override fun getValue(): Any {
        return action.apply(operand.getValue())
    }
}