package com.example.currencyconverter.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.currencyconverter.data.local.entity.Settings
import com.example.currencyconverter.presentation.screens.EnterNameScreen
import com.example.currencyconverter.presentation.screens.HomeScreen
import com.example.currencyconverter.presentation.screens.LoadingScreen
import com.example.currencyconverter.presentation.screens.SelectDefaultCurrenciesScreen
import com.example.currencyconverter.presentation.screens.SettingsScreen
import com.example.currencyconverter.presentation.screens.WelcomeScreen
import com.example.currencyconverter.viewmodel.SettingsViewModel

sealed class Screen(val route: String) {
    object Loading : Screen("loading_screen")
    object Welcome : Screen("welcome_screen")
    object EnterName : Screen("enter_name_screen")
    object SelectDefaultCurrencies : Screen("select_default_currencies_screen")
    object Home : Screen("home_screen")
    object Settings : Screen("settings_screen")
}

@Composable
fun CurrencyConverterNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by settingsViewModel.settings.collectAsState()
    val isLoading by settingsViewModel.isLoading.collectAsState()

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val startDestination = when {
        isLoading -> Screen.Loading.route
        settings.firstAccess -> Screen.Welcome.route
        else -> Screen.Home.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Loading.route) {
            LoadingScreen()
        }
        composable(Screen.Welcome.route) {
            WelcomeScreenFlow(navController, settings.firstAccess)
        }
        composable(Screen.EnterName.route) {
            EnterNameScreenFlow(navController, settings.firstAccess, settingsViewModel)
        }
        composable(Screen.SelectDefaultCurrencies.route) {
            SelectDefaultCurrenciesFlow(navController, settings.firstAccess, settingsViewModel)
        }
        composable(Screen.Home.route) {
            HomeScreen(modifier)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(modifier)
        }
    }
}

@Composable
fun WelcomeScreenFlow(navController: NavHostController, firstAccess: Boolean) {
    if (firstAccess) {
        WelcomeScreen(
            onContinue = { navController.navigate(Screen.EnterName.route) }
        )
    } else {
        navController.navigate(Screen.Home.route)
    }
}

@Composable
fun EnterNameScreenFlow(
    navController: NavHostController,
    firstAccess: Boolean,
    settingsViewModel: SettingsViewModel
) {
    if (firstAccess) {
        EnterNameScreen(
            onContinue = { name ->
                settingsViewModel.setNameAndNavigateToSelectCurrency(
                    name = name,
                    navController = navController
                )
            }
        )
    } else {
        navController.navigate(Screen.Home.route)
    }
}

@Composable
fun SelectDefaultCurrenciesFlow(
    navController: NavHostController,
    firstAccess: Boolean,
    settingsViewModel: SettingsViewModel
) {
    if (firstAccess) {
        SelectDefaultCurrenciesScreen(
            onContinue = { baseCurrency, targetCurrency ->
                settingsViewModel.saveSettingsAndNavigate(
                    baseCurrency,
                    targetCurrency,
                    navController
                )
            }
        )
    } else {
        navController.navigate(Screen.Home.route)
    }
}

fun SettingsViewModel.setNameAndNavigateToSelectCurrency(
    name: String,
    navController: NavHostController
) {
    save(
        Settings(
            name = name,
            firstAccess = true
        )
    )
    refreshSettings()
    navController.navigate(Screen.SelectDefaultCurrencies.route)
}

fun SettingsViewModel.saveSettingsAndNavigate(
    baseCurrency: String,
    targetCurrency: String,
    navController: NavHostController
) {
    save(
        Settings(
            name = settings.value.name,
            defaultBaseCurrency = baseCurrency,
            defaultTargetCurrency = targetCurrency,
            firstAccess = false
        )
    )
    refreshSettings()
    navController.navigate(Screen.Home.route)
}
