package interpreter

import interpreter.tokens.Token
import java.util.function.BooleanSupplier

/** Лексический анализатор. Разбивает строку на части (токены)  */
class Lexer(txt: String) {
    /** Анализируемая текстовая строка  */
    private var text: String? = null

    /** Позиция текущего символа  */
    private var pos = 0

    /** Текущий символ  */
    private var currentChar = 0.toChar()

    /** Допустимый набор символов для числа  */
    private val numFilter = BooleanSupplier { Character.isDigit(currentChar) || currentChar == '.' }

    /** Допустимый набор символов для идентификатора  */
    private val idFilter = BooleanSupplier { Character.isLetter(currentChar) }
    private val logicChars: Set<Char> = HashSet(mutableListOf('>', '<', '=', '!', '&', '|'))
    private val logicFilter = BooleanSupplier { logicChars.contains(currentChar) }

    /** Список всех токенов */
    val tokens = ArrayList<Token>()
    /** Номер текущего токена */
    var index = 0
        set(value){
            if(value >= 0 && value < tokens.size){
                field = value
            }
        }
    /** Текущий токен */
    val currentToken: Token
        get() = tokens[index]
    /**
     * Констуктор класса [Lexer]
     * @param txt Анализируемая текстовая строка
     */
    init {
        if (txt.isEmpty()) {
            currentChar = '\u0000'
        } else {
            text = txt
            currentChar = txt[0]
            pos = 0
            do {
                val curr = nextToken()
                tokens.add(curr)
            } while (!curr.isType("EOF"))
        }
    }

    /** Считывает следующий символ  */
    private fun nextChar() {
        if (pos < text!!.length - 1) {
            pos++
            currentChar = text!![pos]
        } else currentChar = '\u0000'
    }

    /**
     * Метод считывает часть строки, начиная с текущего, пока символы удовлетворяют фильтру
     * @param filter Фильтр символов
     * @return Возвращает результат в виде строки
     */
    private fun getTokenValue(filter: BooleanSupplier): String {
        val strNum = StringBuilder()
        while (filter.asBoolean) {
            strNum.append(currentChar)
            nextChar()
        }
        return strNum.toString()
    }

    /**
     * @return Возвращает следующий токен
     * @throws NumberFormatException Возникает при некорректном формате числа в строке
     */
    fun nextToken(): Token {
        while (currentChar != '\u0000') {
            if (Character.isSpaceChar(currentChar)) {
                nextChar()
            } else if (Character.isDigit(currentChar)) {
                val number = getTokenValue(numFilter)
                return if (number.contains('.')){
                    Token("double", number.toDouble())
                } else Token("int", number.toInt())
            } else if (Character.isLetter(currentChar)) {
                return Token("id", getTokenValue(idFilter))
            } else if (logicFilter.asBoolean) {
                return Token(getTokenValue(logicFilter), null)
            } else {
                val token = Token(currentChar.toString(), null)
                nextChar()
                return token
            }
        }
        currentChar = text?.get(0) ?: '\u0000'
        pos = 0
        return Token("EOF", null)
    }
}