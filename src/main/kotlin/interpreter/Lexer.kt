package interpreter

import interpreter.tokens.Token
import interpreter.tokens.Token.TokenType
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

    val logic = listOf(">", "<", ">=", "<=", "==", "!=", "!", "&&", "||")
    val keyWords = listOf("true", "false",
        "val", "var", "Int", "Double", "Boolean",
        "if", "else", "for")

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
            } while (curr.type != TokenType.EOF)
        }
    }

    fun getRelativeToken(offset: Int) : Token {
        val i = index + offset
        return if ( i>= 0 && i<tokens.size) {
            tokens[i]
        }
        else Token(TokenType.EOF, null)
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
            if (Character.isSpaceChar(currentChar) || currentChar == '\n') {
                nextChar()
            } else if (Character.isDigit(currentChar)) {
                val number = getTokenValue(numFilter)
                return if (number.contains('.')){
                    Token(TokenType.Double, number.toDouble())
                } else Token(TokenType.Int, number.toInt())
            } else if (Character.isLetter(currentChar)) {
                val value = getTokenValue(idFilter)
                val type = if (keyWords.contains(value)) TokenType.KeyWord else TokenType.Id
                return Token(type, value)
            } else if (logicFilter.asBoolean) {
                val value = getTokenValue(logicFilter)
                val type = if (logic.contains(value)) TokenType.Logic else TokenType.Symbol
                return Token(type, value)
            } else {
                val token = Token(TokenType.Symbol, currentChar.toString())
                nextChar()
                return token
            }
        }
        currentChar = text?.get(0) ?: '\u0000'
        pos = 0
        return Token(TokenType.EOF, null)
    }
}