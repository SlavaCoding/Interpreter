package interpreter

import interpreter.operators.Operator
import interpreter.variables.VariableTable

class Expression(var expr: Operator, val globalVariableTable: VariableTable) {
    val result: Any
        get() = expr.getValue()
}