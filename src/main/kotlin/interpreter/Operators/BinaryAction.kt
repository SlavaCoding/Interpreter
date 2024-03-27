package interpreter.Operators

fun interface BinaryAction<T, U, R> {
    fun apply(a: T, b: U): R
}