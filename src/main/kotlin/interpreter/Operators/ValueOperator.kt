package interpreter.Operators

/** Хранит константное значение  */
class ValueOperator<T>
/** @param value Хранимое значение
 */(val constVal: T) : Operator<T>(){
    override fun getValue(): T {
        return constVal
    }
 }