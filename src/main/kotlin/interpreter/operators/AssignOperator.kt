package interpreter.operators

import interpreter.typing.Type

class AssignOperator(val variable: VariableOperator, val expr: Operator) : Operator(Type(Type.BaseType.Unit)) {
    override fun getValue(): Any {
        variable.setValue(expr.getValue())
        return Unit
    }
}