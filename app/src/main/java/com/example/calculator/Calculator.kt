package com.example.calculator

import java.math.BigDecimal

class Calculator {

    companion object {
        const val ERROR_MESSAGE = "Error"
    }

    private var currentExpression: String = ""
    private val operationSigns = listOf('+', '-', '÷', '×')
    private var expressionCanBeReset = false

    private fun expressionIsValid(): Boolean {
        return currentExpression.isNotEmpty() && currentExpression != ERROR_MESSAGE
    }

    fun handleDigitClick(digit: String) {
        if (expressionCanBeReset) {
            eraseExpression()
            expressionCanBeReset = false
        }
        currentExpression += digit
    }

    fun handlePunctuationClick(punctuationSign: String) {
        val lastExpressionSymbol = currentExpression[currentExpression.lastIndex]

        if (expressionIsValid()) {
            if (currentExpression.contains(punctuationSign) && lastExpressionSymbol !in operationSigns) {
                val operationSignIndex = currentExpression.substring(1).indexOfAny(operationSigns.toCharArray())
                val expressionAfterOperationSign = currentExpression.substring(operationSignIndex + 1)
                if (currentExpression.substring(1).any { it in operationSigns } && !expressionAfterOperationSign.contains(punctuationSign)) {
                    expressionCanBeReset = false
                    currentExpression += punctuationSign
                }
            } else if (lastExpressionSymbol !in operationSigns) {
                expressionCanBeReset = false
                currentExpression += punctuationSign
            }
        }
    }

    fun handleOperationClick(operation: String) {
        val lastExpressionSymbol = currentExpression[currentExpression.lastIndex]

        if (expressionIsValid() && currentExpression.lastIndex != 0 && lastExpressionSymbol in operationSigns) {
            val chars = currentExpression.toCharArray()
            chars[chars.lastIndex] = operation[0]
            currentExpression = chars.joinToString("")
            expressionCanBeReset = false
        } else if (expressionIsValid()
            && lastExpressionSymbol != ','
            && lastExpressionSymbol !in operationSigns
            && (!currentExpression.substring(1).any { it in operationSigns })
        ) {
            currentExpression += operation
            expressionCanBeReset = false
        }
    }

    fun changeSign() {
        currentExpression = if (currentExpression.startsWith("-")) {
            currentExpression.removePrefix("-")
        } else {
            currentExpression.replaceRange(0, 0, "-")
        }
    }

    fun handleEraseButton() {
        if (expressionCanBeReset) {
            eraseExpression()
            expressionCanBeReset = false
        } else if (currentExpression.isNotEmpty()) {
            currentExpression = currentExpression.substring(0, currentExpression.lastIndex)
        }
    }

    fun eraseExpression() {
        currentExpression = ""
    }

    fun calculatePercent() {
        val lastExpressionSymbol = currentExpression[currentExpression.lastIndex]

        if (!expressionIsValid() || lastExpressionSymbol in operationSigns) {
            return
        }

        if (currentExpression.contains(Regex("[^\\d,.]"))) {
            calculateResult()
        }

        val result = currentExpression.replace(",", ".").toDouble() / 100
        currentExpression = if (result % 1 == 0.0) {
            result.toInt().toString().replace(".", ",")
        } else {
            result.toString().replace(".", ",")
        }
        expressionCanBeReset = true
    }

    fun calculateResult() {
        try {
            val match =
                Regex("(-?\\d+(?:[,.]\\d+)?)([-+×÷])(-?\\d+(?:[,.]\\d+)?)").find(currentExpression)!!
            val (leftOperand, operation, rightOperand) = match.destructured
            val firstNumber = leftOperand.replace(",", ".").toBigDecimal()
            val secondNumber = rightOperand.replace(",", ".").toBigDecimal()
            var result: BigDecimal = BigDecimal.valueOf(0.0)

            when (operation) {
                "-" -> result = firstNumber - secondNumber
                "+" -> result = firstNumber + secondNumber
                "×" -> result = firstNumber * secondNumber
                "÷" -> {
                    if (secondNumber == BigDecimal.valueOf(0.0)) {
                        currentExpression = ERROR_MESSAGE
                        expressionCanBeReset = true
                        return
                    } else {
                        result = firstNumber.divide(secondNumber)
                    }
                }
            }

            currentExpression = if (result % BigDecimal.valueOf(1.0) == BigDecimal.valueOf(0.0)) {
                result.toInt().toString().replace(".", ",")
            } else {
                result.toString().replace(".", ",")
            }
            expressionCanBeReset = true
        } catch (_: Exception) {

        }
    }

    fun getCurrentExpression(): String = currentExpression
}