package interpreter.variables

import interpreter.typing.Type

data class Variable(var value: Any, val type: Type, val isConstant: Boolean)