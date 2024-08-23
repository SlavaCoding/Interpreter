package interpreter

import interpreter.operators.*
import interpreter.typing.*


class Parser {
    private lateinit var lexer: Lexer
    private lateinit var currentVariableTable: VariableTable

    private fun parseBinaryOperation(nextLayer: () -> Operator, vararg operators: String): Operator {
        var leftOp = nextLayer()
        while (operators.any { lexer.currentToken.isType(it) }) {
            val op = lexer.currentToken.type
            lexer.index++
            val rightOp = nextLayer()
            val (returnType, action) = leftOp.returnType.getBinaryAction(op, rightOp.returnType)
            leftOp = BinaryOperator(action, leftOp, rightOp, returnType)
        }
        return leftOp
    }

    private fun parseInverseOperation(nextLayer: () -> Operator, operator: String): Operator {
        var inverseCount = 0
        while (lexer.currentToken.isType(operator)){
            lexer.index++
            inverseCount++
        }
        if (inverseCount % 2 == 1){
            val op = nextLayer()
            val (returnType, action) = op.returnType.getUnaryAction(operator)
            return UnaryOperator(action, op, returnType)
        }
        else return nextLayer()
    }

    private fun valueLayer(): Operator {
        val value: Operator
        if (lexer.currentToken.isType("id")) {
            val id = lexer.currentToken.value as String
            if (id == "PI") value = ValueOperator(Math.PI, DoubleType())
                //
            else if (id == "E") value = ValueOperator(Math.E, DoubleType())
            else if (id == "true") value = ValueOperator(true, BooleanType())
            else if (id == "false") value = ValueOperator(false, BooleanType())
            else {
                val (_, type) = currentVariableTable.findVariable(id)
                value = VariableOperator(id, currentVariableTable, type)
            }
            //else
        }
        else if (lexer.currentToken.isType("int")){
            value = ValueOperator(lexer.currentToken.value as Int, IntType())
        }
        else if (lexer.currentToken.isType("double")){
            value = ValueOperator(lexer.currentToken.value as Double, DoubleType())
        }
        else {
            throw Exception("Синтаксическая ошибка. Операнд не распознан: " + lexer.currentToken.type)
        }
        lexer.index++
        return value
    }
    private fun bracketLayer(): Operator {
        if (lexer.currentToken.isType("(")) {
            lexer.index++
            val brackets = orLayer()
            if (!lexer.currentToken.isType(")")) {
                throw Exception("Ошибка: ожидался символ ')', найден:  " + lexer.currentToken.type +
                        "Проверьте правильность расстановки скобок")
            }
            lexer.index++
            return brackets
        }
        else return valueLayer()
    }

    private fun powLayer(): Operator = parseBinaryOperation(::bracketLayer, "^")
    private fun unaryMinusLayer(): Operator = parseInverseOperation(:: powLayer, "-")
    private fun mulLayer(): Operator = parseBinaryOperation(::unaryMinusLayer, "*", "/", "%")
    private fun sumLayer(): Operator = parseBinaryOperation(::mulLayer, "+", "-")

    private fun compareLayer(): Operator {
        var leftOp = sumLayer()
        val operators = listOf(">", "<", ">=", "<=", "==", "!=")
        if ( operators.any { lexer.currentToken.isType(it)} ) {
            var op = lexer.currentToken.type
            lexer.index++
            var rightOp = sumLayer()
            val (returnType, action) = leftOp.returnType.getBinaryAction(op, rightOp.returnType)
            leftOp = BinaryOperator(action, leftOp, rightOp, returnType)
            while ( operators.any { lexer.currentToken.isType(it)} ) {
                op = lexer.currentToken.type
                lexer.index++
                var nextOp = rightOp
                rightOp = sumLayer()
                val (nextReturnType, nextAction) = nextOp.returnType.getBinaryAction(op, rightOp.returnType)
                nextOp = BinaryOperator(nextAction, nextOp, rightOp, nextReturnType)
                leftOp = BinaryOperator({ a, b -> (a as Boolean) && (b as Boolean) }, leftOp, nextOp, BooleanType())
            }
        }
        return leftOp
    }

    private fun notLayer(): Operator = parseInverseOperation(::compareLayer, "!")
    private fun andLayer(): Operator = parseBinaryOperation(::notLayer, "&&")
    private fun orLayer(): Operator = parseBinaryOperation(::andLayer, "||")

    fun eval(expr: String, varTable: HashMap<String, Pair<Any, Type>>): Expression {
        lexer = Lexer(expr)
        currentVariableTable = VariableTable(table = varTable)
        return Expression(orLayer(), currentVariableTable)
    }
    fun eval(expr: String): Expression {
        return eval(expr, HashMap())
    }

}