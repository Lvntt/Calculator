package com.example.calculator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val calculator = Calculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.navigationBarColor = getColor(R.color.black)

        val expressionObserver = Observer<String> { expression ->
            updateUi(expression)
        }

        calculator.currentExpression.observe(this, expressionObserver)

        setupOnClickListeners()
    }

    private fun updateUi(currentExpression: String) {
        if (currentExpression != Calculator.ERROR_MESSAGE) {
            binding.expression.visibility = View.VISIBLE
            binding.error.visibility = View.GONE
            binding.expression.text = currentExpression
        } else {
            binding.expression.visibility = View.GONE
            binding.error.visibility = View.VISIBLE
        }
    }

    private fun setupOnClickListeners() {
        binding.zeroDigitButton.setOnClickListener {
            calculator.handleDigitClick(Digit.ZERO)
        }
        binding.oneDigitButton.setOnClickListener {
            calculator.handleDigitClick(Digit.ONE)
        }
        binding.twoDigitButton.setOnClickListener {
            calculator.handleDigitClick(Digit.TWO)
        }
        binding.threeDigitButton.setOnClickListener {
            calculator.handleDigitClick(Digit.THREE)
        }
        binding.fourDigitButton.setOnClickListener {
            calculator.handleDigitClick(Digit.FOUR)
        }
        binding.fiveDigitButton.setOnClickListener {
            calculator.handleDigitClick(Digit.FIVE)
        }
        binding.sixDigitButton.setOnClickListener {
            calculator.handleDigitClick(Digit.SIX)
        }
        binding.sevenDigitButton.setOnClickListener {
            calculator.handleDigitClick(Digit.SEVEN)
        }
        binding.eightDigitButton.setOnClickListener {
            calculator.handleDigitClick(Digit.EIGHT)
        }
        binding.nineDigitButton.setOnClickListener {
            calculator.handleDigitClick(Digit.NINE)
        }
        binding.additionButton.setOnClickListener {
            calculator.handleOperationClick(Operations.ADDITION)
        }
        binding.subtractionButton.setOnClickListener {
            calculator.handleOperationClick(Operations.SUBTRACTION)
        }
        binding.multiplicationButton.setOnClickListener {
            calculator.handleOperationClick(Operations.MULTIPLICATION)
        }
        binding.divisionButton.setOnClickListener {
            calculator.handleOperationClick(Operations.DIVISION)
        }
        binding.commaButton.setOnClickListener {
            calculator.handlePunctuationClick(COMMA)
        }
        binding.changeSignButton.setOnClickListener {
            calculator.changeSign()
        }
        binding.eraseButton.setOnClickListener {
            calculator.handleEraseButton()
        }
        binding.eraseExpressionButton.setOnClickListener {
            calculator.eraseExpression()
        }
        binding.percentButton.setOnClickListener {
            calculator.calculatePercent()
        }
        binding.equalButton.setOnClickListener {
            calculator.calculateResult()
        }
    }

    companion object {
        private const val COMMA = ","
    }
}