package com.example.tipcalculator // Make sure this matches your package name!

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import kotlin.math.ceil // NEW import

class TipViewModel : ViewModel() {

    // --- STATE ---
    private val _billAmountInput = mutableStateOf("")
    val billAmountInput: State<String> = _billAmountInput

    private val _tipPercentInput = mutableStateOf("")
    val tipPercentInput: State<String> = _tipPercentInput

    private val _numberOfPeople = mutableIntStateOf(1)
    val numberOfPeople: State<Int> = _numberOfPeople

    // --- NEW "ROUND UP" STATE ---
    private val _roundUp = mutableStateOf(false)
    val roundUp: State<Boolean> = _roundUp
    // --- END OF NEW STATE ---

    private val _tip = mutableDoubleStateOf(0.0)
    private val _total = mutableDoubleStateOf(0.0)
    private val _totalPerPerson = mutableDoubleStateOf(0.0)

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

    fun onTipPercentChange(newText: String) {
        _tipPercentInput.value = newText
        calculateTip()
    }

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

    // --- NEW "ROUND UP" EVENT ---
    fun onRoundUpToggle(isToggled: Boolean) {
        _roundUp.value = isToggled
        calculateTip()
    }
    // --- END OF NEW EVENT ---

    // --- PRIVATE LOGIC ---
    private fun calculateTip() {
        val billAmountText = _billAmountInput.value
        val tipPercentText = _tipPercentInput.value

        val billAmount = billAmountText.toDoubleOrNull()?.coerceAtLeast(0.0) ?: 0.0
        val tipPercent = tipPercentText.toDoubleOrNull()?.coerceAtLeast(0.0) ?: 0.0

        val tip = (billAmount * tipPercent) / 100
        var total = billAmount + tip

        // --- NEW ROUNDING LOGIC ---
        if (_roundUp.value) {
            // Use kotlin.math.ceil to round *up* to the next whole number
            total = ceil(total)
        }
        // --- END OF NEW LOGIC ---

        _tip.doubleValue = tip
        _total.doubleValue = total
        _totalPerPerson.doubleValue = total / _numberOfPeople.intValue
    }

    private fun formatAsCurrency(amount: Double): String {
        return NumberFormat.getCurrencyInstance().format(amount)
    }
}