package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat // NEW import

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorScreen() {
    var billAmountInput by remember { mutableStateOf("") }
    var tipPercentInput by remember { mutableStateOf("") }

    // --- NEW: CALCULATION LOGIC ---

    // Convert text inputs to numbers.
    // 'toDoubleOrNull()' is a safe way to convert. It returns 'null' if the text isn't a valid number.
    // The '?: 0.0' (Elvis operator) means "if the result is null, use 0.0 instead".
    val billAmount = billAmountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipPercentInput.toDoubleOrNull() ?: 0.0

    // Calculate the tip and total
    val tip = (billAmount * tipPercent) / 100
    val total = billAmount + tip

    // Format the numbers as currency
    val tipFormatted = formatAsCurrency(tip)
    val totalFormatted = formatAsCurrency(total)

    // --- END OF NEW LOGIC ---

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Tip Calculator",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Text field for the bill amount
        OutlinedTextField(
            value = billAmountInput,
            // When the value changes, we update our state.
            // This triggers a "recomposition", and all the logic above runs again.
            onValueChange = { billAmountInput = it },
            label = { Text("Bill Amount") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Text field for the tip percentage
        OutlinedTextField(
            value = tipPercentInput,
            onValueChange = { tipPercentInput = it },
            label = { Text("Tip %") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- UPDATED RESULTS ---
        // Display the new formatted values

        Text(
            text = "Tip: $tipFormatted", // UPDATED
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total: $totalFormatted", // UPDATED
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// --- HELPER FUNCTION ---
/**
 * Formats a Double as a currency string
 */
private fun formatAsCurrency(amount: Double): String {
    // This uses the device's default locale to format currency.
    // In the US, it's "$", in Europe it might be "€", etc.
    return NumberFormat.getCurrencyInstance().format(amount)
}
// --- END OF HELPER FUNCTION ---

@Preview(showBackground = true)
@Composable
fun TipCalculatorScreenPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen()
    }
}