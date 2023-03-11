package com.example.calculator.presentation

import androidx.compose.ui.text.TextStyle

private const val DEFAULT_ERROR_MESSAGE = "Error"
private const val EMPTY_STRING = ""

sealed interface CalculatorUiState {

    data class Initial(val style: TextStyle, val initialExpression: String = EMPTY_STRING): CalculatorUiState

    data class Input(val style: TextStyle, val expression: String) : CalculatorUiState

    data class Error(val style: TextStyle, val errorMessage: String = DEFAULT_ERROR_MESSAGE) : CalculatorUiState
}