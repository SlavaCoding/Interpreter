package interpreter

import interpreter.operators.*
import interpreter.tokens.Token


class Parser {
    private lateinit var lexer: Lexer
    private lateinit var currToken: Token
    private val semanticTable = SemanticTable()


    init {

    }

    private fun valueLayer(): Operator {
        val value: Operator
        if (currToken.isType("id")) {
            val id = currToken.value as String
            if (id == "PI") value = ValueOperator(Math.PI, Type.Double)
                //if (id == "E")
            else     value = ValueOperator(Math.E, Type.Double)
//            else {
//                currToken = lexer.nextToken()
//                value = UnaryOperator(funcMap[id], arithmetic.getOperator())
//            }

        }
        else if (currToken.isType("int")){
            value = ValueOperator(currToken.value as Int, Type.Int)
        }
        else {
            value = ValueOperator(currToken.value as Double, Type.Double)
        }
        currToken = lexer.nextToken()
        return value
    }
    private fun bracketLayer(): Operator {
        if (currToken.isType("(")) {
            val brackets = unaryMinusLayer()
            if (!currToken.isType(")")) {
                throw RuntimeException("Ошибка: ожидался символ ')'. Проверьте правильность расстановки скобок")
            }
            currToken = lexer.nextToken()
            return brackets
        }
        else return valueLayer()
    }
    private fun unaryMinusLayer(): Operator {
        currToken = lexer.nextToken()
        if (currToken.isType("-")){
            val op = unaryMinusLayer()
            val (returnType, action) = semanticTable.getUnaryAction("-", op)
            return UnaryOperator(action, op, returnType)
        }
        else return bracketLayer()
    }
//    private fun universalLayer(
//        map: HashMap<String, BinaryAction<Double?, Double, Double>>,
//        op: DoubleOpSupplier
//    ): Operator<Double?>? {
//        var leftOp = op.getOperator()
//        while (map.containsKey(currToken.type)) {
//            leftOp = Interpreter.Operators.BinaryOperator<Double?, Double?, Double>(
//                map[currToken.type],
//                leftOp,
//                op.getOperator()
//            )!!
//        }
//        return leftOp
//    }

//    private fun sumLayer(): Operator{
//        var leftOp = valueLayer()
//        var rightOp = valueLayer()
//        leftOp = BinaryOperator(
//            table.binaryOpTable[Triple("+", getTypeOperator(leftOp))]
//        )
//        return leftOp
//    }

    fun eval(expr: String): Expression {
        lexer = Lexer(expr)

        return Expression(unaryMinusLayer())
    }
    //    private Operator<?> compareLayer(){
    //        Operator<Double> leftOp = sumLayer();
    //        Operator<?> result = leftOp;
    //        BinaryAction<Double, Double, Boolean> action;
    //        if (currToken.isType("logic")&&currToken.getValue()!="&"&&currToken.getValue()!="|"&&currToken.getValue()!="!"){
    //            if (currToken.isType(">")){
    //                action = (a, b) -> a > b;
    //            }
    //            else{
    //                action = (a, b) -> a < b;
    //            }
    //            result = new BinaryOperator<>(action, leftOp, sumLayer());
    //        }
    //        return result;
    //    }
    //    private Operator<Boolean> andLayer(){
    //        Operator<Boolean> leftOp = compareLayer();
    //        BinaryAction<Boolean, Boolean, Boolean> action;
    //        while (currToken.isType("&")||currToken.isType("|")){
    //            if (currToken.isType("&")){
    //                action = (a, b) -> a && b;
    //            }
    //            else{
    //                action = (a, b) -> a || b;
    //            }
    //            leftOp = new BinaryOperator<>(action, leftOp, compareLayer());
    //        }
    //        return leftOp;
    //    }
}