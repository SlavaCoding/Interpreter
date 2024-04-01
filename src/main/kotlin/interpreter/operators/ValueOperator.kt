package interpreter.operators

import interpreter.Type

/** Хранит константное значение  */
class ValueOperator
/** @param value Хранимое значение
 */(val constVal: Any, returnType: Type) : Operator(returnType){
    override fun getValue(): Any {
        return constVal
    }
 }