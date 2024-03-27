package interpreter

import interpreter.Operators.Operator

class Expression(var expr: Operator<*>) {
    val result: Any?
        get() = expr.getValue()
}