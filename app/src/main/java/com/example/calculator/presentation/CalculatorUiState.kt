package com.example.calculator.presentation

sealed interface CalculatorUiState {

    object Initial : CalculatorUiState

    object Input : CalculatorUiState

    object Error : CalculatorUiState
}