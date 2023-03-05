package com.example.calculator.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.calculator.data.CalculatorButton
import com.example.calculator.model.CalculatorModel

class CalculatorViewModel : ViewModel() {

    private val _state = mutableStateOf<CalculatorUiState>(CalculatorUiState.Initial)
    val state: CalculatorUiState
        get() = _state.value

    private val _currentExpression = mutableStateOf(EMPTY_STRING)
    val currentExpression: String
        get() = _currentExpression.value

    private val calculatorModel = CalculatorModel()

    fun handleButtonClick(button: CalculatorButton) {
        _state.value = CalculatorUiState.Input

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
            CalculatorButton.ERASE -> {
                _currentExpression.value = calculatorModel.eraseExpression()
            }
            CalculatorButton.PERCENT -> {
                try {
                    _currentExpression.value = calculatorModel.calculatePercent()
                    _state.value = CalculatorUiState.Input
                } catch (e: Exception) {
                    _currentExpression.value = CalculatorModel.ERROR_MESSAGE
                    _state.value = CalculatorUiState.Error
                }
            }
            CalculatorButton.EQUALITY -> {
                try {
                    _currentExpression.value = calculatorModel.calculateResult()
                    _state.value = CalculatorUiState.Input
                } catch (e: Exception) {
                    _currentExpression.value = CalculatorModel.ERROR_MESSAGE
                    _state.value = CalculatorUiState.Error
                }
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