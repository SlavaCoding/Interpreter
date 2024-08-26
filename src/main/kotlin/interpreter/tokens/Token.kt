package interpreter.tokens

/** Класс используется для хранения информации о частях выражения  */
open class Token(
    /** Хранит тип токена или произвольный символ  */
    val type: TokenType,
    /** Хранит дополнительное значение  */
    val value: Any?
) {
    enum class TokenType {
        Int, Double, Id, KeyWord, Logic, Symbol, EOF
    }
}