package com.example.tipcalculator

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.text.NumberFormat

class TipViewModel : ViewModel() {

    // --- STATE ---
    private val _billAmountInput = mutableStateOf("")
    val billAmountInput: State<String> = _billAmountInput

    // --- 1. REVERTED: Back to a String for the text field ---
    private val _tipPercentInput = mutableStateOf("")
    val tipPercentInput: State<String> = _tipPercentInput
    // --- END OF REVERT ---

    private val _numberOfPeople = mutableIntStateOf(1)
    val numberOfPeople: State<Int> = _numberOfPeople

    // --- 2. KEPT: Using mutableDoubleStateOf ---
    private val _tip = mutableDoubleStateOf(0.0)
    private val _total = mutableDoubleStateOf(0.0)
    private val _totalPerPerson = mutableDoubleStateOf(0.0)
    // --- END OF FIX ---

    // --- UI-Formatted State ---
    val tipFormatted: String
        get() = formatAsCurrency(_tip.doubleValue)

    val totalFormatted: String
        get() = formatAsCurrency(_total.doubleValue)

    val totalPerPersonFormatted: String
        get() = formatAsCurrency(_totalPerPerson.doubleValue)

    init {
        calculateTip()
    }

    // --- EVENTS (Logic) ---

    fun onBillAmountChange(newText: String) {
        _billAmountInput.value = newText
        calculateTip()
    }

    // --- 1. REVERTED: Back to taking a String ---
    fun onTipPercentChange(newText: String) {
        _tipPercentInput.value = newText
        calculateTip()
    }
    // --- END OF REVERT ---

    fun increasePeople() {
        _numberOfPeople.intValue++
        calculateTip()
    }

    fun decreasePeople() {
        if (_numberOfPeople.intValue > 1) {
            _numberOfPeople.intValue--
            calculateTip()
        }
    }

    // --- PRIVATE LOGIC ---
    private fun calculateTip() {
        val billAmountText = _billAmountInput.value
        // 1. REVERTED: Read the text from the state
        val tipPercentText = _tipPercentInput.value

        val billAmount = billAmountText.toDoubleOrNull()?.coerceAtLeast(0.0) ?: 0.0
        // 1. REVERTED: Convert the text to a number
        val tipPercent = tipPercentText.toDoubleOrNull()?.coerceAtLeast(0.0) ?: 0.0

        // 2. KEPT: Assign with .doubleValue
        _tip.doubleValue = (billAmount * tipPercent) / 100
        _total.doubleValue = billAmount + _tip.doubleValue
        _totalPerPerson.doubleValue = _total.doubleValue / _numberOfPeople.intValue
    }

    private fun formatAsCurrency(amount: Double): String {
        return NumberFormat.getCurrencyInstance().format(amount)
    }
}