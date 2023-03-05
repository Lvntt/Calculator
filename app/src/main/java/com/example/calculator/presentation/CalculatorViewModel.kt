package com.example.calculator.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.calculator.data.CalculatorButton
import com.example.calculator.model.CalculatorModel

class CalculatorViewModel : ViewModel() {
    private val _currentExpression = mutableStateOf(EMPTY_STRING)
    val currentExpression: String
        get() = _currentExpression.value

    private val calculatorModel = CalculatorModel()

    fun handleButtonClick(button: CalculatorButton) {
        when (button) {
            CalculatorButton.ADDITION,
            CalculatorButton.SUBTRACTION,
            CalculatorButton.MULTIPLICATION,
            CalculatorButton.DIVISION
            -> {
                _currentExpression.value = calculatorModel.handleOperationClick(button.value)
            }
            CalculatorButton.PLUS_MINUS -> {
                _currentExpression.value = calculatorModel.changeSign()
            }
            CalculatorButton.COMMA -> {
                _currentExpression.value = calculatorModel.handlePunctuationClick(button.value)
            }
            CalculatorButton.PERCENT -> {
                _currentExpression.value = calculatorModel.calculatePercent()
            }
            CalculatorButton.ERASE -> {
                _currentExpression.value = calculatorModel.eraseExpression()
            }
            CalculatorButton.EQUALITY -> {
                _currentExpression.value = calculatorModel.calculateResult()
            }
            else -> {
                _currentExpression.value = calculatorModel.handleDigitClick(button.value)
            }
        }
    }

    fun handleEraseIconClick() {
        _currentExpression.value = calculatorModel.eraseSymbol()
    }

    companion object {
        const val EMPTY_STRING = ""
    }
}