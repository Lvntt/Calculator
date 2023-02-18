package com.example.calculator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val calculator = Calculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.navigationBarColor = getColor(R.color.black)
        setupOnClickListeners()
    }

    private fun updateUi() {
        val currentExpression = calculator.getCurrentExpression()

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
            calculator.handleDigitClick(binding.zeroDigitButton.text.toString())
            updateUi()
        }
        binding.oneDigitButton.setOnClickListener {
            calculator.handleDigitClick(binding.oneDigitButton.text.toString())
            updateUi()
        }
        binding.twoDigitButton.setOnClickListener {
            calculator.handleDigitClick(binding.twoDigitButton.text.toString())
            updateUi()
        }
        binding.threeDigitButton.setOnClickListener {
            calculator.handleDigitClick(binding.threeDigitButton.text.toString())
            updateUi()
        }
        binding.fourDigitButton.setOnClickListener {
            calculator.handleDigitClick(binding.fourDigitButton.text.toString())
            updateUi()
        }
        binding.fiveDigitButton.setOnClickListener {
            calculator.handleDigitClick(binding.fiveDigitButton.text.toString())
            updateUi()
        }
        binding.sixDigitButton.setOnClickListener {
            calculator.handleDigitClick(binding.sixDigitButton.text.toString())
            updateUi()
        }
        binding.sevenDigitButton.setOnClickListener {
            calculator.handleDigitClick(binding.sevenDigitButton.text.toString())
            updateUi()
        }
        binding.eightDigitButton.setOnClickListener {
            calculator.handleDigitClick(binding.eightDigitButton.text.toString())
            updateUi()
        }
        binding.nineDigitButton.setOnClickListener {
            calculator.handleDigitClick(binding.nineDigitButton.text.toString())
            updateUi()
        }
        binding.commaButton.setOnClickListener {
            calculator.handlePunctuationClick(binding.commaButton.text.toString())
            updateUi()
        }
        binding.sumButton.setOnClickListener {
            calculator.handleOperationClick(binding.sumButton.text.toString())
            updateUi()
        }
        binding.subtractionButton.setOnClickListener {
            calculator.handleOperationClick(binding.subtractionButton.text.toString())
            updateUi()
        }
        binding.multiplicationButton.setOnClickListener {
            calculator.handleOperationClick(binding.multiplicationButton.text.toString())
            updateUi()
        }
        binding.divisionButton.setOnClickListener {
            calculator.handleOperationClick(binding.divisionButton.text.toString())
            updateUi()
        }
        binding.changeSignButton.setOnClickListener {
            calculator.changeSign()
            updateUi()
        }
        binding.eraseButton.setOnClickListener {
            calculator.handleEraseButton()
            updateUi()
        }
        binding.eraseExpressionButton.setOnClickListener {
            calculator.eraseExpression()
            updateUi()
        }
        binding.percentButton.setOnClickListener {
            calculator.calculatePercent()
            updateUi()
        }
        binding.equalButton.setOnClickListener {
            calculator.calculateResult()
            updateUi()
        }
    }
}