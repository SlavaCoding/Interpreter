//package Interpreter
//
//import Interpreter.Operators.*
//import Interpreter.Tokens.Token
//import kotlin.math.abs
//import kotlin.math.cos
//import kotlin.math.sin
//import kotlin.math.tan
//
//class Parser {
//    private var lexer: Lexer? = null
//    private var currToken: Token? = null
//
//    private val sumMap = HashMap<String, BinaryAction<Double, Double, Double>>()
//    private val mulMap = HashMap<String, BinaryAction<Double?, Double, Double>>()
//    private val powMap = HashMap<String, BinaryAction<Double, Double, Double>>()
//    private val funcMap = HashMap<String?, UnaryAction<Double, Double>>()
//    private val arithmetic = DoubleOpSupplier {
//        universalLayer(
//            sumMap,
//            DoubleOpSupplier {
//                universalLayer(mulMap) {
//                    universalLayer(
//                        powMap,
//                        DoubleOpSupplier { this.valueLayer() })
//                }
//            })
//    }
//
//    init {
//        sumMap["+"] = BinaryAction { a: Double, b: Double -> java.lang.Double.sum(a, b) }
//        sumMap["-"] = BinaryAction { a: Double, b: Double -> a - b }
//        mulMap["*"] = BinaryAction { a: Double?, b: Double -> a!! * b }
//        mulMap["/"] = BinaryAction { a: Double?, b: Double -> a!! / b }
//        powMap["^"] = BinaryAction<Double, Double, Double> { a: Double, b: Double -> a.pow(b) }
//        funcMap["abs"] = UnaryAction { a: Double -> abs(a) }
//        funcMap["cos"] = UnaryAction { a: Double -> cos(a) }
//        funcMap["sin"] = UnaryAction { a: Double -> sin(a) }
//        funcMap["tan"] = UnaryAction { a: Double -> tan(a) }
//        funcMap["rad"] = UnaryAction { angdeg: Double -> Math.toRadians(angdeg) }
//    }
//
//    private fun valueLayer(): Operator<Double?>? {
//        currToken = lexer!!.nextToken
//
//        if (currToken!!.isType("-")) {
//            return Interpreter.Operators.UnaryOperator<Double?, Double>(
//                UnaryAction { a: Double? -> -a!! },
//                valueLayer()
//            )!!
//        } else if (currToken!!.isType("(")) {
//            val brackets = arithmetic.getOperator()
//            if (!currToken!!.isType(")")) {
//                throw RuntimeException("Ошибка: ожидался символ ')'. Проверьте правильность расстановки скобок")
//            }
//            currToken = lexer!!.nextToken
//            return brackets
//        } else if (currToken!!.isType("id")) {
//            val id = currToken!!.value as String?
//            val `val`: Operator<Double?>
//            if (id == "PI") `val` = ValueOperator(Math.PI)
//            else if (id == "E") `val` = ValueOperator(Math.E)
//            else {
//                currToken = lexer!!.nextToken
//                `val` = Interpreter.Operators.UnaryOperator<Double?, Double>(funcMap[id], arithmetic.getOperator())!!
//            }
//            currToken = lexer!!.nextToken
//            return `val`
//        } else {
//            val `val`: Operator<Double?> = ValueOperator(currToken!!.value as Double)
//            currToken = lexer!!.nextToken
//            return `val`
//        }
//    }
//
//    private fun universalLayer(
//        map: HashMap<String, BinaryAction<Double?, Double, Double>>,
//        op: DoubleOpSupplier
//    ): Operator<Double?>? {
//        var leftOp = op.getOperator()
//        while (map.containsKey(currToken!!.type)) {
//            leftOp = Interpreter.Operators.BinaryOperator<Double?, Double?, Double>(
//                map[currToken!!.type],
//                leftOp,
//                op.getOperator()
//            )!!
//        }
//        return leftOp
//    }
//
//    fun eval(expr: String?): Expression {
//        lexer = Lexer(expr!!)
//        return Expression(arithmetic.getOperator())
//    } //    private Operator<?> compareLayer(){
//    //        Operator<Double> leftOp = sumLayer();
//    //        Operator<?> result = leftOp;
//    //        BinaryAction<Double, Double, Boolean> action;
//    //        if (currToken.isType("logic")&&currToken.getValue()!="&"&&currToken.getValue()!="|"&&currToken.getValue()!="!"){
//    //            if (currToken.isType(">")){
//    //                action = (a, b) -> a > b;
//    //            }
//    //            else{
//    //                action = (a, b) -> a < b;
//    //            }
//    //            result = new BinaryOperator<>(action, leftOp, sumLayer());
//    //        }
//    //        return result;
//    //    }
//    //    private Operator<Boolean> andLayer(){
//    //        Operator<Boolean> leftOp = compareLayer();
//    //        BinaryAction<Boolean, Boolean, Boolean> action;
//    //        while (currToken.isType("&")||currToken.isType("|")){
//    //            if (currToken.isType("&")){
//    //                action = (a, b) -> a && b;
//    //            }
//    //            else{
//    //                action = (a, b) -> a || b;
//    //            }
//    //            leftOp = new BinaryOperator<>(action, leftOp, compareLayer());
//    //        }
//    //        return leftOp;
//    //    }
//}