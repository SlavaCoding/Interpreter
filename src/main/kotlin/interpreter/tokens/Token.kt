package interpreter.tokens

/** Класс используется для хранения информации о частях выражения  */
open class Token(
    /** Хранит тип токена или произвольный символ  */
    val type: String,
    /** Хранит дополнительное значение  */
    val value: Any?
) {
    /** @return Возвращает тип токена
     */
    /** Проверяет соответствие токена определенному значению типа  */
    fun isType(type: String): Boolean {
        return this.type == type
    }
}