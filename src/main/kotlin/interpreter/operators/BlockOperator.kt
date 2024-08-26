package interpreter.operators

import interpreter.typing.Type

class BlockOperator(val operators: List<Operator>, returnType: Type) : Operator(returnType) {
    override fun getValue(): Any {
        var result: Any = Unit
        for (operator in operators) {
            result = operator.getValue()
        }
        return result
    }

}