package interpreter

import interpreter.operators.Operator

class Expression(var expr: Operator, val globalVariableTable: VariableTable) {
    val result: Any
        get() = expr.getValue()
}