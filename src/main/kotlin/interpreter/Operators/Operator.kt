package interpreter.Operators

/** Базовый класс для всех операторов  */
abstract class Operator<T> {
    /** Возвращает вычисленное оператором значение  */
    abstract fun getValue(): T
}