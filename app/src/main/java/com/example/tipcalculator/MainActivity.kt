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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Call our main screen composable
                    TipCalculatorScreen()
                }
            }
        }
    }
}

// Main screen composable
@Composable
fun TipCalculatorScreen() {
    // --- STATE ---
    // We use 'remember' and 'mutableStateOf' to hold the text
    // the user types into the text fields.
    var billAmountInput by remember { mutableStateOf("") }
    var tipPercentInput by remember { mutableStateOf("") }
    // --- END OF STATE ---

    // A Column arranges its children vertically
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp), // Add generous padding around the screen
        verticalArrangement = Arrangement.Center, // Center everything vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center everything horizontally
    ) {

        Text(
            text = "Tip Calculator",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp)) // A large space

        // --- INPUT FIELDS ---

        // Text field for the bill amount
        OutlinedTextField(
            value = billAmountInput,
            onValueChange = { billAmountInput = it },
            label = { Text("Bill Amount") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true, // Ensures the input is a single line
            // Tell the keyboard to show numbers only
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp)) // A smaller space

        // Text field for the tip percentage
        OutlinedTextField(
            value = tipPercentInput,
            onValueChange = { tipPercentInput = it },
            label = { Text("Tip %") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            // Tell the keyboard to show numbers only
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(32.dp)) // A large space

        // --- RESULTS ---
        // We will display the results here. For now, they are hardcoded.

        Text(
            text = "Tip: $0.00",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total: $0.00",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// This is the preview for our TipCalculatorScreen
@Preview(showBackground = true)
@Composable
fun TipCalculatorScreenPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen()
    }
}