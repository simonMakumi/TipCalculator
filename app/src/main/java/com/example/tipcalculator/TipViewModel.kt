package com.example.tipcalculator


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.text.NumberFormat

class TipViewModel : ViewModel() {

    // --- STATE ---
    private val _billAmountInput = mutableStateOf("")
    val billAmountInput: State<String> = _billAmountInput

    private val _tipPercentInput = mutableStateOf("")
    val tipPercentInput: State<String> = _tipPercentInput

    private val _tip = mutableStateOf(0.0)
    private val _total = mutableStateOf(0.0)

    // --- UI-Formatted State ---
    val tipFormatted: String
        get() = formatAsCurrency(_tip.value)

    val totalFormatted: String
        get() = formatAsCurrency(_total.value)


    // --- EVENTS (Logic) ---

    // Called when the bill amount changes
    fun onBillAmountChange(newText: String) {
        _billAmountInput.value = newText
        calculateTip()
    }

    // Called when the tip percentage changes
    fun onTipPercentChange(newText: String) {
        _tipPercentInput.value = newText
        calculateTip()
    }

    // --- PRIVATE LOGIC ---

    private fun calculateTip() {
        // Read the text from the state
        val billAmountText = _billAmountInput.value
        val tipPercentText = _tipPercentInput.value

        // Safely convert to a Double, or use 0.0 if it's null (e.g., empty)
        // .coerceAtLeast(0.0) is our VALIDATION. It refuses negative numbers.
        val billAmount = billAmountText.toDoubleOrNull()?.coerceAtLeast(0.0) ?: 0.0
        val tipPercent = tipPercentText.toDoubleOrNull()?.coerceAtLeast(0.0) ?: 0.0

        // Calculate
        _tip.value = (billAmount * tipPercent) / 100
        _total.value = billAmount + _tip.value
    }

    /**
     * Formats a Double as a currency string (e.g., "$123.45")
     * This logic now lives in the ViewModel, not the UI.
     */
    private fun formatAsCurrency(amount: Double): String {
        return NumberFormat.getCurrencyInstance().format(amount)
    }
}