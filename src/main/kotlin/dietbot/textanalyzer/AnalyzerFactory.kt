package dietbot.textanalyzer

import java.lang.Error

object AnalyzerFactory {
    private val OPERATORS = setOf("+", "-", "show", "%")

    fun matchAndCreate(text: String?): Analyzer {
        val tokens = text?.split(" ") ?: emptyList()

        val operation = tokens[0]

        if (!checkOperator(operation)) throw Error("Illegal operation!")

        return when (operation) {
            "+" -> AddAnalyzer(tokens[1], tokens.getOrElse(2) {""} )
            "show" -> ShowAnalyzer(tokens[1], tokens.getOrElse(2) {null})
            else -> AddAnalyzer("", "")
        }
    }

    private fun checkOperator(operator: String): Boolean {
        return OPERATORS.contains(operator)
    }
}