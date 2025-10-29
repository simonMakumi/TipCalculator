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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tipcalculator.ui.theme.TipCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Get the ViewModel instance
                    TipCalculatorScreen(viewModel = viewModel())
                }
            }
        }
    }
}

// Our screen now receives the ViewModel
@Composable
fun TipCalculatorScreen(viewModel: TipViewModel) {

    // --- STATE ---
    // Read all state from the ViewModel
    val billAmountInput by viewModel.billAmountInput
    val tipPercentInput by viewModel.tipPercentInput

    val tipFormatted = viewModel.tipFormatted
    val totalFormatted = viewModel.totalFormatted
    // --- END OF STATE ---


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
            onValueChange = { viewModel.onBillAmountChange(it) }, // Call ViewModel
            label = { Text("Bill Amount") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            // --- NEW ICON ---
            leadingIcon = { Text("$") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Text field for the tip percentage
        OutlinedTextField(
            value = tipPercentInput,
            onValueChange = { viewModel.onTipPercentChange(it) }, // Call ViewModel
            label = { Text("Tip") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            // --- NEW SUFFIX ---
            suffix = { Text("%") }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- RESULTS ---
        Text(
            text = "Tip: $tipFormatted",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total: $totalFormatted",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TipCalculatorScreenPreview() {
    TipCalculatorTheme {

        Text("Tip Calculator Screen")
    }
}