package interpreter.operators

import interpreter.typing.Type
import interpreter.typing.Type.BaseType

class RegisterId() : Operator(Type(BaseType.Unit)) {
    override fun getValue(): Any {

        return Unit
    }
}