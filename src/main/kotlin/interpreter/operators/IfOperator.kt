package interpreter.operators

import interpreter.typing.Type

class IfOperator(val condition: Operator, ifPart: Operator, elsePart: Operator?, returnType: Type) : Operator(returnType) {
    override fun getValue(): Any {
        TODO("Not yet implemented")
    }
}