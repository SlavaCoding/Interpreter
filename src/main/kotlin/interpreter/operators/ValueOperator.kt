package interpreter.operators

import interpreter.typing.Type

/** Хранит константное значение  */
class ValueOperator
/** @param constVal Хранимое значение
 */(val constVal: Any, returnType: Type) : Operator(returnType){
    override fun getValue(): Any {
        return constVal
    }
 }