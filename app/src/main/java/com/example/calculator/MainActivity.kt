package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var input: TextView? = null
    var lastNumeric : Boolean = false
    var lastDecimal : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input = findViewById<TextView>(R.id.input)
    }

    fun onDigit(view:View) {
        if(input?.text == "0") {
            input?.text = (view as Button).text
        }
        else {
            input?.append((view as Button).text)
        }
        lastNumeric = true
    }

    fun onClear(view:View) {
        input?.text = ""
    }

    fun onDecimalPoint(view:View) {
        if(lastNumeric && !lastDecimal) {
            input?.append(".")
            lastNumeric = false
            lastDecimal = true
        }
    }

    fun onOperator(view: View) {
        input?.text?.let {
            if(lastNumeric && !isOperatorAdded(it.toString())) {
                input?.append((view as Button).text)
                lastNumeric = false
                lastDecimal = false
            }
        }
    }

    fun onEqual(view:View) {
        if(lastNumeric) {
            var value = input?.text.toString()
            var prefix = ""

            try {
                if(value.startsWith("-")) {
                    prefix = "-"
                    value = value.substring(1)
                }

                if(value.contains("-")) {
                    val splitValue = value.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix != "") {
                        one = prefix + one
                    }

                    input?.text = removeZero((one.toDouble() - two.toDouble()).toString())
                }
                else if(value.contains("+")) {
                    val splitValue = value.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix != "") {
                        one = prefix + one
                    }

                    input?.text = removeZero((one.toDouble() + two.toDouble()).toString())
                }
                else if(value.contains("/")) {
                    val splitValue = value.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix != "") {
                        one = prefix + one
                    }

                    input?.text = removeZero((one.toDouble() /  two.toDouble()).toString())
                }
                else if(value.contains("*")) {
                    val splitValue = value.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix != "") {
                        one = prefix + one
                    }

                    input?.text = removeZero((one.toDouble() * two.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZero(result:String) :String {
        var value = result

        if(result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }

        return value
    }

    private fun isOperatorAdded(value:String):Boolean {
        return if(value.startsWith("-")) {
            false
        }
        else {
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }
}