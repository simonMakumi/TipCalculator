package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
                    TipCalculatorScreen(viewModel = viewModel())
                }
            }
        }
    }
}

@Composable
fun TipCalculatorScreen(viewModel: TipViewModel) {

    // --- STATE ---
    val billAmountInput by viewModel.billAmountInput
    val tipPercentInput by viewModel.tipPercentInput
    val numberOfPeople by viewModel.numberOfPeople

    val tipFormatted = viewModel.tipFormatted
    val totalFormatted = viewModel.totalFormatted
    val totalPerPersonFormatted = viewModel.totalPerPersonFormatted
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

        // Bill Amount
        OutlinedTextField(
            value = billAmountInput,
            onValueChange = { viewModel.onBillAmountChange(it) },
            label = { Text("Bill Amount") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = { Text("$") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tip Percent
        OutlinedTextField(
            value = tipPercentInput,
            onValueChange = { viewModel.onTipPercentChange(it) },
            label = { Text("Tip") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            suffix = { Text("%") }
        )

        Spacer(modifier = Modifier.height(16.dp)) // Smaller spacer

        // --- NEW "SPLIT BY" ROW ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Split by",
                fontSize = 20.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Decrease Button
                IconButton(onClick = { viewModel.decreasePeople() }) {
                    Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease people")
                }

                Text(
                    text = "$numberOfPeople",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                // Increase Button
                IconButton(onClick = { viewModel.increasePeople() }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Increase people")
                }
            }
        }
        // --- END OF NEW ROW ---

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

        Spacer(modifier = Modifier.height(16.dp)) // NEW

        // --- NEW "PER PERSON" TEXT ---
        Text(
            text = "Per Person: $totalPerPersonFormatted",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary // Make it stand out
        )
        // --- END OF NEW TEXT ---
    }
}


@Preview(showBackground = true)
@Composable
fun TipCalculatorScreenPreview() {
    TipCalculatorTheme {
        Text("Tip Calculator Screen")
    }
}