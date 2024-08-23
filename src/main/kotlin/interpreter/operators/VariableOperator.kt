package interpreter.operators

import interpreter.typing.Type
import interpreter.VariableTable

class VariableOperator
/** @param value Хранимое значение
 */(val varName: String, val varTable: VariableTable, returnType: Type) : Operator(returnType) {
    override fun getValue(): Any {
        val (value, _) = varTable.findVariable(varName)
        return value
    }
    fun setValue(value: Any) {
        varTable.setVariable(varName, value)
    }
}