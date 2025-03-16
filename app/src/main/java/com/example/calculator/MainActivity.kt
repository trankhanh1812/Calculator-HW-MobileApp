package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : AppCompatActivity() {
    private lateinit var resultTextView: TextView
    private var currentInput: String = ""
    private var operand1: Double? = null
    private var operator: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)

        //number
        findViewById<Button>(R.id.button0).setOnClickListener { appendNumber("0")}
        findViewById<Button>(R.id.button1).setOnClickListener { appendNumber("1")}
        findViewById<Button>(R.id.button2).setOnClickListener { appendNumber("2")}
        findViewById<Button>(R.id.button3).setOnClickListener { appendNumber("3")}
        findViewById<Button>(R.id.button4).setOnClickListener { appendNumber("4")}
        findViewById<Button>(R.id.button5).setOnClickListener { appendNumber("5")}
        findViewById<Button>(R.id.button6).setOnClickListener { appendNumber("6")}
        findViewById<Button>(R.id.button7).setOnClickListener { appendNumber("7")}
        findViewById<Button>(R.id.button8).setOnClickListener { appendNumber("8")}
        findViewById<Button>(R.id.button9).setOnClickListener { appendNumber("9")}

        //operator
        findViewById<Button>(R.id.buttonAdd).setOnClickListener {performOperation("+")}
        findViewById<Button>(R.id.buttonSubtract).setOnClickListener {performOperation("-")}
        findViewById<Button>(R.id.buttonMultiply).setOnClickListener {performOperation("x")}
        findViewById<Button>(R.id.buttonDivide).setOnClickListener {performOperation("/")}

        //func
        findViewById<Button>(R.id.buttonEquals).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.buttonC).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.buttonCE).setOnClickListener { clearEntry() }
        findViewById<Button>(R.id.buttonSign).setOnClickListener { changeSign() }
        findViewById<Button>(R.id.buttonDecimal).setOnClickListener { appendDecimal() }
        findViewById<Button>(R.id.buttonBS).setOnClickListener { backspace() }
    }
    private fun appendNumber(number: String) {
        currentInput += number
        updateResultTextView()
    }

    private fun appendDecimal() {
        if(!currentInput.contains(".")) {
            currentInput += "."
            updateResultTextView()
        }
    }

    private fun changeSign() {
        if(currentInput.isNotEmpty()) {
            if(currentInput.startsWith("-")) {
                currentInput = currentInput.substring(1)
            }
            else currentInput = "-$currentInput"
            updateResultTextView()
        }
    }

    private fun backspace() {
        if(currentInput.isNotEmpty()){
            currentInput = currentInput.substring(0,currentInput.length - 1)
            updateResultTextView()
        }
    }

    private fun performOperation(operation: String) {
        if(currentInput.isNotEmpty()){
            operand1 = currentInput.toDouble()
            this.operator = operation
            currentInput = ""
            updateResultTextView()
        }
    }

    private fun calculateResult() {
        if(operand1 != null && currentInput.isNotEmpty()) {
            val operand2 = currentInput.toDouble()
            val result = when (operator) {
                "+" -> operand1!! + operand2
                "-" -> operand1!! - operand2
                "x" -> operand1!! * operand2
                "/" -> if(operand2 != 0.0) operand1!! / operand2 else {
                    resultTextView.text = "MathError"
                    return
                }
                else -> 0.0
            }
            currentInput = result.toString()
            operand1 = null
            operator = null
            updateResultTextView()
        }
    }

    private fun clearAll() {
        currentInput = ""
        operator = null
        operand1 = null
        updateResultTextView()
    }

    private fun clearEntry() {
        currentInput = ""
        updateResultTextView()
    }

    private fun updateResultTextView() {
        resultTextView.text = if(currentInput.isNotEmpty())  currentInput else "0"
    }
}