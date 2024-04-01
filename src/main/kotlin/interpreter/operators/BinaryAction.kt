package interpreter.operators

fun interface BinaryAction {
    fun apply(a: Any, b: Any): Any
}