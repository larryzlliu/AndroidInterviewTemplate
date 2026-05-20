package com.example.techscreentemplate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Screen(viewModel: MainViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Currency.entries.forEach { currency ->
                    RadioButton(
                        selected = uiState.currency == currency,
                        onClick = { viewModel.onCurrencyChanged(currency) },
                    )
                    Text(currency.label)
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.amount,
                onValueChange = viewModel::onAmountChanged,
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = CentsVisualTransformation(uiState.currency),
                isError = uiState.showMaxError,
                supportingText = if (uiState.showMaxError) {
                    { Text("Exceeds ${viewModel.maxAmountFormatted}") }
                } else null,
                singleLine = true,
                modifier = Modifier.width(200.dp),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = viewModel::clearAmount) {
                Text("Clear")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Screen()
}
