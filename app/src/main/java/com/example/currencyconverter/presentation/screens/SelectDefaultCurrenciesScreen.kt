package com.example.currencyconverter.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.presentation.components.CurrencyPicker
import com.example.currencyconverter.viewmodel.CurrencyViewModel

@Composable
fun SelectDefaultCurrenciesScreen(
    modifier: Modifier = Modifier,
    currencyViewModel: CurrencyViewModel = hiltViewModel(),
    onContinue: (String, String) -> Unit,
) {
    var baseCurrency by remember { mutableStateOf("USD") }
    var targetCurrency by remember { mutableStateOf("EUR") }

    val currencies by currencyViewModel.currencies.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Select default currencies:", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))

        CurrencyPicker(
            label = "Base Currency: ",
            selectedCurrency = baseCurrency,
            onCurrencySelected = { baseCurrency = it },
            currencies = currencies
        )
        CurrencyPicker(
            label = "Target Currency: ",
            selectedCurrency = targetCurrency,
            onCurrencySelected = { targetCurrency = it },
            currencies = currencies
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onContinue(baseCurrency, targetCurrency) }) {
            Text("Continue")
        }
    }
}