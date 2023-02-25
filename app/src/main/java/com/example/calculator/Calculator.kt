package com.example.calculator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.math.BigDecimal
import java.math.RoundingMode

class Calculator {

    private var _currentExpression: MutableLiveData<String> = MutableLiveData(EMPTY_STRING)
    val currentExpression: LiveData<String>
        get() = _currentExpression
    private val currentExpressionValue: String
        get() = requireNotNull(currentExpression.value)
    private var canResetExpression = false
    private val operationSigns = listOf(
        Operations.ADDITION.value,
        Operations.SUBTRACTION.value,
        Operations.DIVISION.value,
        Operations.MULTIPLICATION.value
    )

    private fun expressionIsValid(): Boolean {
        return currentExpressionValue.isNotEmpty() && currentExpressionValue != ERROR_MESSAGE
    }

    private fun expressionCanBeCalculated(): Boolean = currentExpressionValue.contains(Regex(OPERATIONS_GROUP))

    private fun operationSignCanBeReplaced(): Boolean {
        val lastSymbol = currentExpressionValue.last()
        return expressionIsValid() && currentExpressionValue.lastIndex != 0 && lastSymbol in operationSigns
    }

    private fun operationSignCanBeInserted(): Boolean {
        val lastSymbol = currentExpressionValue.last()
        return expressionIsValid() && !expressionHasOperationSigns() && lastSymbol != COMMA[0] && lastSymbol !in operationSigns
    }

    // using substring(1) to ignore possible minus prefix
    private fun expressionHasOperationSigns(): Boolean {
        return currentExpressionValue.substring(1).any { it in operationSigns }
    }

    // discards the fractional part if it equals 0
    private fun processFractionalPart(number: Double): String {
        return if (BigDecimal.valueOf(number) % (BigDecimal.valueOf(1.0)) == BigDecimal.valueOf(0.0)) {
            number.toInt().toString().replace(DOT, COMMA)
        } else {
            number.toString().replace(DOT, COMMA)
        }
    }

    fun handleDigitClick(digit: Digit) {
        if (canResetExpression) {
            eraseExpression()
            canResetExpression = false
        }
        _currentExpression.value += digit.value
    }

    fun handlePunctuationClick(punctuationSign: String) {
        if (!expressionIsValid() || currentExpressionValue.isEmpty()) return

        val lastSymbol = currentExpressionValue.last()

        if (currentExpressionValue.contains(punctuationSign) && lastSymbol !in operationSigns) {
            val operationSignIndex = currentExpressionValue.substring(1).indexOfAny(operationSigns.toCharArray())
            val expressionAfterOperationSign = currentExpressionValue.substring(operationSignIndex + 1)
            val punctuationSignCanBeInserted = !expressionAfterOperationSign.contains(punctuationSign)

            if (expressionHasOperationSigns() && punctuationSignCanBeInserted) {
                canResetExpression = false
                _currentExpression.value += punctuationSign
            }
        } else if (lastSymbol !in operationSigns) {
            canResetExpression = false
            _currentExpression.value += punctuationSign
        }
    }

    fun handleOperationClick(operation: Operations) {
        if (currentExpressionValue.isEmpty()) return
        if (operationSignCanBeReplaced()) {
            val chars = currentExpressionValue.toCharArray()
            chars[chars.lastIndex] = operation.value
            _currentExpression.value = chars.joinToString(EMPTY_STRING)
            canResetExpression = false
        } else if (operationSignCanBeInserted()) {
            _currentExpression.value += operation.value
            canResetExpression = false
        }
    }

    fun changeSign() {
        _currentExpression.value = if (currentExpressionValue.startsWith(MINUS)) {
            currentExpressionValue.removePrefix(MINUS)
        } else {
            currentExpressionValue.replaceRange(0, 0, MINUS)
        }
    }

    fun handleEraseButton() {
        if (canResetExpression) {
            eraseExpression()
            canResetExpression = false
        } else if (currentExpressionValue.isNotEmpty()) {
            _currentExpression.value = currentExpressionValue.substring(0, currentExpressionValue.lastIndex)
        }
    }

    fun eraseExpression() {
        _currentExpression.value = EMPTY_STRING
    }

    fun calculatePercent() {
        if (!expressionIsValid() || currentExpressionValue.isEmpty() || currentExpressionValue.last() in operationSigns) return

        if (expressionCanBeCalculated()) {
            calculateResult()
        }

        val result = currentExpressionValue.replace(COMMA, DOT).toDouble().toBigDecimal().divide(BigDecimal.valueOf(100.0))
        _currentExpression.value = processFractionalPart(result.toDouble())
        canResetExpression = true
    }

    fun calculateResult() {
        try {
            val match = Regex(EXPRESSION_REGEX).find(currentExpressionValue)
            if (match == null) {
                Log.d(TAG, NULL_MATCH_MESSAGE)
                return
            }
            val (leftOperand, operation, rightOperand) = match.destructured
            val firstNumber = leftOperand.replace(COMMA, DOT).toDouble().toBigDecimal()
            val secondNumber = rightOperand.replace(COMMA, DOT).toDouble().toBigDecimal()
            var result: BigDecimal = BigDecimal.valueOf(0.0)

            when (operation) {
                Operations.SUBTRACTION.value.toString() -> result = firstNumber - secondNumber
                Operations.ADDITION.value.toString() -> result = firstNumber + secondNumber
                Operations.MULTIPLICATION.value.toString() -> result = firstNumber * secondNumber
                Operations.DIVISION.value.toString() -> {
                    if (secondNumber == BigDecimal.valueOf(0.0)) {
                        _currentExpression.value = ERROR_MESSAGE
                        canResetExpression = true
                        return
                    } else {
                        result = firstNumber.divide(secondNumber, 9, RoundingMode.HALF_UP)
                    }
                }
            }
            _currentExpression.value = processFractionalPart(result.toDouble())
            canResetExpression = true

        } catch (e: Exception) {
            Log.d(TAG, e.toString())
        }
    }

    companion object {
        private const val TAG = "CALCULATOR"
        private const val NULL_MATCH_MESSAGE = "Regex match is null"
        private const val NUMBER = "\\d+"
        private const val OPTIONAL_MINUS = "-?"
        private const val OPTIONAL_FRACTIONAL_PART = "(?:[,.]$NUMBER)?"
        private const val FIRST_OPERAND_GROUP = "($OPTIONAL_MINUS$NUMBER$OPTIONAL_FRACTIONAL_PART)"
        private const val SECOND_OPERAND_GROUP = "($OPTIONAL_MINUS$NUMBER$OPTIONAL_FRACTIONAL_PART)"
        private const val OPERATIONS_GROUP = "([-+×÷])"
        private const val EXPRESSION_REGEX = "$FIRST_OPERAND_GROUP$OPERATIONS_GROUP$SECOND_OPERAND_GROUP"
        private const val EMPTY_STRING = ""
        private const val MINUS = "-"
        private const val COMMA = ","
        private const val DOT = "."
        const val ERROR_MESSAGE = "Error"
    }
}