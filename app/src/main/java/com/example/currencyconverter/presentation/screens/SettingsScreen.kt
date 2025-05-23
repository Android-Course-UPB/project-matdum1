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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.helpers.showSnackbarMessage
import com.example.currencyconverter.presentation.components.CurrencyPicker
import com.example.currencyconverter.viewmodel.CurrencyViewModel
import com.example.currencyconverter.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    currencyViewModel: CurrencyViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by settingsViewModel.settings.collectAsState()
    val currencies by currencyViewModel.currencies.collectAsState()
    val isDarkMode by settingsViewModel.darkMode.collectAsState()

    var newName by remember { mutableStateOf(settings.name) }

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                SnackbarHost(hostState = snackbarHostState)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Change Your Name:", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(2.dp))

            TextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("Enter Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (newName.isBlank()) {
                        showSnackbarMessage(
                            snackbarHostState = snackbarHostState,
                            coroutineScope = coroutineScope,
                            message = "Name cannot be empty"
                        )
                    } else {
                        settingsViewModel.updateName(newName)
                        showSnackbarMessage(
                            snackbarHostState = snackbarHostState,
                            coroutineScope = coroutineScope,
                            message = "Name updated successfully to: $newName"
                        )
                    }
                }
            ) {
                Text(text = "Update Name")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Set Default Currency:", style = MaterialTheme.typography.bodyLarge)
            CurrencyPicker(
                label = "Default Base Currency: ",
                selectedCurrency = settings.defaultBaseCurrency,
                onCurrencySelected = {
                    if (settings.defaultTargetCurrency == it) {
                        settingsViewModel.updateDefaultTargetCurrency(settings.defaultBaseCurrency)
                    }
                    settingsViewModel.updateDefaultBaseCurrency(it)
                },
                currencies = currencies
            )

            CurrencyPicker(
                label = "Default Target Currency: ",
                selectedCurrency = settings.defaultTargetCurrency,
                onCurrencySelected = {
                    if (settings.defaultBaseCurrency == it) {
                        settingsViewModel.updateDefaultBaseCurrency(settings.defaultTargetCurrency)
                    }
                    settingsViewModel.updateDefaultTargetCurrency(it)
                },
                currencies = currencies
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Dark Mode ", style = MaterialTheme.typography.bodyLarge)
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = {
                        settingsViewModel.toggleDarkMode(it)
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Version: 1.0", style = MaterialTheme.typography.bodySmall)
            Text(text = "Made by Matei", style = MaterialTheme.typography.bodySmall)
        }
    }
}

