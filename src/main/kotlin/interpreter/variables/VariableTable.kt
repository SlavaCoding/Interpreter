package interpreter.variables

import interpreter.typing.Type

class VariableTable(val parentTable: VariableTable? = null,
                    val table: HashMap<String, Variable> = HashMap()) {
    fun findVariable(name: String): Variable {
        val variable = table[name]
        variable?.let {
            return it
        }
        parentTable?.let {
            return it.findVariable(name)
        }
        throw RuntimeException("Синтаксическая ошибка: неизвестный идентификатор: $name")
    }

    fun addVariable(name: String, variable: Variable){
        table[name] = variable
    }

    fun setVariable(name: String, value: Any){
        val variable = table[name]
        if (variable!=null){
            variable.value = value
            //table[name] = Pair(value, type)
        }
        else if (parentTable!=null){
            parentTable.setVariable(name, value)
        }
        else throw RuntimeException("Синтаксическая ошибка: неизвестный идентификатор: $name")
    }
}