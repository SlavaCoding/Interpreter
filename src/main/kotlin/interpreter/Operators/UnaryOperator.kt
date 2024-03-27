package interpreter.Operators

/** Хранит операнд типа T, возвращает значение типа R  */
class UnaryOperator<T, R>
/**
 * @param action Действие, производимое над операндом
 * @param operand Хранимый в операторе операнд
 */(var action: UnaryAction<T, R>, var operand: Operator<T>) : Operator<R>() {
    override fun getValue(): R {
        return action.apply(operand.getValue())
    }
}