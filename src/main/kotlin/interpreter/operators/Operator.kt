package interpreter.operators

import interpreter.typing.Type

/** Базовый класс для всех операторов  */
abstract class Operator(
    val returnType: Type
) {
    /** Возвращает вычисленное оператором значение  */
    abstract fun getValue(): Any
}