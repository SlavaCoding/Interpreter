package interpreter.operators

fun interface UnaryAction {
    fun apply(d: Any): Any
}