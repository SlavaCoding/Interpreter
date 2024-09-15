package interpreter

import interpreter.operators.*
import interpreter.tokens.Token.TokenType
import interpreter.typing.*
import interpreter.variables.Variable
import interpreter.variables.VariableTable


class Parser {
    private lateinit var lexer: Lexer
    private lateinit var currentVariableTable: VariableTable

    private fun parseBinaryOperation(nextLayer: () -> Operator, vararg operators: String): Operator {
        var leftOp = nextLayer()
        while (operators.any { lexer.currentToken.value == it }) {
            val op = lexer.currentToken.value as String
            lexer.index++
            val rightOp = nextLayer()
            val (returnType, action) = leftOp.returnType.getBinaryAction(op, rightOp.returnType)
            leftOp = BinaryOperator(action, leftOp, rightOp, returnType)
        }
        return leftOp
    }

    private fun parseInverseOperation(nextLayer: () -> Operator, operator: String): Operator {
        var inverseCount = 0
        while (lexer.currentToken.value == operator){
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

    private fun getId(): VariableOperator {
        val id = lexer.currentToken.value as String
        val variable = currentVariableTable.findVariable(id)
        return VariableOperator(id, currentVariableTable, variable.type)
    }

    private fun valueLayer(): Operator {
        val value: Operator = when (lexer.currentToken.type) {
            TokenType.KeyWord -> {
                val id = lexer.currentToken.value as String
                when (id) {
                    "true" -> ValueOperator(true, BooleanType())
                    "false" -> ValueOperator(false, BooleanType())
                    else -> throw Exception("Ключевое слово не может быть использовано в качестве имени переменной")
                }
            }
            TokenType.Id -> getId()
            TokenType.Int -> ValueOperator(lexer.currentToken.value as Int, IntType())
            TokenType.Double -> ValueOperator(lexer.currentToken.value as Double, DoubleType())
            else -> throw Exception("Синтаксическая ошибка. Операнд не распознан: ${lexer.currentToken.type} ${lexer.currentToken.value}")
        }
        lexer.index++
        return value
    }
    private fun bracketLayer(): Operator {
        if (lexer.currentToken.value == "(") {
            lexer.index++
            val brackets = orLayer()
            if (lexer.currentToken.value != ")") {
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
        if ( operators.any { lexer.currentToken.value == it } ) {
            var op = lexer.currentToken.value as String
            lexer.index++
            var rightOp = sumLayer()
            val (returnType, action) = leftOp.returnType.getBinaryAction(op, rightOp.returnType)
            leftOp = BinaryOperator(action, leftOp, rightOp, returnType)
            while ( operators.any { lexer.currentToken.value == it} ) {
                op = lexer.currentToken.value as String
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

//    private fun registerId(isConstant: Boolean): Operator {
//        lexer.index++
//        val name: String
//        val type: Type
//        if (lexer.currentToken.type == TokenType.Id) {
//            name = lexer.currentToken.value as String
//            lexer.index++
//        }
//        else throw Exception("Имя переменной не найдено")
//        if (lexer.currentToken.value == ":"){
//            lexer.index++
//            type = when (lexer.currentToken.value) {
//                "Int" -> IntType()
//                "Double" -> DoubleType()
//                "Boolean" -> BooleanType()
//                else -> throw Exception()
//            }
//        }
//        else if (lexer.currentToken.value == "=") {
//            lexer.index++
//            val expr = orLayer()
//            type = expr.returnType
//            currentVariableTable.addVariable(name, Variable(, type, isConstant))
//            return AssignOperator(variable, expr)
//        }
//    }

    private fun blockLayer(): Operator {
        val operators = ArrayList<Operator>()
        while (lexer.currentToken.type != TokenType.EOF) {  // TODO
//            if (lexer.currentToken.type == TokenType.KeyWord)
//                when(lexer.currentToken.value) {
//                    "val" -> registerId(true)
//                    "var" -> registerId(false)
//                }
//            else
                if (lexer.currentToken.type == TokenType.Id && (lexer.getRelativeToken(1).value == "=")){
                val variable = getId()
                lexer.index+=2
                operators.add(AssignOperator(variable, orLayer()))
            }
            else operators.add(orLayer())
        }
        return BlockOperator(operators, operators.last().returnType)
    }

    fun eval(expr: String, varTable: HashMap<String, Variable>): Expression {
        lexer = Lexer(expr)
        currentVariableTable = VariableTable(table = varTable)
        return Expression(blockLayer(), currentVariableTable)
    }
    fun eval(expr: String): Expression {
        return eval(expr, HashMap())
    }

}