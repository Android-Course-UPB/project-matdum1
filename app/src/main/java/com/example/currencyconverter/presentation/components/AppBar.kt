package com.example.currencyconverter.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyAppBar(
    onHomeClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onExitClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = "Currency Converter") },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Navigation")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Home") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                    },
                    onClick = {
                        expanded = false
                        onHomeClick()
                    }
                )
                DropdownMenuItem(
                    text = { Text("Settings") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                    },
                    onClick = {
                        expanded = false
                        onSettingsClick()
                    }
                )
                DropdownMenuItem(
                    text = { Text("Exit") },
                    leadingIcon = {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Exit")
                    },
                    onClick = {
                        expanded = false
                        onExitClick()
                    }
                )
            }
        }
    )
}