package interpreter.Operators

/** Хранит два операнда типа T и U, возвращает значение типа R  */
class BinaryOperator<T, U, R>
/**
 * @param action Действие, производимое над операндами
 * @param left Хранимый в операторе операнд
 * @param right Хранимый в операторе операнд
 */(var action: BinaryAction<T, U, R>, var left: Operator<T>, var right: Operator<U>) : Operator<R>() {
    override fun getValue(): R {
        return action.apply(left.getValue(), right.getValue())
    }
}