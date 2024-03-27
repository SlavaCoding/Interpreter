package interpreter.Operators

fun interface DoubleOpSupplier {
    fun getOperator(): Operator<Double?>?
}