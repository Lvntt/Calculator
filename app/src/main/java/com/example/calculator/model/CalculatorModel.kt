package com.example.calculator.model

import android.util.Log
import com.example.calculator.data.ButtonsSource
import com.example.calculator.data.CalculatorButton
import com.example.calculator.data.Constants.ERROR_MESSAGE
import java.math.BigDecimal
import java.math.RoundingMode

class CalculatorModel {

    private var currentExpression = EMPTY_STRING
    private var canResetExpression = false

    private val operationSigns = listOf(
        CalculatorButton.ADDITION.value[0],
        CalculatorButton.SUBTRACTION.value[0],
        CalculatorButton.DIVISION.value[0],
        CalculatorButton.MULTIPLICATION.value[0]
    )

    fun getButtons() = ButtonsSource.buttons

    fun handleDigitClick(button: CalculatorButton): String {
        val digit = button.value

        if (canResetExpression) {
            eraseExpression()
            canResetExpression = false
        }
        currentExpression += digit

        return currentExpression
    }

    fun handlePunctuationClick(button: CalculatorButton): String {
        val punctuationSign = button.value

        if (!expressionIsValid() || currentExpression.isEmpty()) {
            return currentExpression
        }

        val lastSymbol = currentExpression.last()

        if (currentExpression.contains(punctuationSign) && lastSymbol !in operationSigns) {
            val operationSignIndex =
                currentExpression.substring(1).indexOfAny(operationSigns.toCharArray())
            val expressionAfterOperationSign = currentExpression.substring(operationSignIndex + 1)
            val punctuationSignCanBeInserted =
                !expressionAfterOperationSign.contains(punctuationSign)

            if (expressionHasOperationSigns() && punctuationSignCanBeInserted) {
                canResetExpression = false
                currentExpression += punctuationSign
            }
        } else if (lastSymbol !in operationSigns) {
            canResetExpression = false
            currentExpression += punctuationSign
        }

        return currentExpression
    }

    fun handleOperationClick(button: CalculatorButton): String {
        val operation = button.value

        if (currentExpression.isEmpty()) {
            return currentExpression
        }

        if (operationSignCanBeReplaced()) {
            val chars = currentExpression.toCharArray()
            chars[chars.lastIndex] = operation[0]
            currentExpression = chars.joinToString(EMPTY_STRING)
            canResetExpression = false
        } else if (operationSignCanBeInserted()) {
            currentExpression += operation
            canResetExpression = false
        }

        return currentExpression
    }

    fun calculatePercent(): String {
        if (expressionCanBeCalculated()) {
            calculateResult()
        }

        if (!expressionIsValid() || currentExpression.isEmpty() || currentExpression.last() in operationSigns) {
            return currentExpression
        }

        val result = currentExpression.replace(COMMA, DOT).toDouble().toBigDecimal().divide(
            BigDecimal.valueOf(100.0)
        )
        currentExpression = processFractionalPart(result.toDouble())
        canResetExpression = true

        return currentExpression
    }

    fun changeSign(): String {
        if (!expressionIsValid()) {
            return currentExpression
        }
        currentExpression = if (currentExpression.startsWith(MINUS)) {
            currentExpression.removePrefix(MINUS)
        } else {
            currentExpression.replaceRange(0, 0, MINUS)
        }

        return currentExpression
    }

    fun eraseSymbol(): String {
        if (canResetExpression) {
            eraseExpression()
            canResetExpression = false
        } else if (currentExpression.isNotEmpty()) {
            currentExpression = currentExpression.substring(0, currentExpression.lastIndex)
        }

        return currentExpression
    }

    fun eraseExpression(): String {
        currentExpression = EMPTY_STRING
        return currentExpression
    }

    fun calculateResult(): String {
        val match = Regex(EXPRESSION_REGEX).find(currentExpression)

        if (match == null) {
            Log.d(TAG, NULL_MATCH_MESSAGE)
            return currentExpression
        }

        val (leftOperand, operation, rightOperand) = match.destructured
        val firstNumber = leftOperand.replace(COMMA, DOT).toDouble().toBigDecimal()
        val secondNumber = rightOperand.replace(COMMA, DOT).toDouble().toBigDecimal()
        var result: BigDecimal = BigDecimal.valueOf(0.0)
        when (operation) {
            CalculatorButton.SUBTRACTION.value -> result = firstNumber - secondNumber
            CalculatorButton.ADDITION.value -> result = firstNumber + secondNumber
            CalculatorButton.MULTIPLICATION.value -> result = firstNumber * secondNumber
            CalculatorButton.DIVISION.value -> {
                if (secondNumber == BigDecimal.valueOf(0.0)) {
                    currentExpression = EMPTY_STRING
                    throw ArithmeticException(ERROR_MESSAGE)
                } else {
                    result = firstNumber.divide(secondNumber, 9, RoundingMode.HALF_UP)
                }
            }
        }

        currentExpression = processFractionalPart(result.toDouble())
        canResetExpression = true

        return currentExpression
    }

    private fun expressionIsValid(): Boolean {
        return currentExpression.isNotEmpty() && currentExpression != ERROR_MESSAGE
    }

    // using substring(1) to ignore possible minus prefix
    private fun expressionHasOperationSigns(): Boolean {
        return currentExpression.substring(1).any { it in operationSigns }
    }

    private fun operationSignCanBeReplaced(): Boolean {
        val lastSymbol = currentExpression.last()
        return expressionIsValid() && currentExpression.lastIndex != 0 && lastSymbol in operationSigns
    }

    private fun operationSignCanBeInserted(): Boolean {
        val lastSymbol = currentExpression.last()
        return expressionIsValid() && !expressionHasOperationSigns() && lastSymbol != COMMA[0] && lastSymbol !in operationSigns
    }

    private fun expressionCanBeCalculated(): Boolean = currentExpression.contains(
        Regex(
            OPERATIONS_GROUP
        )
    )

    // discards the fractional part if it equals 0
    private fun processFractionalPart(number: Double): String {
        return if (BigDecimal.valueOf(number) % (BigDecimal.valueOf(1.0)) == BigDecimal.valueOf(0.0)) {
            number.toInt().toString().replace(DOT, COMMA)
        } else {
            number.toString().replace(DOT, COMMA)
        }
    }

    private companion object {
        const val TAG = "CALCULATOR"
        const val NULL_MATCH_MESSAGE = "Regex match is null"
        const val NUMBER = "\\d+"
        const val OPTIONAL_MINUS = "-?"
        const val OPTIONAL_FRACTIONAL_PART = "(?:[,.]$NUMBER)?"
        const val FIRST_OPERAND_GROUP = "($OPTIONAL_MINUS$NUMBER$OPTIONAL_FRACTIONAL_PART)"
        const val SECOND_OPERAND_GROUP = "($OPTIONAL_MINUS$NUMBER$OPTIONAL_FRACTIONAL_PART)"
        const val OPERATIONS_GROUP = "([-+×÷])"
        const val EXPRESSION_REGEX =
            "$FIRST_OPERAND_GROUP$OPERATIONS_GROUP$SECOND_OPERAND_GROUP"
        const val EMPTY_STRING = ""
        const val MINUS = "-"
        const val COMMA = ","
        const val DOT = "."
    }
}