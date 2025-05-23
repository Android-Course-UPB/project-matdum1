package com.example.currencyconverter.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverter.MainActivity
import com.example.currencyconverter.presentation.components.CurrencyAppBar
import com.example.currencyconverter.presentation.components.ExitConfirmationDialog
import com.example.currencyconverter.presentation.navigation.CurrencyConverterNavHost
import com.example.currencyconverter.presentation.navigation.Screen

@Composable
fun CurrencyConverterApp() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentDestination = currentBackStackEntry?.destination?.route
    val showAppBar = currentDestination in listOf(Screen.Home.route, Screen.Settings.route)
    var showExitDialog by remember { mutableStateOf(false) }

    if (showExitDialog) {
        ExitConfirmationDialog(
            onConfirm = {
                (context as? MainActivity)?.finish()
            },
            onDismiss = {
                showExitDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            if (showAppBar) CurrencyAppBar(
                onHomeClick = { navController.navigate(Screen.Home.route) },
                onSettingsClick = { navController.navigate(Screen.Settings.route) },
                onExitClick = { showExitDialog = true }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        CurrencyConverterNavHost(
            navController = navController,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = innerPadding.calculateTopPadding())
                .fillMaxSize()
        )
    }
}
