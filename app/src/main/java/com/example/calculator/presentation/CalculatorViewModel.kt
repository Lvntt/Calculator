package com.example.calculator.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.calculator.data.CalculatorButton
import com.example.calculator.model.CalculatorModel
import com.example.calculator.ui.theme.ExpressionErrorTextStyle
import com.example.calculator.ui.theme.ExpressionInitialTextStyle
import com.example.calculator.ui.theme.ExpressionInputTextStyle

class CalculatorViewModel : ViewModel() {

    private val _state =
        mutableStateOf<CalculatorUiState>(CalculatorUiState.Initial(ExpressionInitialTextStyle))
    val state: CalculatorUiState
        get() = _state.value

    private var currentExpression: String = EMPTY_STRING

    private val calculatorModel = CalculatorModel()

    private fun setError() {
        currentExpression = CalculatorModel.ERROR_MESSAGE
        _state.value =
            CalculatorUiState.Error(ExpressionErrorTextStyle)
    }

    fun getButtons(): List<List<CalculatorButton>> = calculatorModel.getButtons()

    fun handleButtonClick(button: CalculatorButton) {
        _state.value = CalculatorUiState.Input(ExpressionInputTextStyle, currentExpression)

        when (button) {
            CalculatorButton.ADDITION,
            CalculatorButton.SUBTRACTION,
            CalculatorButton.MULTIPLICATION,
            CalculatorButton.DIVISION
            -> {
                currentExpression = calculatorModel.handleOperationClick(button)
            }
            CalculatorButton.PLUS_MINUS -> {
                currentExpression = calculatorModel.changeSign()
            }
            CalculatorButton.COMMA -> {
                currentExpression = calculatorModel.handlePunctuationClick(button)
            }
            CalculatorButton.ERASE -> {
                currentExpression = calculatorModel.eraseExpression()
            }
            CalculatorButton.PERCENT -> {
                try {
                    currentExpression = calculatorModel.calculatePercent()
                } catch (e: Exception) {
                    setError()
                    return
                }
            }
            CalculatorButton.EQUALITY -> {
                try {
                    currentExpression = calculatorModel.calculateResult()
                } catch (e: Exception) {
                    setError()
                    return
                }
            }
            else -> {
                currentExpression = calculatorModel.handleDigitClick(button)
            }
        }

        _state.value = CalculatorUiState.Input(ExpressionInputTextStyle, currentExpression)
    }

    fun handleEraseIconClick() {
        currentExpression = calculatorModel.eraseSymbol()
        _state.value = CalculatorUiState.Input(ExpressionInputTextStyle, currentExpression)
    }

    companion object {
        const val EMPTY_STRING = ""
    }
}