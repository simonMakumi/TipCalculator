package com.example.tipcalculator

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.text.NumberFormat

class TipViewModel : ViewModel() {

    // --- STATE ---
    private val _billAmountInput = mutableStateOf("")
    val billAmountInput: State<String> = _billAmountInput

    private val _tipPercentInput = mutableStateOf("")
    val tipPercentInput: State<String> = _tipPercentInput

    // --- NEW STATE for Splitting ---
    private val _numberOfPeople = mutableIntStateOf(1) // Start with 1 person
    val numberOfPeople: State<Int> = _numberOfPeople
    // --- END OF NEW STATE ---

    private val _tip = mutableStateOf(0.0)
    private val _total = mutableStateOf(0.0)

    // --- NEW STATE for Per Person ---
    private val _totalPerPerson = mutableStateOf(0.0)
    // --- END OF NEW STATE ---


    // --- UI-Formatted State ---
    val tipFormatted: String
        get() = formatAsCurrency(_tip.value)

    val totalFormatted: String
        get() = formatAsCurrency(_total.value)

    // --- NEW UI-Formatted State ---
    val totalPerPersonFormatted: String
        get() = formatAsCurrency(_totalPerPerson.value)
    // --- END OF NEW STATE ---


    // --- INIT ---
    // Calculate initial values when ViewModel is created
    init {
        calculateTip()
    }

    // --- EVENTS (Logic) ---

    fun onBillAmountChange(newText: String) {
        _billAmountInput.value = newText
        calculateTip()
    }

    fun onTipPercentChange(newText: String) {
        _tipPercentInput.value = newText
        calculateTip()
    }

    // --- NEW EVENTS for Splitting ---
    fun increasePeople() {
        _numberOfPeople.intValue++ // Increase count by 1
        calculateTip() // Recalculate
    }

    fun decreasePeople() {
        if (_numberOfPeople.intValue > 1) { // Validation: Can't go below 1
            _numberOfPeople.intValue-- // Decrease count by 1
            calculateTip() // Recalculate
        }
    }
    // --- END OF NEW EVENTS ---


    // --- PRIVATE LOGIC ---

    private fun calculateTip() {
        val billAmountText = _billAmountInput.value
        val tipPercentText = _tipPercentInput.value

        val billAmount = billAmountText.toDoubleOrNull()?.coerceAtLeast(0.0) ?: 0.0
        val tipPercent = tipPercentText.toDoubleOrNull()?.coerceAtLeast(0.0) ?: 0.0

        // Calculate tip and total
        _tip.value = (billAmount * tipPercent) / 100
        _total.value = billAmount + _tip.value

        // --- NEW CALCULATION ---
        // Calculate total per person
        _totalPerPerson.value = _total.value / _numberOfPeople.intValue
        // --- END OF NEW CALCULATION ---
    }

    private fun formatAsCurrency(amount: Double): String {
        return NumberFormat.getCurrencyInstance().format(amount)
    }
}