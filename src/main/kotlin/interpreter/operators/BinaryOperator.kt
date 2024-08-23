package interpreter.operators

import interpreter.typing.Type

/** Хранит два операнда типа T и U, возвращает значение типа R  */
class BinaryOperator
/**
 * @param action Действие, производимое над операндами
 * @param left Хранимый в операторе операнд
 * @param right Хранимый в операторе операнд
 */(var action: BinaryAction, var left: Operator, var right: Operator, returnType: Type) : Operator(returnType) {
    override fun getValue(): Any {
        return action.apply(left.getValue(), right.getValue())
    }
}