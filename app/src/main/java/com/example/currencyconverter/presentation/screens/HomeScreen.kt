package com.example.currencyconverter.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.R
import com.example.currencyconverter.helpers.showSnackbarMessage
import com.example.currencyconverter.presentation.components.CurrencyPicker
import com.example.currencyconverter.viewmodel.CurrencyViewModel
import com.example.currencyconverter.viewmodel.ExchangeRateViewModel
import com.example.currencyconverter.viewmodel.SettingsViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    currencyViewModel: CurrencyViewModel = hiltViewModel(),
    exchangeRateViewModel: ExchangeRateViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by settingsViewModel.settings.collectAsState()
    val currencies by currencyViewModel.currencies.collectAsState()
    val conversionResult by exchangeRateViewModel.conversionResult.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var amount by remember { mutableStateOf(TextFieldValue("")) }
    var baseCurrency by remember { mutableStateOf(settings.defaultBaseCurrency) }
    var targetCurrency by remember { mutableStateOf(settings.defaultTargetCurrency) }

    LaunchedEffect(settings) {
        baseCurrency = settings.defaultBaseCurrency
        targetCurrency = settings.defaultTargetCurrency
    }

    Scaffold(
        snackbarHost = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                SnackbarHost(hostState = snackbarHostState)
            }
        }) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            Text(
                text = "Hello, ${settings.name}"
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CurrencyPicker(
                    selectedCurrency = baseCurrency,
                    onCurrencySelected = {
                        if (targetCurrency == it) {
                            targetCurrency = baseCurrency
                        }
                        baseCurrency = it
                        exchangeRateViewModel.convertCurrency(
                            amount = amount.text.toDoubleOrNull() ?: 0.0,
                            from = baseCurrency,
                            to = targetCurrency
                        )
                    },
                    currencies = currencies
                )

                Spacer(modifier = Modifier.width(8.dp))

                TextField(
                    value = amount,
                    onValueChange = {
                        amount = it
                        val amountDouble = it.text.toDoubleOrNull()
                        if (amountDouble != null) {
                            exchangeRateViewModel.convertCurrency(
                                amount = amountDouble,
                                from = baseCurrency,
                                to = targetCurrency
                            )
                        } else {
                            exchangeRateViewModel.clearConversionResult()
                            if (amount.text.isNotBlank()) {
                                showSnackbarMessage(
                                    snackbarHostState = snackbarHostState,
                                    coroutineScope = coroutineScope,
                                    message = "Please enter a valid amount"
                                )
                            }
                        }
                    },
                    label = { Text("Amount") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                var temp = baseCurrency
                baseCurrency = targetCurrency
                targetCurrency = temp
                if (amount.text.isNotBlank()) {
                    val newAmount = conversionResult?.toString() ?: ""

                    temp = amount.text
                    amount = TextFieldValue(
                        text = newAmount,
                        selection = TextRange(newAmount.length)
                    )
                    exchangeRateViewModel.setConversionResult(temp.toDouble())
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.swap_vert),
                    contentDescription = "Swap currencies",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CurrencyPicker(
                    selectedCurrency = targetCurrency,
                    onCurrencySelected = {
                        if (baseCurrency == it) {
                            baseCurrency = targetCurrency
                        }
                        targetCurrency = it
                        exchangeRateViewModel.convertCurrency(
                            amount = amount.text.toDoubleOrNull() ?: 0.0,
                            from = baseCurrency,
                            to = targetCurrency
                        )
                    },
                    currencies = currencies
                )

                Spacer(modifier = Modifier.width(8.dp))

                TextField(
                    value = conversionResult?.toString() ?: "",
                    onValueChange = {},
                    label = { Text("Converted Amount") },
                    modifier = Modifier.weight(1f),
                    enabled = false
                )
            }
        }
    }
}
