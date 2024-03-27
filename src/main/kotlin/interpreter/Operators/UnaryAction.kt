package interpreter.Operators

fun interface UnaryAction<T, R> {
    fun apply(d: T): R
}