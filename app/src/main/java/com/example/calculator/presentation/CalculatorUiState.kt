package com.example.calculator.presentation

private const val DEFAULT_ERROR_MESSAGE = "Error"
private const val EMPTY_STRING = ""

sealed interface CalculatorUiState {

    data class Initial(val initialExpression: String = EMPTY_STRING): CalculatorUiState

    data class Input(val expression: String) : CalculatorUiState

    data class Error(val errorMessage: String = DEFAULT_ERROR_MESSAGE) : CalculatorUiState
}