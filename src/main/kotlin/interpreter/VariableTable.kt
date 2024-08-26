package interpreter

import interpreter.typing.Type
import interpreter.typing.Type.BaseType


class VariableTable(val parentTable: VariableTable? = null,
                    val table: HashMap<String, Pair<Any, Type>> = HashMap()) {
    fun findVariable(name: String): Pair<Any, Type> {
        val variable = table[name]
        variable?.let {
            return it
        }
        parentTable?.let {
            return it.findVariable(name)
        }
        throw RuntimeException("Синтаксическая ошибка: неизвестный идентификатор: $name")
    }

    fun addVariable(name: String, type: Type){
        val default = when (type.kind) {
            BaseType.Int -> 0
            BaseType.Double -> 0.0
            BaseType.Boolean -> false
            BaseType.Any -> Unit
            BaseType.Unit -> Unit
        }
        table[name] = Pair(default, type)
    }

    fun setVariable(name: String, value: Any){
        val variable = table[name]
        if (variable!=null){
            val (_, type) = variable
            table[name] = Pair(value, type)
        }
        else if (parentTable!=null){
            parentTable.setVariable(name, value)
        }
        else throw RuntimeException("Синтаксическая ошибка: неизвестный идентификатор: $name")
    }
}